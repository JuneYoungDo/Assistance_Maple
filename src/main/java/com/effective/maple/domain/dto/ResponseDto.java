package com.effective.maple.domain.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ResponseDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemInfo {
        private String img;
        private String name;
        private double basicOption;
        private double etcOption;   // 작 + 익셉
        private double addOption;   // 추옵
        private double potential;
        private double additional;
        private int starForce;
        private double starForceStat;
        private double value;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EfficientResult {
        private Result result1 = new Result();
        private Result result2 = new Result();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {
        private String allStatPer = "0.0";
        private String priceForMainPer = "0.0";
        private String mainPerForPrice = "0.0";
    }
}
