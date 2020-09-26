package com.seredenko.cities.Service;

import com.seredenko.cities.Model.User;
import com.seredenko.cities.Repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class CitiesService {

    @Autowired
    private UserRepository userRepository;

    public void beginGame(User user){
        HashMap<String, Boolean> cities = user.getCities();
        int num = (int) (Math.random() * 9000);
        Object firstKey = cities.keySet().toArray()[num-1];
        cities.replace((String) firstKey, true);
        user.setLastWord((String) firstKey);
        user.setCities(cities);
    }
}
