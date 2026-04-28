package com.forthems.ftnlabel.service;

// Services do the work/logic

import com.forthems.ftnlabel.model.User;
import org.springframework.stereotype.Service;

// Service = Chef(behind the scenes cooking)
@Service // Create an object of this class automatically.
// Show this class is a service.
// spring sees @Service and stores it internally;
// UserService userService = new UserService();
public class UserService {

    public User createUser(User user) { // user object sent from controller
        System.out.println("User name from service: " + user.getName());
        System.out.println("User age from service: " + user.getAge());
        //sout is optional, we just want to return user in JSON form
        return user;
    }

    public User showAgeUser() {
        User user = new User();
        // creates user with age 20 Manually.
        user.setAge(20);
        return user;
    }
}
