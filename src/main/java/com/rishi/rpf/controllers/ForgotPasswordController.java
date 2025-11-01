package com.rishi.rpf.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rishi.rpf.entity.User;
import com.rishi.rpf.forms.ForgotPasswordForm;
import com.rishi.rpf.forms.ResetPasswordForm;
import com.rishi.rpf.forms.VerifyOtpForm;
import com.rishi.rpf.helpers.Message;
import com.rishi.rpf.helpers.MessageType;
import com.rishi.rpf.services.EmailService;
import com.rishi.rpf.services.OtpService;
import com.rishi.rpf.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/forgot-password")
public class ForgotPasswordController {

    private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailService emailService;

    @GetMapping
    public String forgotPasswordPage(Model model) {
        model.addAttribute("forgotPasswordForm", new ForgotPasswordForm());
        return "forgot-password";
    }

    @PostMapping("/request-otp")
    public String requestOtp(@Valid @ModelAttribute ForgotPasswordForm forgotPasswordForm,
            BindingResult bindingResult, Model model, HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "forgot-password";
        }

        String email = forgotPasswordForm.getEmail();
        User user = userService.getUserByEmail(email);

        if (user == null) {
            Message message = Message.builder()
                    .content("Email not found. Please check your email address.")
                    .type(MessageType.red)
                    .build();
            session.setAttribute("message", message);
            return "redirect:/forgot-password";
        }

