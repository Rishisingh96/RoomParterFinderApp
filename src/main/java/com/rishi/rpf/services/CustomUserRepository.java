package com.rishi.rpf.services;

import java.util.List;

import com.rishi.rpf.entity.User;

public interface CustomUserRepository {
    List<User> filterUsers(String state, String city, String area, String college,
                           String religion, String occupation, String gender,
                           Integer age, String foodPreference);
}


