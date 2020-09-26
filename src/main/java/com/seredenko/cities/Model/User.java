package com.seredenko.cities.Model;



import com.seredenko.cities.Status.UserStatus;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Entity(name = "users")
public class User {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Id
    @Email
    @NotEmpty
    @Column(unique = true)
    private String email;

    @Size(min = 4)
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLES", joinColumns={
            @JoinColumn(name = "USER_EMAIL", referencedColumnName = "email") }, inverseJoinColumns = {
            @JoinColumn(name = "ROLE_NAME", referencedColumnName = "name") })
    private List<Role> roles;

    private UserStatus gameStatus;

    private Integer words;

    private HashMap<String, Boolean> cities;

    private String lastWord;

    public User() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(UserStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Integer getWords() {
        return words;
    }

    public void setWords(Integer words) {
        this.words = words;
    }

    public HashMap<String, Boolean> getCities() {
        return cities;
    }

    public void setCities(HashMap<String, Boolean> cities) {
        this.cities = cities;
    }

    public String getLastWord() {
        return lastWord;
    }

    public void setLastWord(String lastWord) {
        this.lastWord = lastWord;
    }
}