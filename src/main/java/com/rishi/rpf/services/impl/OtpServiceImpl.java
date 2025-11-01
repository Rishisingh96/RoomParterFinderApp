package com.rishi.rpf.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rishi.rpf.entity.Otp;
import com.rishi.rpf.repository.OtpRepository;
import com.rishi.rpf.services.OtpService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class OtpServiceImpl implements OtpService {

    private static final Logger logger = LoggerFactory.getLogger(OtpServiceImpl.class);
    private static final int OTP_LENGTH = 6;
    private static final int OTP_VALIDITY_MINUTES = 10;

    @Autowired
    private OtpRepository otpRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public String generateOtp(String email) {
        try {
            // Delete ALL existing unused OTPs for this email (more reliable than update)
            // This ensures we can create a new OTP without constraint violations
            List<Otp> existingOtps = otpRepository.findByEmailAndUsedFalse(email);
            if (!existingOtps.isEmpty()) {
                otpRepository.deleteAll(existingOtps);
                entityManager.flush(); // Ensure delete is committed
                logger.debug("Deleted {} existing unused OTP(s) for email: {}", existingOtps.size(), email);
            }

            // Generate new 6-digit OTP
            String otpCode = generateRandomOtp();
            
            // Calculate expiry time (10 minutes from now)
            LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(OTP_VALIDITY_MINUTES);

            // Create and save OTP
            Otp otp = Otp.builder()
                    .id(UUID.randomUUID().toString())
                    .email(email)
                    .code(otpCode)
                    .expiryTime(expiryTime)
                    .used(false)
                    .build();

            otpRepository.save(otp);
            entityManager.flush(); // Ensure insert is committed
            logger.info("OTP generated for email: {}", email);

            return otpCode;
        } catch (Exception e) {
            logger.error("Error generating OTP for email: {}", email, e);
            throw e;
        }
    }

    @Override
    @Transactional
    public boolean validateOtp(String email, String otpCode) {
        Optional<Otp> otpOptional = otpRepository.findByEmailAndCodeAndUsedFalse(email, otpCode);
        
        if (otpOptional.isEmpty()) {
            logger.warn("Invalid OTP code for email: {}", email);
            return false;
        }

        Otp otp = otpOptional.get();

        // Check if OTP has expired
        if (LocalDateTime.now().isAfter(otp.getExpiryTime())) {
            logger.warn("OTP expired for email: {}", email);
            otp.setUsed(true);
            otpRepository.save(otp);
            return false;
        }

        // Mark OTP as used
        otp.setUsed(true);
        otpRepository.save(otp);
        logger.info("OTP validated and invalidated successfully for email: {}", email);

        return true;
    }

    @Override
    public boolean verifyOtpWithoutInvalidating(String email, String otpCode) {
        Optional<Otp> otpOptional = otpRepository.findByEmailAndCodeAndUsedFalse(email, otpCode);
        
        if (otpOptional.isEmpty()) {
            logger.warn("Invalid OTP code for email: {}", email);
            return false;
        }

        Otp otp = otpOptional.get();

        // Check if OTP has expired
        if (LocalDateTime.now().isAfter(otp.getExpiryTime())) {
            logger.warn("OTP expired for email: {}", email);
            otp.setUsed(true);
            otpRepository.save(otp);
            return false;
        }

        logger.info("OTP verified (not invalidated) for email: {}", email);
        return true;
    }

    @Override
    public void invalidateOtp(String email, String otpCode) {
        Optional<Otp> otpOptional = otpRepository.findByEmailAndCodeAndUsedFalse(email, otpCode);
        if (otpOptional.isPresent()) {
            Otp otp = otpOptional.get();
            otp.setUsed(true);
            otpRepository.save(otp);
        }
    }

    private String generateRandomOtp() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

}

