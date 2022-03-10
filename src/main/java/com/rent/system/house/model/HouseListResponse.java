package com.rent.system.house.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HouseListResponse {
    private List<HouseBaseInfo> list = new ArrayList<>();
    private long total;
}
