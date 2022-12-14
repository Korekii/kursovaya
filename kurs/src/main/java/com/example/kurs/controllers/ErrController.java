package com.example.kurs.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ErrController {
    @RequestMapping("/error")
    @ResponseBody
    @GetMapping
    String error(HttpServletRequest request) {
        return "<h1>404</h1>";
    }


    public String getErrorPath() {
        return "/error";
    }
}
