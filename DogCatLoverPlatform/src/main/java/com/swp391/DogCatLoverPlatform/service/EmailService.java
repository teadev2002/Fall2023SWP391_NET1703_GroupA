package com.swp391.DogCatLoverPlatform.service;

import com.swp391.DogCatLoverPlatform.entity.PetCategoryEntity;
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

    public void sendEmailPay(String to, String reason, PetCategoryEntity petCategory) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("minhtampro4545@gmail.com");
        message.setTo(to);
        message.setSubject("Blog Notice");

        // Construct the email message
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("From Scooby with love:\n\n");
        emailContent.append(reason);
        emailContent.append("\n\nPet Category Information:\n");
        emailContent.append("Name: ").append(petCategory.getName()).append("\n");
        emailContent.append("Breed: ").append(petCategory.getBreed()).append("\n");
        emailContent.append("Age: ").append(petCategory.getAge()).append(" months\n");
        emailContent.append("Weight: ").append(petCategory.getWeight()).append(" kg\n");
        emailContent.append("Color: ").append(petCategory.getColor()).append("\n");
        message.setText(emailContent.toString());

        javaMailSender.send(message);
    }
}
