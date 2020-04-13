package com.sizaif.emsdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.WebParam;

@Controller
@RequestMapping("/contest")
public class ContestController {


    @RequestMapping("/toContestList")
    public String queryAllContestlist(Model model){
        return "production/Contest/contests";
    }

}
