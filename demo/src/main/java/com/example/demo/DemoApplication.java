package com.example.demo;
import DTOs.UserDto;
import Entity.User;
import Services.GenerateIDService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import static Services.GenerateIDService.*;

import java.util.LinkedList;
import java.util.List;

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
        var user = Users.stream().filter(x -> x.ID == userEDT.ID).findFirst();
        if(user.isEmpty()) {
            return;
        }
        if (userEDT.Name == null) userEDT.Name = Users.stream().filter(x -> x.ID == userEDT.ID).findFirst()
        Users.set((int)userEDT.ID, userEDT);
    }
    public User editUser(User user){
        User newUser = new User();
        var newUser = Users.stream()
        if(user.Age != 0) newUser.Age = user.Age;
        if(user.Name != null) newUser.Name = user.Name;
        if(user.Address != null) newUser.Address = user.Address;
        if(user.PassportData != null) newUser.PassportData = user.PassportData;
    }
}