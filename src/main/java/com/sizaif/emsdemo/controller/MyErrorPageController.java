package com.sizaif.emsdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorPageController {

    @RequestMapping("error_400")
    public String toPage400(){
        return "production/error/page_400";
    }
    @RequestMapping("error_401")
    public String toPage401(){
        return "production/error/page_401";
    }
    @RequestMapping("error_404")
    public String toPage404(){
        return "production/error/page_404";
    }
    @RequestMapping("error_403")
    public String toPage403(){
        return "production/error/page_403";
    }
    @RequestMapping("error_500")
    public String toPage500(){  return "production/error/page_500"; }
}
