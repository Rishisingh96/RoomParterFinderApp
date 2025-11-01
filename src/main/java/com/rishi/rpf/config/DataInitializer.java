package com.rishi.rpf.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.rishi.rpf.entity.Providers;
import com.rishi.rpf.entity.User;
import com.rishi.rpf.helpers.AppConstants;
import com.rishi.rpf.services.UserService;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if admin user already exists
        if (userService.isUserExistByEmail("rishisinghdev98@gmail.com")) {
            System.out.println("Admin user already exists: rishisinghdev98@gmail.com");
            return;
        }

        // Create admin user
        User admin = User.builder()
                .name("Admin User")
                .email("rishisinghdev98@gmail.com")
                .password("Rishi@9838")
                .about("System Administrator")
                .enabled(true)
                .emailVerified(true)
                .phoneVerified(false)
                .provider(Providers.SELF)
                .roleList(List.of(AppConstants.ROLE_ADMIN, AppConstants.ROLE_USER))
                .build();

        userService.saveUser(admin);
        System.out.println("Admin user created: rishisinghdev98@gmail.com");
    }
}
