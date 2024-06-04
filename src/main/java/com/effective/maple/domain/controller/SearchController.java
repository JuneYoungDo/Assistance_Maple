package com.effective.maple.domain.controller;

import com.effective.maple.domain.dto.CompareDto.ItemInformation;
import com.effective.maple.domain.usecase.MainUseCase;
import com.effective.maple.domain.usecase.SearchUseCase;
import com.effective.maple.global.exception.BaseException;
import com.effective.maple.global.exception.errorCode.CharacterErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequiredArgsConstructor
@SessionAttributes("itemInformation")
public class SearchController {

    private final MainUseCase mainUseCase;
    private final SearchUseCase searchUseCase;

    @ModelAttribute("itemInformation")
    public ItemInformation itemInformation() {
        return new ItemInformation();
    }

    @GetMapping("/search")
    public String getSearchPage(@ModelAttribute("itemInformation") ItemInformation itemInformation,
        Model model) {
        if (itemInformation.getCustomAllPart() == null) {
            itemInformation.setCustomAllPart(mainUseCase.getInitCustomAllPart());
        }
        model.addAttribute("itemInformation", itemInformation);

        return "searchItem";
    }

    @GetMapping("/search/nickname")
    public String returnSearchPage(@RequestParam("status") String status,
        @ModelAttribute("itemInformation") ItemInformation itemInformation,
        SessionStatus sessionStatus, Model model) {
        if ("reset".equals(status)) {
            sessionStatus.setComplete(); // 세션 무효화
            ItemInformation newItemInformation = new ItemInformation();
            newItemInformation.setCustomAllPart(mainUseCase.getInitCustomAllPart());
            model.addAttribute("itemInformation", newItemInformation);
            return "searchItem";
        }

        try {
            model.addAttribute("itemInformation", itemInformation);
            model.addAttribute("itemInfoList", searchUseCase.getItemListUp(itemInformation));
        } catch (BaseException exception) {
            model.addAttribute("exception", exception);
        } catch (Exception exception) {
            model.addAttribute("exception", new BaseException(CharacterErrorCode.SERVER_ERROR));
        }

        return "searchItem";
    }
}
