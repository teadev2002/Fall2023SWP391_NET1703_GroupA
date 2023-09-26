package com.swp391.DogCatLoverPlatform.service;

import com.swp391.DogCatLoverPlatform.dto.UserDTO;
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
        user.setName(username);
        userRepository.save(user);
    }

    public List<UserEntity> getAllUser(){
       return userRepository.findAll();
    }

    public void deleteUser(int id){
        userRepository.deleteById(id);
    }


    public boolean checkLogin(String email, String password) {
        UserEntity user = userRepository.findByEmailAndPassword(email, password);
        if(user != null){
            return true;
        }
        return false;
    }

    public boolean checkEmailExist(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if(user != null){
            return true;
        }
        return false;
    }

    public UserDTO getUserByEmail(String email){
        UserEntity user = userRepository.findByEmail(email);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUserName(user.getName());
        userDTO.setFullName(user.getFullName());
        userDTO.setEmail(user.getEmail());
        userDTO.setAddress(user.getAddress());
        userDTO.setImage(user.getImage());
        userDTO.setPhone(user.getPhone());



        return userDTO;
    }
}
