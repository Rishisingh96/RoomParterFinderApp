package com.rishi.rpf.controllers;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.rishi.rpf.entity.User;
import com.rishi.rpf.repository.UserRepository;
import com.rishi.rpf.services.UserService;


@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;


    // user dashboard page

    @RequestMapping(value = "/dashboard")
    public String userDashboard() {
        System.out.println("User dashboard page");
        return "user/dashboard";
    }

    // user profile page
    @RequestMapping(value = "/profile")
    public String userProfile(Model model,  Authentication authentication) {

        return "user/profile";
    }

    // user add contacts page

    // user view contacts page

    // user edit contacts page

    // user delete contacts page

    // user search contacts page

    @GetMapping("/filter")
    @ResponseBody
    public List<User> filterUsers(
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String area,
            @RequestParam(required = false) String college,
            @RequestParam(required = false) String religion,
            @RequestParam(required = false) String occupation,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String foodPreference
    ) {
        List<User> users = userService.filterUsers(state, city, area, college, religion, occupation, gender, age, foodPreference);
        System.out.println("[FILTER API] Params: state=" + state + ", city=" + city + ", area=" + area + ", college=" + college + ", religion=" + religion + ", occupation=" + occupation + ", gender=" + gender + ", age=" + age + ", foodPreference=" + foodPreference);
        System.out.println("[FILTER API] Matched users: " + users.size());
        for (User u : users) {
            System.out.println("[FILTER API] User: " + u);
        }
        return users;
    }

    // view another user's public profile
    @GetMapping("/view/{id}")
    public String viewUser(@PathVariable("id") String id, Model model) {
        var userOpt = userService.getUserById(id);
        userOpt.ifPresent(u -> model.addAttribute("userView", u));
        return "user/profile_view";
    }
}
