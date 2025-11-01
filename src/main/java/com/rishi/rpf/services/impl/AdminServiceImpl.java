package com.rishi.rpf.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rishi.rpf.entity.User;
import com.rishi.rpf.helpers.AppConstants;
import com.rishi.rpf.repository.UserRepository;
import com.rishi.rpf.services.AdminService;
import com.rishi.rpf.services.EmailService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void enableUser(String userId) {
        userRepository.findById(userId).ifPresent(u -> {
            u.setEnabled(true);
            userRepository.save(u);
            try{ emailService.sendEmail(u.getEmail(), "Account Enabled", "Hello "+u.getName()+", your account has been enabled by admin."); }catch(Exception ignored){}
        });
    }

    @Override
    public void disableUser(String userId) {
        userRepository.findById(userId).ifPresent(u -> {
            u.setEnabled(false);
            userRepository.save(u);
            try{ emailService.sendEmail(u.getEmail(), "Account Disabled", "Hello "+u.getName()+", your account has been disabled by admin."); }catch(Exception ignored){}
        });
    }

    @Override
    public void promoteToAdmin(String userId) {
        userRepository.findById(userId).ifPresent(u -> {
            if (!u.getRoleList().contains(AppConstants.ROLE_ADMIN)) {
                u.getRoleList().add(AppConstants.ROLE_ADMIN);
                userRepository.save(u);
                try{ emailService.sendEmail(u.getEmail(), "Role Updated", "Hello "+u.getName()+", you have been granted admin role."); }catch(Exception ignored){}
            }
        });
    }

    @Override
    public void demoteToUser(String userId) {
        userRepository.findById(userId).ifPresent(u -> {
            u.getRoleList().remove(AppConstants.ROLE_ADMIN);
            userRepository.save(u);
            try{ emailService.sendEmail(u.getEmail(), "Role Updated", "Hello "+u.getName()+", your admin role has been removed."); }catch(Exception ignored){}
        });
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.findById(userId).ifPresent(u -> {
            userRepository.deleteById(userId);
            try{ emailService.sendEmail(u.getEmail(), "Account Deleted", "Hello "+u.getName()+", your account has been deleted by admin."); }catch(Exception ignored){}
        });
    }

    @Override
    public long totalUsers() {
        return userRepository.count();
    }

    @Override
    public long totalAdmins() {
        return userRepository.findAll().stream()
                .filter(u -> u.getRoleList().contains(AppConstants.ROLE_ADMIN))
                .count();
    }
}


