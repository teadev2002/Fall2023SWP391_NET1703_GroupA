package com.swp391.DogCatLoverPlatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendRejectionEmail(String to, String reason) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("minhtampro4545@gmail.com");
        message.setTo(to);
        message.setSubject("Blog Rejection Notice");
        message.setText("Your blog has been rejected due to the following reason:\n\n" + reason);

        javaMailSender.send(message);
    }
}
