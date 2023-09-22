package com.swp391.DogCatLoverPlatform.service;

import com.swp391.DogCatLoverPlatform.entity.UserEntity;
import com.swp391.DogCatLoverPlatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    public static int increase = 0;

    @Autowired
    UserRepository userRepository;

    public void addUser(String fullName, String password, String email, String username){
        UserEntity user = new UserEntity();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);
        user.setUserName(username);
        userRepository.save(user);
    }

    public List<UserEntity> getAllUser(){
       return userRepository.findAll();
    }

    public void deleteUser(int id){
        userRepository.deleteById(id);
    }



}
