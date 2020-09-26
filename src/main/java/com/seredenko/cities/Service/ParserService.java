package com.seredenko.cities.Service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

@Service
public class ParserService {

    public HashMap<String, Boolean> setCities() {
        HashMap<String, Boolean> cities = new HashMap<>();



        try {
            //get File with cities
            File file = ResourceUtils.getFile("classpath:static/cities.txt");
            System.out.println(file.length());
            Scanner sc = new Scanner(file);

            while(sc.hasNextLine()){
                String line = sc.nextLine();
                cities.put(line, false);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return cities;
    }
}
