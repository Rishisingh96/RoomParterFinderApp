package com.rishi.rpf.services;

import java.util.List;

import com.rishi.rpf.entity.User;

public interface AdminService {
    List<User> listAllUsers();
    void enableUser(String userId);
    void disableUser(String userId);
    void promoteToAdmin(String userId);
    void demoteToUser(String userId);
    void deleteUser(String userId);
    long totalUsers();
    long totalAdmins();
}


