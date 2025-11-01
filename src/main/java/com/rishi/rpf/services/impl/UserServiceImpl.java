package com.rishi.rpf.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rishi.rpf.entity.Providers;
import com.rishi.rpf.entity.User;
import com.rishi.rpf.helpers.AppConstants;
import com.rishi.rpf.helpers.Helper;
import com.rishi.rpf.helpers.ResourceNotFoundException;
import com.rishi.rpf.repository.UserRepository;
import com.rishi.rpf.services.EmailService;
import com.rishi.rpf.services.UserService;

@Service
public class UserServiceImpl implements UserService {
  
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;


    private EmailService emailService;

    private Helper helper;

    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
            Helper helper, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.helper = helper;
        this.emailService = emailService;
    }

    

    @Override
    public User saveUser(User user) {
        // TODO Auto-generated method stub
        // user id : have to generate
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        // password encode
        // user.setPassword(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // set the user role
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        // user.setProfile(user)

        // Ensure provider is set (default to SELF if null)
        if (user.getProvider() == null) {
            user.setProvider(Providers.SELF);
        }
        logger.info("User provider: {}", user.getProvider());

        String emailToken = UUID.randomUUID().toString();
        // user.setEmailToken(emailToken);
        User savedUser = userRepository.save(user);
        try{
            emailService.sendEmail(savedUser.getEmail(), "Welcome to Room Partner Finder", "Hello " + savedUser.getName() + ", your account has been created successfully.");
        }catch(Exception ex){
            logger.warn("Failed to send welcome email: {}", ex.getMessage());
        }
        return savedUser;
        // return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        // TODO Auto-generated method stub
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        // TODO: Auto-generated method stub
        User user2 = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderUserId(user.getProviderUserId());

        // save the user in database
        User save = userRepository.save(user2);
        return Optional.ofNullable(save);
    }

    @Override
    public void deleteUser(String id) {
        User user2 = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(user2);
    }

    @Override
    public boolean isUserExist(String userId) {
        User user2 = userRepository.findById(userId).orElse(null);
        return user2 != null ? true : false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        return user != null ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public List<User> filterUsers(String state, String city, String area, String college, String religion, String occupation, String gender, Integer age, String foodPreference) {
        return userRepository.filterUsers(state, city, area, college, religion, occupation, gender, age, foodPreference);
    }

    @Override
    public boolean resetPassword(String email, String newPassword) {
        User user = getUserByEmail(email);
        if (user == null) {
            logger.warn("User not found for email: {}", email);
            return false;
        }
        
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        logger.info("Password reset successfully for email: {}", email);
        return true;
    }

}
