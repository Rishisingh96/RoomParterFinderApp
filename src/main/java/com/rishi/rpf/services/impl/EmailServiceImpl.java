package com.rishi.rpf.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.rishi.rpf.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendEmail(String to, String subject, String body) throws MailException {
        try {
            logger.info("Attempting to send email to: {}", to);
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(body);
            msg.setFrom(fromEmail);
            mailSender.send(msg);
            logger.info("Email sent successfully to: {}", to);
        } catch (MailException e) {
            logger.error("Failed to send email to: {}. Error: {}", to, e.getMessage(), e);
            throw e; // Re-throw to let the caller handle it
        } catch (Exception e) {
            logger.error("Unexpected error while sending email to: {}. Error: {}", to, e.getMessage(), e);
            throw new MailSendException("Failed to send email: " + e.getMessage(), e);
        }
    }
}
