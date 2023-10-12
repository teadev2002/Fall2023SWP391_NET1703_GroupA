package com.swp391.DogCatLoverPlatform.controller;

import com.swp391.DogCatLoverPlatform.dto.BlogDTO;
import com.swp391.DogCatLoverPlatform.dto.BookingDTO;
import com.swp391.DogCatLoverPlatform.dto.UserDTO;
import com.swp391.DogCatLoverPlatform.entity.BlogEntity;
import com.swp391.DogCatLoverPlatform.entity.BookingEntity;
import com.swp391.DogCatLoverPlatform.entity.UserEntity;
import com.swp391.DogCatLoverPlatform.exception.MessageException;
import com.swp391.DogCatLoverPlatform.repository.BlogRepository;
import com.swp391.DogCatLoverPlatform.repository.BookingEntityRepository;
import com.swp391.DogCatLoverPlatform.service.BlogService;
import com.swp391.DogCatLoverPlatform.service.BookingService;
import com.swp391.DogCatLoverPlatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/manager")
    public String manager(Model model, HttpServletRequest req) {
        UserDTO userDTO = getUserIdFromCookie(req);

        if(userDTO == null){
            return "redirect:/index/login";
        }
        List<BookingEntity> list = bookingEntityRepository.findByUserCreate(userDTO.getId());
        model.addAttribute("listBooking", list);
        model.addAttribute("quantity", list.size());

        return "booking-manager";
    }


    @Autowired
    private BookingEntityRepository bookingEntityRepository;

    @Autowired
    private BlogRepository blogRepository;



    @GetMapping("/booking-by-date-and-blog")
    public ResponseEntity<?> findByDateAndBlog(@RequestParam("date") Date date, @RequestParam("id") Integer idBlog){
        List<BookingDTO> dtos = bookingService.getByDateAndIdblog(date,idBlog);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping("/create-booking")
    public ResponseEntity<?> create(@RequestBody BookingEntity booking, HttpServletRequest req) {
        UserDTO user = getUserIdFromCookie(req);
        boolean result = false;
        if(user == null){
            throw new MessageException("Bạn chưa đăng nhập",444);
        }else{
            result = bookingService.createBooking(booking,user);
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    //GetUserIdFromCookie cực kỳ quan trọng!!!
    private UserDTO getUserIdFromCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        UserDTO userDTO = new UserDTO();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("User".equals(cookie.getName())) {
                    String email = cookie.getValue();
                    userDTO = userService.getUserByEmail(email);
                    return userDTO;
                }
            }
        }
        return null;
    }
}
