package com.swp391.DogCatLoverPlatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String reason) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("minhtampro4545@gmail.com");
        message.setTo(to);
        message.setSubject("Blog Notice");
        message.setText("From Scooby with love:\n\n" + reason);

        javaMailSender.send(message);
    }
}
