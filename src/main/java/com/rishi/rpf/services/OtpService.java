package com.rishi.rpf.services;

public interface OtpService {
    
    String generateOtp(String email);
    
    boolean validateOtp(String email, String otpCode);
    
    boolean verifyOtpWithoutInvalidating(String email, String otpCode);
    
    void invalidateOtp(String email, String otpCode);
    
}

