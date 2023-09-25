package com.example.demo;
import DTOs.UserDto;
import Entity.User;
import Services.GenerateIDService;
import Services.UserValidationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import static Services.GenerateIDService.*;
import static Services.UserValidationService.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
public class DemoApplication {

    private static List<User> Users = new LinkedList<>();

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/getUser")
    public List<User> getUsers()
    {
       return Users;
    }

    @PutMapping("/addUser")
    public void AddUser(@RequestBody UserDto userDto)
    {
        var newUser = new User();
        newUser.Age = userDto.Age;
        newUser.Name = userDto.Name;
        newUser.Address = userDto.Address;
        newUser.PassportData = userDto.PassportData;
        newUser.ID = GenerateID(Users);
        Users.add(newUser);
    }
    @PatchMapping("/editUser")
    public void editUser(@RequestBody User userEDT)
    {
        findUser(Users, userEDT.ID)
                .ifPresent(u -> {
                    var changedUser = new User();
                    changedUser.ID = userEDT.ID;
                    changedUser.Name = ValidateString(userEDT.Name) ? u.Name : userEDT.Name;
                    changedUser.Address = ValidateString(userEDT.Address) ? u.Address : userEDT.Address;
                    changedUser.PassportData = ValidateString(userEDT.PassportData) ? u.PassportData : userEDT.PassportData;

                    Users.set(Users.indexOf(u), changedUser);
                });
    }
    private Optional<User> findUser(List<User> users, long id){
        var userOpt = users.stream()
                .filter(u -> u.ID == id)
                .findFirst();

        return userOpt;
    }
}