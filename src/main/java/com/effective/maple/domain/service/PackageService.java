package com.effective.maple.domain.service;

import com.effective.maple.domain.dto.CompareDto.PackageItem;
import com.effective.maple.domain.usecase.PackageUseCase;
import java.text.DecimalFormat;
import org.springframework.stereotype.Service;

@Service
public class PackageService implements PackageUseCase {

    @Override
    public String getPackageResult(PackageItem packageItem) {
        long voucherPriceForWon = packageItem.getVoucherPrice()/100000000 * packageItem.getPointPrice();
        Long noVoucherPackagePrice = packageItem.getPackagePrice() * 100000000 - voucherPriceForWon * 100000000;
        Long packagePriceForMeso = noVoucherPackagePrice / packageItem.getPointPrice();
        Long result = packagePriceForMeso / packageItem.getItemNum();

        return DecimalFormat.getInstance().format(result);
    }

    @Override
    public PackageItem getInitPackageItem() {
        PackageItem packageItem = new PackageItem(29900L,3000L,200000000L,15L,"");
        packageItem.setResult(getPackageResult(packageItem));

        return packageItem;
    }
}
