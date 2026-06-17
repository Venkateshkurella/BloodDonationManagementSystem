package com.Blood.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {

    @Value("${spring.mail.username:}")
    private String senderEmail;

    @Value("${BREVO_API_KEY:${spring.mail.password:}}")
    private String apiKey;

    public void sendVerificationCode(String targetEmail, String code) {
        System.out.println("[OTP Simulation] Generated OTP Code for " + targetEmail + " is: " + code);

        if (apiKey == null || apiKey.trim().isEmpty()) {
            System.out.println("Brevo API Key (spring.mail.password) is missing. Skipping email delivery.");
            return;
        }

        try {
            System.out.println("Attempting to send email via Brevo REST API...");
            System.out.println("Recipient Email: " + targetEmail);

            RestTemplate restTemplate = new RestTemplate();
            String url = "https://api.brevo.com/v3/smtp/email";

            // Set up headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            headers.set("api-key", apiKey);

            // Construct payload JSON using Map
            Map<String, Object> body = new HashMap<>();
            
            // Sender info
            Map<String, String> sender = new HashMap<>();
            sender.put("name", "Blood Donation");
            // Use verified sender. If senderEmail contains '@smtp-brevo.com', it's a login username and not a verified sender.
            String fromEmail = "venkateshkurella369@gmail.com";
            if (senderEmail != null && senderEmail.contains("@") && !senderEmail.contains("smtp-brevo.com")) {
                fromEmail = senderEmail;
            }
            sender.put("email", fromEmail);
            body.put("sender", sender);

            // To info
            Map<String, String> recipient = new HashMap<>();
            recipient.put("email", targetEmail);
            body.put("to", List.of(recipient));

            // Subject and content
            body.put("subject", "Blood Donation Verification Code");
            body.put("htmlContent", "Thank you for registering.<br><br>Your OTP is: <b>" + code + "</b><br><br>This OTP is valid for 10 minutes.");

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("OTP sent successfully to: " + targetEmail + " via Brevo REST API!");
                System.out.println("Response: " + response.getBody());
            } else {
                System.out.println("Failed to send OTP. Status: " + response.getStatusCode() + ", Body: " + response.getBody());
            }

        } catch (Exception e) {
            System.out.println("Email sending via Brevo REST API failed");
            e.printStackTrace();
        }
    }
}