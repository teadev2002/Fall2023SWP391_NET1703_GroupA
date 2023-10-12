package com.swp391.DogCatLoverPlatform.service;

import com.swp391.DogCatLoverPlatform.dto.BlogDTO;
import com.swp391.DogCatLoverPlatform.dto.BookingDTO;
import com.swp391.DogCatLoverPlatform.dto.UserDTO;
import com.swp391.DogCatLoverPlatform.entity.BlogEntity;
import com.swp391.DogCatLoverPlatform.entity.BookingEntity;
import com.swp391.DogCatLoverPlatform.entity.UserEntity;
import com.swp391.DogCatLoverPlatform.exception.MessageException;
import com.swp391.DogCatLoverPlatform.repository.BlogRepository;
import com.swp391.DogCatLoverPlatform.repository.BookingEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private BookingEntityRepository bookingEntityRepository;

    @Autowired
    private BlogRepository blogRepository;

    public boolean createBooking(BookingEntity booking, UserDTO userDTO){
        UserEntity user = userService.userRepository.findByEmail(userDTO.getEmail());
        Optional<BlogEntity> blog = blogRepository.findById(booking.getBlogEntity_BookingEntity().getId());

        booking.setCreate_date(new java.util.Date(System.currentTimeMillis()));
        booking.setStatus(false);
        booking.setUserEntity_BookingEntity(user);
        booking.setTotal_price(blog.get().getPrice());
        bookingEntityRepository.save(booking);
        return true;
    }

    public List<BookingDTO> getByDateAndIdblog(Date date, Integer idBlog){
        List<BookingEntity> result = bookingEntityRepository.findByDate(date,idBlog);
        List<BookingDTO> dtos = new ArrayList<>();
        for(BookingEntity bookingEntity : result){
            BookingDTO bookingDTO = new BookingDTO();
            bookingDTO.setId(bookingEntity.getId());
            bookingDTO.setBookingDate(bookingEntity.getBookingDate());
            bookingDTO.setBookingTime(bookingEntity.getBookingTime());
            dtos.add(bookingDTO);
        }
        return dtos;
    }
}
