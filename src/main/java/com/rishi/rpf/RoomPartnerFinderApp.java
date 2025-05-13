package com.rishi.rpf;

import com.rishi.rpf.entity.User;
import com.rishi.rpf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@SpringBootApplication
public class RoomPartnerFinderApp {

//	static UserRepository userRepository;
//	static PasswordEncoder passwordEncoder;
//
//	public RoomPartnerFinderApp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//		this.userRepository = userRepository;
//		this.passwordEncoder = passwordEncoder;
//	}

	public static void main(String[] args) {
		SpringApplication.run(RoomPartnerFinderApp.class, args);
//		User user = new User();
//		user.setUserId(UUID.randomUUID().toString());
//		user.setName("Rishi");
//		user.setEmail("rishi@gmail.com");
//		user.setPassword(passwordEncoder.encode("rishi"));
//
//		userRepository.findByEmail("rishi@gmail.com").ifPresentOrElse(user1 -> {},()->{
//			userRepository.save(user);
//			System.out.println("User saved");
//		});

	}
}
