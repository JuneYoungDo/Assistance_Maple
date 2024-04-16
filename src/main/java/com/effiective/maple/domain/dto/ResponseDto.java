package com.effiective.maple.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ResponseDto {

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
    public static  class Result {
        private String allStatPer = "0.0";
        private String priceForMainPer = "0.0";
        private String mainPerForPrice = "0.0";
    }
}
