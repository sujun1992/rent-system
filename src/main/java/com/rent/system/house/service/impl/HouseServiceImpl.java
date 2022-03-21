package com.rent.system.house.service.impl;

import com.rent.system.common.CommonHttpResponse;
import com.rent.system.house.dao.HouseDao;
import com.rent.system.house.dao.entity.HouseEntity;
import com.rent.system.house.dao.entity.HouseEntity_;
import com.rent.system.house.model.*;
import com.rent.system.house.service.HouseService;
import com.rent.system.user.dao.UserDao;
import com.rent.system.user.dao.entity.UserEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class HouseServiceImpl implements HouseService {

    @Value("${image.base.path}")
    private String imageBasePath;
    @Value("${this.service.url:http://localhost:8080}")
    private String serviceUrl;
    private HouseDao houseDao;
    private UserDao userDao;

    @Autowired
    public void setHouseDao(HouseDao houseDao) {
        this.houseDao = houseDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public ResponseEntity<CommonHttpResponse<String>> addHouse(HouseAddRequestBody body,
                                                               HttpSession session) {
        String userId = String.valueOf(session.getAttribute("userId"));
        HouseEntity entity = new HouseEntity();
        entity.setHouseOwner(userId);
        entity.setArea(body.getArea());
        entity.setAddress(body.getAddress());
        entity.setHouseDesc(body.getHouseDesc());
        entity.setRentalTime(body.getRentalTime());
        entity.setRentNum(body.getRentNum());
        entity.setShareNum(body.getShareNum());
        entity.setRoomType(body.getRoomType());
        if (body.getHousePicture() != null) {
            // 记录服务器文件系统url
            entity.setHousePicture(body.getHousePicture());
        }
        if (body.getHouseType() != null) {
            entity.setHouseType(body.getHouseType());
        }
        houseDao.save(entity);
        return CommonHttpResponse.ok("success");
    }

    private String saveImage(MultipartFile houseType) {
        String originalFileName = houseType.getOriginalFilename();
        // 获取图片后缀
        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
        // 生成图片存储的名称，UUID 避免相同图片名冲突，并加上图片后缀
        String fileName = UUID.randomUUID() + suffix;
        // 图片存储路径
        String filePath = imageBasePath + fileName;
        filePath = new File(filePath).getAbsolutePath();
        File saveFile = new File(filePath);
        try {
            // 将上传的文件保存到服务器文件系统
            houseType.transferTo(saveFile);
        } catch (IOException e) {
            log.error("保存图片失败！", e);
        }
        return filePath;
    }

    @Override
    public void getHouseType(String houseId, HttpServletResponse response) {
        Optional<HouseEntity> optional = houseDao.findById(houseId);
        if (optional.isPresent()) {
            String houseType = optional.get().getHouseType();
            createImage(response, houseType);
        }
    }

    private void createImage(HttpServletResponse response, String houseType) {
        response.setContentType("image/*");
        try {
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment;filename=" + URLEncoder.encode(
                            houseType.substring(houseType.lastIndexOf(File.separator)),
                            StandardCharsets.UTF_8.name()));
        } catch (UnsupportedEncodingException e) {
            log.error("设置文件名失败！", e);
        }
        try (InputStream inputStream = new FileInputStream(
                houseType); OutputStream outputStream = response.getOutputStream()) {
            byte[] buff = new byte[1024];
            int len;
            while ((len = inputStream.read(buff, 0, 1024)) != -1) {
                outputStream.write(buff, 0, len);
            }
            outputStream.flush();
        } catch (Exception e) {
            log.error("生成房型图片失败！", e);
        }
    }

    @Override
    public void getHousePicture(String houseId, HttpServletResponse response) {
        Optional<HouseEntity> optional = houseDao.findById(houseId);
        if (optional.isPresent()) {
            String housePicture = optional.get().getHousePicture();
            createImage(response, housePicture);
        }
    }

    @Override
    public ResponseEntity<CommonHttpResponse<String>> delHouse(String houseId) {
        houseDao.deleteById(houseId);
        return CommonHttpResponse.ok("success");
    }

    @Override
    public ResponseEntity<CommonHttpResponse<HouseListResponse>> getHouseList(int page, int size,
                                                                              String area, Boolean share, int rentMinNum, int rentMaxNum, HttpSession session) {
        //page从0开始计数而不是1
        Pageable pageable = PageRequest
                .of(page - 1, size, Sort.Direction.DESC, HouseEntity_.RENTAL_TIME);
        int type = Integer.parseInt(String.valueOf(session.getAttribute("type")));
        Page<HouseEntity> houseEntities = houseDao.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasLength(area)) {
                Predicate areaPredicate = criteriaBuilder
                        .equal(root.get(HouseEntity_.area), area);
                predicates.add(areaPredicate);
            }
            Predicate sharePredicate;
            if (share != null) {
                if (share) {
                    sharePredicate = criteriaBuilder
                            .greaterThan(root.get(HouseEntity_.shareNum), 1);
                } else {
                    sharePredicate = criteriaBuilder
                            .lessThan(root.get(HouseEntity_.shareNum), 1);
                }
                predicates.add(sharePredicate);
            }
            if (rentMaxNum > 0) {
                Predicate rentMin = criteriaBuilder
                        .greaterThanOrEqualTo(root.get(HouseEntity_.rentNum), rentMinNum);
                Predicate rentMax = criteriaBuilder
                        .lessThanOrEqualTo(root.get(HouseEntity_.rentNum), rentMaxNum);
                predicates.add(rentMin);
                predicates.add(rentMax);
            }
            switch (type) {
                case 0:
                    Predicate tenant = criteriaBuilder
                            .equal(root.get(HouseEntity_.houseRentStatus), 0);
                    predicates.add(tenant);
                    Predicate auditPredicate = criteriaBuilder
                            .equal(root.get(HouseEntity_.houseAuditStatus), 1);
                    predicates.add(auditPredicate);
                    break;
                case 1:
                    String userId = String.valueOf(session.getAttribute("userId"));
                    Predicate landlord = criteriaBuilder
                            .equal(root.get(HouseEntity_.houseOwner), userId);
                    predicates.add(landlord);
                    break;
                default:
                    Predicate audit = criteriaBuilder
                            .equal(root.get(HouseEntity_.houseAuditStatus), 0);
                    predicates.add(audit);
                    break;
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
        HouseListResponse response = new HouseListResponse();
        response.setTotal(houseEntities.getTotalElements());
        List<UserEntity> userEntityList = userDao.findAllById(houseEntities.stream()
                .map(HouseEntity::getHouseTenant)
                .filter(StringUtils::hasLength)
                .collect(Collectors.toList()));
        Map<String, List<UserEntity>> map = userEntityList.stream()
                .collect(Collectors.groupingBy(UserEntity::getUserId));
        response.setList(
                houseEntities.getContent().stream().map(HouseBaseInfo::new).collect(Collectors.toList()));
        response.getList().forEach(houseBaseInfo -> {
            houseBaseInfo.setHousePicture(
                    serviceUrl + "/house/" + houseBaseInfo.getHouseId() + "/housePicture");
            houseBaseInfo.setHouseType(
                    serviceUrl + "/house/" + houseBaseInfo.getHouseId() + "/houseType");
            if (map.get(houseBaseInfo.getHouseTenant()) != null) {
                houseBaseInfo.setLodger(new LodgerInfo(map.get(houseBaseInfo.getHouseTenant()).get(0)));
            }
        });
        return CommonHttpResponse.ok(response);
    }

    @Override
    public ResponseEntity<CommonHttpResponse<HouseBaseInfo>> getHouse(String houseId) {
        Optional<HouseEntity> optional = houseDao.findById(houseId);
        if (optional.isPresent()) {
            HouseEntity entity = optional.get();
            return CommonHttpResponse.ok(new HouseBaseInfo(entity));
        }
        return CommonHttpResponse.exception(60002, "未找到对应房屋信息！");
    }

    @Override
    public ResponseEntity<CommonHttpResponse<List<HouseBaseInfo>>> currentRentHouse(HttpSession session) {
        String userId = String.valueOf(session.getAttribute("userId"));
        List<HouseEntity> entities = houseDao.findByHouseTenantAndHouseRentStatus(userId, 1);
        if (entities != null && !entities.isEmpty()) {
            return CommonHttpResponse.ok(entities.stream().map(HouseBaseInfo::new)
                    .peek(info -> userDao.findById(info.getHouseOwner()).ifPresent(user -> info.setOwner(new OwnerInfo(user))))
                    .collect(Collectors.toList()));
        }
        return CommonHttpResponse.exception(60002, "未找到对应房屋信息！");
    }

    @Override
    public ResponseEntity<CommonHttpResponse<String>> leaseRenewal(String houseId, int type, int rentNum, HttpSession session) {
        String userId = String.valueOf(session.getAttribute("userId"));
        Optional<HouseEntity> optional = houseDao.findById(houseId);
        if (optional.isPresent()) {
            HouseEntity entity = optional.get();
            if (type == 1) {
                entity.setHouseRentStatus(3);
                entity.setHouseRentAction(0);
            } else {
                entity.setHouseRentStatus(6);
                entity.setHouseRentAction(2);
            }
            entity.setRentNum(rentNum);
            entity.setHouseTenant(userId);
            houseDao.saveAndFlush(entity);
            return CommonHttpResponse.ok("success");
        }
        return CommonHttpResponse.exception(60002, "未找到对应房屋信息！");
    }

    @Override
    public ResponseEntity<CommonHttpResponse<String>> updateHouse(String houseId,
                                                                  HouseAddRequestBody body) {
        Optional<HouseEntity> optional = houseDao.findById(houseId);
        if (optional.isPresent()) {
            HouseEntity entity = optional.get();
            entity.setArea(body.getArea());
            entity.setAddress(body.getAddress());
            entity.setHouseDesc(body.getHouseDesc());
            entity.setRentalTime(body.getRentalTime());
            entity.setRentNum(body.getRentNum());
            entity.setShareNum(body.getShareNum());
            entity.setRoomType(body.getRoomType());
            if (body.getHousePicture() != null) {
                // 记录服务器文件系统url
                entity.setHousePicture(body.getHousePicture());
            }
            if (body.getHouseType() != null) {
                entity.setHouseType(body.getHouseType());
            }
            houseDao.save(entity);
            return CommonHttpResponse.ok("success");
        }
        return CommonHttpResponse.exception(60002, "未找到对应房屋信息！");
    }

    @Override
    public ResponseEntity<CommonHttpResponse<List<HouseTenantInfo>>> rent(HttpSession session) {
        String userId = String.valueOf(session.getAttribute("userId"));
        List<Integer> status = new ArrayList<>();
        status.add(3);
        status.add(4);
        status.add(6);
        List<HouseEntity> entities = houseDao.findByHouseOwnerAndHouseRentStatusIn(
                userId, status);
        List<UserEntity> userEntities = userDao.findAllById(
                entities.stream().map(HouseEntity::getHouseTenant).collect(Collectors.toList()));
        Map<String, List<UserEntity>> map = userEntities.stream()
                .collect(Collectors.groupingBy(UserEntity::getUserId));
        return CommonHttpResponse.ok(entities.stream().map(houseEntity -> {
            HouseTenantInfo houseTenantInfo = new HouseTenantInfo();
            if (map.get(houseEntity.getHouseTenant()) != null) {
                BeanUtils.copyProperties(map.get(houseEntity.getHouseTenant()).get(0), houseTenantInfo);
            }
            houseTenantInfo.setHouseId(houseEntity.getHouseId());
            houseTenantInfo.setAddress(houseEntity.getAddress());
            houseTenantInfo.setArea(houseEntity.getArea());
            houseTenantInfo.setType(houseEntity.getHouseRentStatus());
            return houseTenantInfo;
        }).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<CommonHttpResponse<String>> picture(MultipartFile picture) {
        return CommonHttpResponse.ok(saveImage(picture));
    }

    @Override
    public ResponseEntity<CommonHttpResponse<String>> agree(HouseAgreeRequest request) {
        Optional<HouseEntity> optional = houseDao.findByHouseTenantAndHouseId(
                request.getUserId(), request.getHouseId());
        if (optional.isPresent()) {
            HouseEntity entity = optional.get();
            if (request.isPass()) {
                entity.setHouseRentStatus(5);
                entity.setHouseRentAction(1);
            } else {
                entity.setHouseRentStatus(0);
                entity.setHouseRentAction(3);
            }
            houseDao.save(entity);
            return CommonHttpResponse.ok("success");
        }
        return CommonHttpResponse.exception(60002, "未找到对应房屋信息！");
    }

    @Override
    public ResponseEntity<CommonHttpResponse<String>> houseRequest(HouseRequest request,
                                                                   HttpSession session) {
        String userId = String.valueOf(session.getAttribute("userId"));
        Optional<HouseEntity> optional = houseDao.findById(request.getHouseId());
        if (optional.isPresent()) {
            HouseEntity entity = optional.get();
            entity.setHouseRentStatus(4);
            entity.setRentDuration(request.getRentDuration());
            entity.setHouseTenant(userId);
            houseDao.save(entity);
            return CommonHttpResponse.ok("success");
        }
        return CommonHttpResponse.exception(60002, "未找到对应房屋信息！");
    }

    @Override
    public ResponseEntity<CommonHttpResponse<Map<Integer, List<HouseBaseInfo>>>> tenantAuditInfo(HttpSession session) {
        String userId = String.valueOf(session.getAttribute("userId"));
        List<HouseEntity> houseEntities = houseDao.findByHouseTenant(userId);
        Map<Integer, List<HouseBaseInfo>> map = houseEntities.stream()
                .filter(entity -> entity.getHouseRentStatus() == 3 || entity.getHouseRentStatus() == 4 || entity.getHouseRentStatus() == 6)
                .map(HouseBaseInfo::new)
                .peek(info -> userDao.findById(info.getHouseOwner()).ifPresent(user -> info.setOwner(new OwnerInfo(user))))
                .collect(Collectors.groupingBy(HouseBaseInfo::getHouseRentStatus));
        return CommonHttpResponse.ok(map);
    }

    @Override
    public ResponseEntity<CommonHttpResponse<String>> adminAgree(HouseAgreeRequest request) {
        Optional<HouseEntity> optional = houseDao.findById(request.getHouseId());
        if (optional.isPresent()) {
            HouseEntity entity = optional.get();
            if (request.isPass()) {
                entity.setHouseRentStatus(1);
                entity.setHouseRentAction(4);
                entity.setHouseTenant(request.getUserId());
            } else {
                entity.setHouseRentStatus(0);
                entity.setHouseRentAction(3);
            }
            houseDao.save(entity);
            return CommonHttpResponse.ok("success");
        }
        return CommonHttpResponse.exception(60002, "未找到对应房屋信息！");
    }

    @Override
    public ResponseEntity<CommonHttpResponse<Map<Integer, List<HouseBaseInfo>>>> adminAuditInfo(HttpSession session) {
        List<Integer> status = new ArrayList<>();
        status.add(0);
        status.add(1);
        status.add(2);
        List<HouseEntity> entities = houseDao.findByHouseRentStatusAndHouseRentActionIn(5, status);
        return CommonHttpResponse.ok(entities.stream()
                .map(HouseBaseInfo::new)
                .peek(info -> userDao.findById(info.getHouseOwner()).ifPresent(user -> info.setOwner(new OwnerInfo(user))))
                .collect(Collectors.groupingBy(HouseBaseInfo::getHouseRentAction)));
    }

    @Override
    public ResponseEntity<CommonHttpResponse<List<HouseBaseInfo>>> getRentHouse(HttpSession session) {
        List<HouseEntity> entities = houseDao.findByHouseRentStatus(1);
        return CommonHttpResponse.ok(entities.stream()
                .map(HouseBaseInfo::new)
                .peek(info -> userDao.findById(info.getHouseOwner()).ifPresent(user -> info.setOwner(new OwnerInfo(user))))
                .peek(info -> userDao.findById(info.getHouseTenant()).ifPresent(user -> info.setLodger(new LodgerInfo(user))))
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<CommonHttpResponse<List<HouseBaseInfo>>> getOwnerRentHouse(HttpSession session) {
        String userId = String.valueOf(session.getAttribute("userId"));
        List<HouseEntity> entities = houseDao.findByHouseOwnerAndHouseRentStatusIn(userId, Collections.singletonList(1));
        return CommonHttpResponse.ok(entities.stream()
                .map(HouseBaseInfo::new)
                .peek(info -> userDao.findById(info.getHouseOwner()).ifPresent(user -> info.setOwner(new OwnerInfo(user))))
                .peek(info -> userDao.findById(info.getHouseTenant()).ifPresent(user -> info.setLodger(new LodgerInfo(user))))
                .collect(Collectors.toList()));
    }
}
