package com.swp391.DogCatLoverPlatform.service;

import com.swp391.DogCatLoverPlatform.dto.UserDTO;
import com.swp391.DogCatLoverPlatform.entity.RoleEntity;
import com.swp391.DogCatLoverPlatform.entity.UserEntity;
import com.swp391.DogCatLoverPlatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    public boolean addUser(UserDTO userDTO){
        boolean isSuccess = false;
        UserEntity user = new UserEntity();
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setName(userDTO.getUserName());
        user.setImage("team-001.jpg");
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(1);
        user.setRoleEntity(roleEntity);
        try {
            userRepository.save(user);
            isSuccess = true;
        }catch (Exception exception){
            System.out.println("Thêm thất bại " + exception.getLocalizedMessage());
            isSuccess = false;
        }

        return isSuccess;
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
        userDTO.setRoleDTO(user.getRoleEntity().getName());
        userDTO.setId_role(user.getRoleEntity().getId());


        return userDTO;
    }


    public boolean updateUser(String fullname, String username, String phone, String address, String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        userEntity.setFullName(fullname);
        userEntity.setName(username);
        userEntity.setAddress(address);
        userEntity.setPhone(phone);
        userRepository.save(userEntity);
        return true;
    }
}
