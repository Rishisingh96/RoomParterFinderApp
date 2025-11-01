package com.rishi.rpf.services;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
