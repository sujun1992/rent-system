package com.rent.system.house.service.impl;

import com.rent.system.common.CommonHttpResponse;
import com.rent.system.house.dao.HouseDao;
import com.rent.system.house.dao.entity.HouseEntity;
import com.rent.system.house.dao.entity.HouseEntity_;
import com.rent.system.house.model.HouseAddRequestBody;
import com.rent.system.house.model.HouseBaseInfo;
import com.rent.system.house.model.HouseListResponse;
import com.rent.system.house.service.HouseService;
import lombok.extern.slf4j.Slf4j;
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

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HouseServiceImpl implements HouseService {

    @Value("${image.base.path}")
    private String imageBasePath;
    private HouseDao houseDao;

    @Autowired
    public void setHouseDao(HouseDao houseDao) {
        this.houseDao = houseDao;
    }

    @Override
    public ResponseEntity<CommonHttpResponse<String>> addHouse(HouseAddRequestBody body,
                                                               MultipartFile houseType,
                                                               MultipartFile housePicture) {
        HouseEntity entity = new HouseEntity();
        entity.setArea(body.getArea());
        entity.setHouseDesc(body.getDesc());
        entity.setRentalTime(body.getRentalTime());
        entity.setRentNum(body.getRentNum());
        entity.setShareNum(body.getShareNum());
        if (housePicture != null) {
            // 记录服务器文件系统url
            entity.setHousePicture(saveImage(housePicture));
        }
        if (houseType != null) {
            entity.setHouseType(saveImage(houseType));
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
    public ResponseEntity<CommonHttpResponse<HouseListResponse>> getHouseList(int page, int size, String area, boolean share, int rentMinNum, int rentMaxNum, HttpSession session) {
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
            if (share) {
                sharePredicate = criteriaBuilder
                        .greaterThan(root.get(HouseEntity_.shareNum), 1);
            } else {
                sharePredicate = criteriaBuilder
                        .equal(root.get(HouseEntity_.shareNum), 1);
            }
            predicates.add(sharePredicate);
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
                    break;
                case 1:
                    String userId = String.valueOf(session.getAttribute("userId"));
                    Predicate landlord = criteriaBuilder
                            .equal(root.get(HouseEntity_.houseOwner), userId);
                    predicates.add(landlord);
                    break;
                default:
                    break;
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
        HouseListResponse response = new HouseListResponse();
        response.setTotal(houseEntities.getTotalElements());
        response.setList(houseEntities.getContent().stream().map(HouseBaseInfo::new).collect(Collectors.toList()));
        return CommonHttpResponse.ok(response);
    }

    @Override
    public ResponseEntity<CommonHttpResponse<HouseBaseInfo>> getHouse(String houseId) {
        Optional<HouseEntity> optional = houseDao.findById(houseId);
        if (optional.isPresent()) {
            HouseEntity entity = optional.get();
            return CommonHttpResponse.ok(new HouseBaseInfo(entity));
        }
        return CommonHttpResponse.exception(60001, "未找到对应房屋信息！");
    }
}
