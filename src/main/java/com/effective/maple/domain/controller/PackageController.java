package com.effective.maple.domain.controller;

import com.effective.maple.domain.dto.CompareDto.PackageItem;
import com.effective.maple.domain.usecase.PackageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PackageController {

    private final PackageUseCase packageUseCase;


    @GetMapping("/package")
    public String packagePage(Model model) {
        PackageItem packageItem = packageUseCase.getInitPackageItem();
        model.addAttribute("packageItem", packageItem);

        return "package";
    }

    @GetMapping("/package/calculate")
    public String packageCalculate(PackageItem packageItem, Model model) {
        String price = packageUseCase.getPackageResult(packageItem);
        PackageItem result;
        result = packageItem;
        result.setResult(price);
        model.addAttribute("packageItem", result);

        return "package";
    }
}
