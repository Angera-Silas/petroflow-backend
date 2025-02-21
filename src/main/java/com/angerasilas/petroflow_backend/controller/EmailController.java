package com.angerasilas.petroflow_backend.controller;

import java.util.Random;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.angerasilas.petroflow_backend.dto.EmailRequestDTO;
import com.angerasilas.petroflow_backend.service.EmailService;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Send a general email with custom subject, body, and recipients.
     */
    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailRequestDTO emailRequest) {
        emailService.sendEmail(emailRequest);
        return "Email sent successfully!";
    }

    /**
     * Send an OTP to the given email for password reset.
     */
    @PostMapping("/send-otp")
    public String sendOtp(@RequestBody EmailRequestDTO emailRequest) {
        if (emailRequest.getTo() == null || emailRequest.getTo().isEmpty()) {
            throw new IllegalArgumentException("Email address is required.");
        }

        String otp = generateOtp();

        // Prepare OTP email
        emailRequest.setSubject("🔐 Password Reset Request");
        emailRequest.setBody(
                "<p>Dear User,</p>" +
                        "<p>We received a request to reset your password. To proceed, please use the following One-Time Password (OTP):</p>"
                        +
                        "<h2 style='color: #d32f2f; text-align: center;'>" + otp + "</h2>" +
                        "<p>This OTP is valid for a limited time and will expire shortly.</p>" +
                        "<p><strong>If you did not request a password reset, please ignore this email.</strong></p>" +
                        "<hr>" +
                        "<p>Thank you,</p>" +
                        "<p><strong>PetroFlow Support Team</strong></p>");

        emailService.sendEmail(emailRequest);
        return otp; // Store in frontend local storage or cookies
    }

    /**
     * Generate a random 6-digit OTP.
     */
    private String generateOtp() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }
}
