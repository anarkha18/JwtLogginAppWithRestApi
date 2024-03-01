package com.homejwt.controllers;

import com.homejwt.services.LoginCredentialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private LoginCredentialService loginCredentialService;
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);


    @GetMapping("/addUsers")
    public String home(){
     try {
         loginCredentialService.addUsers();
         logger.info("users saved!!");
         return "users created!!!";
     }catch (Exception e){
         logger.info("Error");
         return "user not saved!!!";
     }
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/userHome")

    public String userHome() {
        logger.info("hello");
        return "hello user";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/adminHome")
    public String adminHome() {
        logger.info("hello");
        return "hello admin";
    }

    @GetMapping("/test")
    public String test(){
        logger.info("test");
        return "tester";
    }



}
