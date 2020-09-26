package com.seredenko.cities.Controller;

import com.seredenko.cities.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AvtorizeController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String reg(){
        return "reg";
    }

    @PostMapping("/register")
    public String regPost(@RequestParam String email, @RequestParam String password){
        userService.createUser(email,password);
        return "reg";
    }
}
