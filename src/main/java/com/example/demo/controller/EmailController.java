package com.example.demo.controller;

import com.example.demo.dal.EmailDAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/email")
public class EmailController {

    @Autowired
    EmailDAL emailDAL;

    @GetMapping("sendEmail")
    public String sendEmail() {
        return emailDAL.sendEmail();
    }
}
