package com.effective.maple.domain.usecase;

import com.effective.maple.domain.dto.RequestDto.PackageItem;

public interface PackageUseCase {
    String getPackageResult(PackageItem packageItem);
    PackageItem getInitPackageItem();
}
