package com.seredenko.cities;

import com.seredenko.cities.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CitiesApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
    }
    @Test
    void kek(){
        userService.createAdmin("denis1-seredenko@ukr.net", "12345");
    }

}
