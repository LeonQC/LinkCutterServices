package com.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/link")
public class TestLinkController {
    @GetMapping("/test")
    public String test(){

        return "link test";
    }

}
