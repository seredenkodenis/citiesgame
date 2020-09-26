package com.seredenko.cities.Service;

import com.seredenko.cities.Model.Role;
import com.seredenko.cities.Model.User;
import com.seredenko.cities.Repos.UserRepository;
import com.seredenko.cities.Status.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParserService parserService;

    public void createUser(String email, String password){
        BCryptPasswordEncoder  encoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setEmail(email);
        user.setPassword(encoder.encode(password));

        Role role = new Role("USER");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);


        user.setCities(parserService.setCities());

        user.setGameStatus(UserStatus.Waiting);

        userRepository.save(user);
    }

    public void createAdmin(String email, String password){
        BCryptPasswordEncoder  encoder = new BCryptPasswordEncoder();

        User cityUser = new User();
        cityUser.setEmail(email);
        cityUser.setPassword(encoder.encode(password));

        Role role = new Role("ADMIN");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        cityUser.setRoles(roles);

        userRepository.save(cityUser);
    }
}
