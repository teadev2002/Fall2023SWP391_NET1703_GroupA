package com.swp391.DogCatLoverPlatform.service;

import com.swp391.DogCatLoverPlatform.config.ModelMapperConfig;
import com.swp391.DogCatLoverPlatform.dto.UserDTO;
import com.swp391.DogCatLoverPlatform.entity.RoleEntity;
import com.swp391.DogCatLoverPlatform.entity.UserEntity;
import com.swp391.DogCatLoverPlatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class UserService {
    @Autowired
    ModelMapperConfig modelMapperConfig;

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

    public UserDTO getUserById(int id_user) {
        UserEntity userEntity = userRepository.findById(id_user).orElseThrow();
        UserDTO userDTO = modelMapperConfig.modelMapper().map(userEntity, UserDTO.class);
        return userDTO;
    }

    //Lấy User từ Cookie
    public UserDTO getUserByCookies(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        String email = "null";
        try {
            for (Cookie cookie : cookies) {
                if ("User".equals(cookie.getName())) {
                    email = cookie.getValue();
                    UserDTO userDTO = getUserByEmail(email);
                    System.out.println(userDTO.getId());
                    return userDTO;
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi không tìm thấy Cookies!");
        }
        return null;
    }


    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOTP(String toEmail, String otp, UserDTO userDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp);

//        UserEntity userEntity = new UserEntity();
//        userEntity.setId(userDTO.getId());
//        userEntity.setName(userDTO.getUserName());
//        userEntity.setEmail(userDTO.getEmail());
//        userEntity.setFullName(userDTO.getFullName());
//        userEntity.setAddress(userDTO.getAddress());
//        userEntity.setImage(userDTO.getImage());
//        userEntity.setPhone(userDTO.getPhone());
//
//        RoleEntity roleEntity = new RoleEntity();
//        roleEntity.setId(userDTO.getId_role());
//        userEntity.setRoleEntity(roleEntity);


//        userEntity.setOtp(otp);
        userRepository.updateOtpInUser(otp, userDTO.getId());
        javaMailSender.send(message);
    }

    public void sendEmail(String email) throws IOException, MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        // Sử dụng MimeMessageHelper để xử lý email có phần kèm
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Thiết lập địa chỉ email người nhận và tiêu đề
        helper.setTo(email);
        helper.setSubject("Xác nhận giao dịch");


        Path imagePath = Paths.get("D:\\SWPGIT12\\DogCatLoverPlatform\\src\\main\\resources\\static\\images\\blog\\blog-dog-12.png");
        byte[] imageBytes = Files.readAllBytes(imagePath);
        InputStreamSource imageSource = new ByteArrayResource(imageBytes);
        helper.addAttachment("image.png", imageSource);

        // Thêm nội dung email với hình ảnh
        String emailContent = "<h1>Nhấn vào đây để xác nhận giao dịch:</h1>" +
                "<button><a href=' http://localhost:8080/index/home'>Xác nhận </a   ></button>";
        helper.setText(emailContent, true);



        // Gửi email
        javaMailSender.send(message);
    }

    public UserEntity getUserWithAuthority(HttpServletRequest req){
        String email = null;
        for(Cookie c : req.getCookies()){
            System.out.println("cookie: "+c.getName()+" value: "+c.getValue());
            if(c.getName().equals("User")){
                email = c.getValue();
            }
        }
        if (email == null){
            return null;
        }
        else {
            return userRepository.findByEmail(email);
        }
    }


}
