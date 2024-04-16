package com.effiective.maple.domain.dto;

import com.effiective.maple.domain.dto.ResponseDto.EfficientResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class RequestDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemInformation {
        private OptimizeStat optimizeStat = new OptimizeStat();
        private EfficientResult efficientResult = new EfficientResult();
        private Item item1 = new Item();
        private Item item2 = new Item();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        private EquipInformation equipInformation = new EquipInformation();
        private ChangeInformation changeInformation = new ChangeInformation();
        private PotentialInformation potentialInformation = new PotentialInformation();
        private EdiPotentialInformation ediPotentialInformation = new EdiPotentialInformation();
        private Price price = new Price();
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OptimizeStat {
        private int level = 0;
        private double attack = 0;
        private double sub = 0;
        private double mainPer = 0;
        private double subPer = 0;
        private double allPer = 0;
        private double critical = 0;
        private double boss = 0;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EquipInformation {
        private int main = 0;
        private int sub = 0;
        private int attack = 0;
        private int allPer = 0;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangeInformation {
        private int main = 0;
        private int sub = 0;
        private int attack = 0;
        private int boss = 0;
        private int critical = 0;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PotentialInformation {
        private int mainPer = 0;
        private int subPer = 0;
        private int allPer = 0;
        private int main = 0;
        private int sub = 0;
        private int critical = 0;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EdiPotentialInformation {
        private int mainPer = 0;
        private int subPer = 0;
        private int allPer = 0;
        private int main = 0;
        private int sub = 0;
        private int attack = 0;
        private int critical = 0;
        private int level = 0;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Price {
        private Long price = 0L;
    }
}