        // Generate and send OTP
        try {
            String otp = otpService.generateOtp(email);
            logger.info("OTP generated for email: {}", email);
            
            String emailBody = String.format(
                "Hello %s,\n\n" +
                "You have requested to reset your password. Your OTP is: %s\n\n" +
                "This OTP is valid for 10 minutes.\n\n" +
                "If you did not request this, please ignore this email.\n\n" +
                "Best regards,\nRoom Partner Finder Team",
                user.getName(), otp
            );

            try {
                emailService.sendEmail(email, "Password Reset OTP - Room Partner Finder", emailBody);
                logger.info("OTP email sent successfully to: {}", email);
                
                Message message = Message.builder()
                        .content("OTP has been sent to your email. Please check your inbox.")
                        .type(MessageType.green)
                        .build();
                session.setAttribute("message", message);

                // Redirect to verify OTP page with email in session
                session.setAttribute("resetPasswordEmail", email);
                return "redirect:/forgot-password/verify-otp";
            } catch (Exception emailEx) {
                logger.error("Failed to send OTP email to: {}. Error: {}", email, emailEx.getMessage(), emailEx);
                
                // Provide more detailed error message
                String errorMessage = "Failed to send OTP email. ";
                if (emailEx.getMessage() != null && emailEx.getMessage().contains("authentication")) {
                    errorMessage += "Email authentication failed. Please check email configuration.";
                } else if (emailEx.getMessage() != null && emailEx.getMessage().contains("connection")) {
                    errorMessage += "Cannot connect to email server. Please try again later.";
                } else {
                    errorMessage += "Please check your email configuration or try again later.";
                }
                
                Message message = Message.builder()
                        .content(errorMessage)
                        .type(MessageType.red)
                        .build();
                session.setAttribute("message", message);
                return "redirect:/forgot-password";
            }
            
        } catch (Exception e) {
            logger.error("Error generating or sending OTP for email: {}. Error: {}", email, e.getMessage(), e);
            Message message = Message.builder()
                    .content("Failed to process OTP request. Please try again later.")
                    .type(MessageType.red)
                    .build();
            session.setAttribute("message", message);
            return "redirect:/forgot-password";
        }
    }

    @GetMapping("/verify-otp")
    public String verifyOtpPage(Model model, HttpSession session) {
        String email = (String) session.getAttribute("resetPasswordEmail");
        
        if (email == null) {
            Message message = Message.builder()
                    .content("Please request OTP first.")
                    .type(MessageType.red)
                    .build();
            session.setAttribute("message", message);
            return "redirect:/forgot-password";
        }

        VerifyOtpForm verifyOtpForm = new VerifyOtpForm();
        verifyOtpForm.setEmail(email);
        model.addAttribute("verifyOtpForm", verifyOtpForm);
        return "verify-otp";
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@Valid @ModelAttribute VerifyOtpForm verifyOtpForm,
            BindingResult bindingResult, Model model, HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "verify-otp";
        }

        String email = verifyOtpForm.getEmail();
        String otpCode = verifyOtpForm.getOtp();

        // Verify OTP without invalidating (we'll invalidate when password is reset)
        boolean isValid = otpService.verifyOtpWithoutInvalidating(email, otpCode);

        if (!isValid) {
            Message message = Message.builder()
                    .content("Invalid or expired OTP. Please try again.")
                    .type(MessageType.red)
                    .build();
            session.setAttribute("message", message);
            return "verify-otp";
        }

        // OTP is valid, redirect to reset password page
        ResetPasswordForm resetPasswordForm = new ResetPasswordForm();
        resetPasswordForm.setEmail(email);
        resetPasswordForm.setOtp(otpCode);
        model.addAttribute("resetPasswordForm", resetPasswordForm);
        session.setAttribute("resetPasswordEmail", email);
        session.setAttribute("verifiedOtp", otpCode);

        return "reset-password";
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage(Model model, HttpSession session) {
        String email = (String) session.getAttribute("resetPasswordEmail");
        String otp = (String) session.getAttribute("verifiedOtp");

        if (email == null || otp == null) {
            Message message = Message.builder()
                    .content("Please verify OTP first.")
                    .type(MessageType.red)
                    .build();
            session.setAttribute("message", message);
            return "redirect:/forgot-password";
        }

        ResetPasswordForm resetPasswordForm = new ResetPasswordForm();
        resetPasswordForm.setEmail(email);
        resetPasswordForm.setOtp(otp);
        model.addAttribute("resetPasswordForm", resetPasswordForm);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@Valid @ModelAttribute ResetPasswordForm resetPasswordForm,
            BindingResult bindingResult, Model model, HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "reset-password";
        }

        String email = resetPasswordForm.getEmail();
        String otp = resetPasswordForm.getOtp();
        String newPassword = resetPasswordForm.getNewPassword();
        String confirmPassword = resetPasswordForm.getConfirmPassword();

        // Verify OTP again (security check)
        boolean isValidOtp = otpService.validateOtp(email, otp);
        if (!isValidOtp) {
            Message message = Message.builder()
                    .content("OTP expired or invalid. Please start the process again.")
                    .type(MessageType.red)
                    .build();
            session.setAttribute("message", message);
            return "redirect:/forgot-password";
        }

        // Check if passwords match
        if (!newPassword.equals(confirmPassword)) {
            Message message = Message.builder()
                    .content("Passwords do not match. Please try again.")
                    .type(MessageType.red)
                    .build();
            session.setAttribute("message", message);
            model.addAttribute("resetPasswordForm", resetPasswordForm);
            return "reset-password";
        }

        // Reset password
        boolean success = userService.resetPassword(email, newPassword);

        if (success) {
            // Invalidate OTP
            otpService.invalidateOtp(email, otp);
            
            // Clear session attributes
            session.removeAttribute("resetPasswordEmail");
            session.removeAttribute("verifiedOtp");

            // Send confirmation email
            try {
                User user = userService.getUserByEmail(email);
                String emailBody = String.format(
                    "Hello %s,\n\n" +
                    "Your password has been successfully reset.\n\n" +
                    "If you did not make this change, please contact support immediately.\n\n" +
                    "Best regards,\nRoom Partner Finder Team",
                    user.getName()
                );
                emailService.sendEmail(email, "Password Reset Confirmation - Room Partner Finder", emailBody);
            } catch (Exception e) {
                logger.warn("Failed to send confirmation email: {}", e.getMessage());
            }

            Message message = Message.builder()
                    .content("Password reset successfully! You can now login with your new password.")
                    .type(MessageType.green)
                    .build();
            session.setAttribute("message", message);
            return "redirect:/login";
        } else {
            Message message = Message.builder()
                    .content("Failed to reset password. Please try again.")
                    .type(MessageType.red)
                    .build();
            session.setAttribute("message", message);
            return "reset-password";
        }
    }

}

