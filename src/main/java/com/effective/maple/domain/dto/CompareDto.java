package com.effective.maple.domain.dto;

import com.effective.maple.domain.dto.OptimizeDto.CustomAllPart;
import com.effective.maple.domain.dto.ResponseDto.EfficientResult;
import com.effective.maple.domain.dto.ResponseDto.ItemInfo;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CompareDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemInformation {
        private CustomAllPart customAllPart;
        private EfficientResult efficientResult;
        private Item item1 = new Item();
        private Item item2 = new Item();
        private List<ItemInfo> customItemList;
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

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PackageItem {
        private Long packagePrice = 0L;
        private Long pointPrice = 0L;
        private Long voucherPrice = 0L;
        private Long itemNum = 0L;
        private String result = "";
    }

}
