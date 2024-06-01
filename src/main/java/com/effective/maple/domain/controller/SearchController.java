package com.effective.maple.domain.controller;

import com.effective.maple.domain.dto.RequestDto.ItemListUpReq;
import com.effective.maple.domain.usecase.MainUseCase;
import com.effective.maple.domain.usecase.SearchUseCase;
import com.effective.maple.global.exception.BaseException;
import com.effective.maple.global.exception.errorCode.CharacterErrorCode;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final MainUseCase mainUseCase;
    private final SearchUseCase searchUseCase;

    @GetMapping("/search")
    public String getSearchPage(Model model) {
        ItemListUpReq itemListUpReq = new ItemListUpReq();
        itemListUpReq.setOptimizeStat(mainUseCase.getInitOptimize());
        model.addAttribute("itemListUp", itemListUpReq);

        return "searchItem";
    }

    @GetMapping("/search/nickname")
    public String returnSearchPage(ItemListUpReq itemListUpReq, Model model) {
        model.addAttribute("itemListUp", itemListUpReq);
        try {
            model.addAttribute("itemInfoList", searchUseCase.getItemListUp(itemListUpReq));
        } catch (BaseException exception) {
            model.addAttribute("exception", exception);
        } catch (Exception exception) {
            model.addAttribute("exception", new BaseException(CharacterErrorCode.SERVER_ERROR));
        }

        return "searchItem";
    }
}
