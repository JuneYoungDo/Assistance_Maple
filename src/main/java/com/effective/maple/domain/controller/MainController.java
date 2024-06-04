package com.effective.maple.domain.controller;

import com.effective.maple.domain.dto.CompareDto.ItemInformation;
import com.effective.maple.domain.usecase.MainUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequiredArgsConstructor
@SessionAttributes("itemInformation")
public class MainController {

    private final MainUseCase mainUseCase;

    @ModelAttribute("itemInformation")
    public ItemInformation itemInformation() {
        return new ItemInformation();
    }

    @GetMapping("/")
    public String getMainPage(Model model, @ModelAttribute("itemInformation") ItemInformation itemInformation) {
        if (itemInformation.getCustomAllPart() == null) {
            System.out.println("null");
            itemInformation.setCustomAllPart(mainUseCase.getInitCustomAllPart());
        }
        model.addAttribute("itemInformation", itemInformation);

        return "index";
    }

    @GetMapping("/calculate")   // 파라미터 1,2 -> 단순 계산 , 3,4 -> 캐릭터로 가져가기
    public String calculate(@RequestParam("calculate") String type, @ModelAttribute("itemInformation") ItemInformation itemInformation,
        Model model) {
        if (type.equals("reset")) {
            ItemInformation newItemInformation = new ItemInformation();
            newItemInformation.setCustomAllPart(mainUseCase.getInitCustomAllPart());
            itemInformation = newItemInformation;
            model.addAttribute(itemInformation);
            return "index";
        }
        ItemInformation result = mainUseCase.getEfficientResult(itemInformation,
            Integer.parseInt(type));
        if(type.equals("1") || type.equals("2")) {
            model.addAttribute("itemInformation", result);
            return "index";
        } else {
            result = mainUseCase.addItemToCharacter(result,Integer.parseInt(type));
            model.addAttribute("itemInformation", result);
            return "searchItem";
        }
    }


    @GetMapping("/ads.txt")
    public String ads() {
        return "ads.txt";
    }
}
