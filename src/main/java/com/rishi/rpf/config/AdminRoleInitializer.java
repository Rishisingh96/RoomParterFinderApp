package com.rishi.rpf.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.rishi.rpf.entity.User;
import com.rishi.rpf.helpers.AppConstants;
import com.rishi.rpf.repository.UserRepository;

/**
 * Ensures that the main admin user always has ROLE_ADMIN assigned.
 * This is helpful if the user was created earlier without the admin role.
 */
@Component
public class AdminRoleInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AdminRoleInitializer.class);

    // Change this email if you want a different primary admin
    private static final String ADMIN_EMAIL = "rishisinghdev98@gmail.com";

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) {
        userRepository.findByEmail(ADMIN_EMAIL).ifPresent(user -> {
            ensureAdminRole(user);
        });
    }

    private void ensureAdminRole(User user) {
        if (!user.getRoleList().contains(AppConstants.ROLE_ADMIN)) {
            user.getRoleList().add(AppConstants.ROLE_ADMIN);
            userRepository.save(user);
            logger.info("Assigned ROLE_ADMIN to user with email: {}", user.getEmail());
        } else {
            logger.info("User with email {} already has ROLE_ADMIN", user.getEmail());
        }
    }
}


