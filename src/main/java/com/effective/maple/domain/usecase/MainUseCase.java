package com.effective.maple.domain.usecase;

import com.effective.maple.domain.dto.CompareDto.ItemInformation;
import com.effective.maple.domain.dto.OptimizeDto.CustomAllPart;

public interface MainUseCase {
    CustomAllPart getInitCustomAllPart();
    ItemInformation getEfficientResult(ItemInformation itemInformation,int type);
    ItemInformation addItemToCharacter(ItemInformation itemInformation, int type);
}
