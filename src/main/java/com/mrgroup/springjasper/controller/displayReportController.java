package com.mrgroup.springjasper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/renderRpt")
public class displayReportController {

    @RequestMapping("/show")
    public String displayReport(){
        return "showReport";
    }

}
