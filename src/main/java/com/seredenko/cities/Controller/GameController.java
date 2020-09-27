package com.seredenko.cities.Controller;

import com.seredenko.cities.CitiesApplication;
import com.seredenko.cities.Model.User;
import com.seredenko.cities.Repos.UserRepository;
import com.seredenko.cities.Service.CitiesService;
import com.seredenko.cities.Status.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/game")
public class GameController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CitiesService citiesService;

    @GetMapping("")
    public String game(Principal principal, Model model){
        User user = userRepository.findUserByEmail(principal.getName());

        //give last word in user's game
        model.addAttribute("lastWord", user.getLastWord());
        return "index";
    }

    @GetMapping("/falseWord")
    public String falseWord(){
        //return if word doesn't exists or mistake in word
        return "FalseWord";
    }

    @GetMapping("/usedWord")
    public String usedWord(){
        //return if word was used
        return "usedWord";
    }

    @GetMapping("/uncorrectRule")
    public String uncorrectRule(){
        //return if word was used
        return "uncorrectRule";
    }

    @PostMapping("/next")
    public String game(@RequestParam String word, Principal principal){
        User user = userRepository.findUserByEmail(principal.getName());

        //check status of user's game
        if(user.getGameStatus().equals(UserStatus.Waiting)){
            return "redirect:/game";
        }

        String lastWord = user.getLastWord().toLowerCase();

        // we - Ирпень Миоры
        char lastWordChar = lastWord.toLowerCase().charAt(lastWord.length() - 1);

        System.out.println(lastWordChar!='ы');
        char PreLastWordChar = lastWord.toLowerCase().charAt(lastWord.length() - 2);
        if(lastWordChar == 'ы'){
            if(word.toLowerCase().charAt(0) != PreLastWordChar){
                System.out.println(2);
                return "redirect:/game/uncorrectRule";
            }
        }else if(lastWordChar == 'ь'){
            if(word.toLowerCase().charAt(0) != PreLastWordChar){
                System.out.println(2);
                return "redirect:/game/uncorrectRule";
            }
        }else{
            if(word.toLowerCase().charAt(0) != lastWordChar){
                System.out.println(2);
                return "redirect:/game/uncorrectRule";
            }
        }


        //get all cities
        HashMap<String, Boolean> inGame = user.getCities();
        Set<Map.Entry<String, Boolean>> inGame2 = inGame.entrySet();

        //get word from map
        Boolean used = inGame.get(word);

        //return error if word doesn't exists
        if(used == null)
            return "redirect:/game/falseWord";

        //set word used
        if(!used)
            inGame.replace(word,true);

        if(used)
            return "redirect:/game/usedWord";

        //answer from server
        for(Map.Entry<String, Boolean> elem: inGame2){
            //take element and check if it isn't used
            char userWordChar = word.toLowerCase().charAt(word.length() - 1);
            if((userWordChar == 'ь') || (userWordChar == 'ы')){
                if((elem.getKey().toLowerCase().charAt(0) == word.charAt(word.length()-2)) && (!elem.getValue())){
                    elem.setValue(true);
                    user.setLastWord(elem.getKey());
                    user.setCities(inGame);
                    userRepository.save(user);
                    return "redirect:/game";
                }
            }else {
                if ((elem.getKey().toLowerCase().charAt(0) == word.charAt(word.length() - 1)) && (!elem.getValue())) {
                    elem.setValue(true);
                    user.setLastWord(elem.getKey());
                    user.setCities(inGame);
                    userRepository.save(user);
                    return "redirect:/game";
                }
            }
        }

        return "redirect:/game/winner";
    }

    @GetMapping("/begin")
    public String gameBegin(Principal principal){
        User user = userRepository.findUserByEmail(principal.getName());

        //get user's status
        UserStatus userStatus = user.getGameStatus();

        //if user's status is waiting, than set status to playing
        if(userStatus.equals(UserStatus.Waiting)){
            user.setGameStatus(UserStatus.Playing);
        }else if(userStatus.equals(UserStatus.Playing)){
            return "redirect:/game";
        }

        citiesService.beginGame(user);
        userRepository.save(user);
        return "redirect:/game";
    }

    @GetMapping("/stop")
    public String gameStop(Principal principal){
        User user = userRepository.findUserByEmail(principal.getName());

        //get user's status
        UserStatus userStatus = user.getGameStatus();

        if(userStatus.equals(UserStatus.Playing)){
            //set user status to waiting
            user.setGameStatus(UserStatus.Waiting);

            //set every city to false + last word to null
            HashMap<String, Boolean> citiesEnter = user.getCities();
            Set<Map.Entry<String, Boolean>> cities = citiesEnter.entrySet();
            for(Map.Entry<String, Boolean> city:cities){
                city.setValue(false);
            }
            user.setCities(citiesEnter);
            user.setLastWord(null);
        }else if(userStatus.equals(UserStatus.Waiting)){
            return "redirect:/game";
        }

        userRepository.save(user);
        return "redirect:/game";
    }

}
