package com.rishi.rpf.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rishi.rpf.entity.User;
import com.rishi.rpf.helpers.AppConstants;
import com.rishi.rpf.helpers.Helper;
import com.rishi.rpf.helpers.ResourceNotFoundException;
import com.rishi.rpf.repository.UserRepository;
// import com.rishi.scm.services.EmailService;
import com.rishi.rpf.services.UserService;

@Service
public class UserServiceImpl implements UserService {
  
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;


    // private EmailService emailService;

    private Helper helper;

    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
            Helper helper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.helper = helper;
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

        logger.info(user.getProvider().toString());

        String emailToken = UUID.randomUUID().toString();
        // user.setEmailToken(emailToken);
        User savedUser = userRepository.save(user);
        // String emailLink = Helper.getLinkForEmailVerification(emailToken);
        // emailService.sendEmail(savedUser.getEmail(), "Verify Account : Smart  Contact Manager", emailLink);
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

}
