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

    public void sendInvoiceEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mintampro4545@gmail.com"); // Update with your email
        message.setTo(to);
        message.setSubject("Invoice Payment");
        message.setText("this is invoice" +content);

        javaMailSender.send(message);
    }
}
