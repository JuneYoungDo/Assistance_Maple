package com.effective.maple.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class OptimizeDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CustomAllPart {
        private String nickname;
        private String preset;
        private OptimizeStat optimizeStat;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OptimizeStat {
        private int level = 0;
        private double main = 0;
        private double attack = 0;
        private double sub = 0;
        private double mainPer = 0;
        private double subPer = 0;
        private double allPer = 0;
        private double critical = 0;
        private double boss = 0;
    }
}
