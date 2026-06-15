package com.Blood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public void sendVerificationCode(String targetEmail, String code) {

        try {
            System.out.println("Sender Email: " + senderEmail);
            System.out.println("Recipient Email: " + targetEmail);

            SimpleMailMessage message = new SimpleMailMessage();

            
            message.setFrom("venkateshkurella369@gmail.com");
            message.setTo(targetEmail);
            message.setSubject("Blood Donation Verification Code");

            message.setText(
                    "Thank you for registering.\n\n"
                    + "Your OTP is: " + code
                    + "\n\nThis OTP is valid for 10 minutes.");

            mailSender.send(message);

            System.out.println("OTP sent successfully to: " + targetEmail);

        } catch (Exception e) {

            System.out.println("Email sending failed");
            e.printStackTrace();

        }
    }
}