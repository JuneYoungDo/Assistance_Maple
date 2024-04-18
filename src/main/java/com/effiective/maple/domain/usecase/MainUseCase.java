package com.effiective.maple.domain.usecase;

import com.effiective.maple.domain.dto.RequestDto.ItemInformation;
import com.effiective.maple.domain.dto.RequestDto.OptimizeStat;
import com.effiective.maple.domain.dto.RequestDto.PackageItem;

public interface MainUseCase {
    OptimizeStat getInitOptimize();
    ItemInformation getEfficientResult(ItemInformation itemInformation,int type);
    String getPackageResult(PackageItem packageItem);
    PackageItem getInitPackageItem();
}
