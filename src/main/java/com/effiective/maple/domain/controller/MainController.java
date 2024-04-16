package com.effiective.maple.domain.controller;

import com.effiective.maple.domain.dto.RequestDto.ItemInformation;
import com.effiective.maple.domain.dto.ResponseDto.EfficientResult;
import com.effiective.maple.domain.usecase.MainUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MainUseCase mainUseCase;

    /**
     * 메인 화면 가져오기 [GET] /
     */
    @GetMapping("/")
    public String getMainPage(Model model) {
        ItemInformation itemInformation = new ItemInformation();
        itemInformation.setOptimizeStat(mainUseCase.getInitOptimize());
        itemInformation.setEfficientResult(new EfficientResult());

        model.addAttribute("itemInformation", itemInformation);

        return "index";
    }

    @GetMapping("/calculate")
    public String calculate(@RequestParam("calculate") String type, ItemInformation itemInformation,
        Model model) {
        ItemInformation result = mainUseCase.getEfficientResult(itemInformation,
            Integer.parseInt(type));

        model.addAttribute("itemInformation", result);

        return "index";
    }
}
