package com.effiective.maple.domain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    /**
     * 메인 화면 가져오기
     * [GET] /
     */
    @GetMapping("/")
    public String getMainPage(){
        return "index";
    }
}
