package com.effiective.maple.domain.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
