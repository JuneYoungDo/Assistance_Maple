package com.effective.maple.domain.usecase;

import com.effective.maple.domain.dto.CompareDto.PackageItem;

public interface PackageUseCase {
    String getPackageResult(PackageItem packageItem);
    PackageItem getInitPackageItem();
}
