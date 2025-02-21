package com.angerasilas.petroflow_backend.service.impl;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.angerasilas.petroflow_backend.dto.EmailRequestDTO;
import com.angerasilas.petroflow_backend.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(EmailRequestDTO emailRequest) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            helper.setTo(emailRequest.getTo());
            if (emailRequest.getCc() != null) {
                helper.setCc(emailRequest.getCc());
            }
            if (emailRequest.getBcc() != null) {
                helper.setBcc(emailRequest.getBcc());
            }
            helper.setSubject(emailRequest.getSubject());
            helper.setText(emailRequest.getBody(), true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending email: " + e.getMessage());
        }
    }
}
