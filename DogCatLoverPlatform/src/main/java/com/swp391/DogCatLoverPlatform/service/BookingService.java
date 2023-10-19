package com.swp391.DogCatLoverPlatform.service;

import com.swp391.DogCatLoverPlatform.dto.BlogDTO;
import com.swp391.DogCatLoverPlatform.dto.BookingDTO;
import com.swp391.DogCatLoverPlatform.dto.UserDTO;
import com.swp391.DogCatLoverPlatform.entity.BlogEntity;
import com.swp391.DogCatLoverPlatform.entity.BookingEntity;
import com.swp391.DogCatLoverPlatform.entity.UserEntity;
import com.swp391.DogCatLoverPlatform.repository.BlogRepository;
import com.swp391.DogCatLoverPlatform.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private BookingRepository bookingRepository;

    @Autowired
    private BlogRepository blogRepository;

    public boolean createBooking(BookingEntity booking, UserDTO userDTO){
        UserEntity user = userService.userRepository.findByEmail(userDTO.getEmail());
        Optional<BlogEntity> blog = blogRepository.findById(booking.getBlogEntity_BookingEntity().getId());

        booking.setCreate_date(new java.util.Date(System.currentTimeMillis()));
        booking.setStatus(false);
        booking.setUserEntity_BookingEntity(user);
        booking.setTotal_price(blog.get().getPrice());
        bookingRepository.save(booking);
        return true;
    }

    public boolean updateBookingStatus(int bookingId) {
        Optional<BookingEntity> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isPresent()) {
            BookingEntity booking = bookingOptional.get();
            booking.setStatus(true);
            bookingRepository.save(booking);
            return true;
        }
        return false;
    }

    public boolean removeOrderFromCart(int bookingId) {
        Optional<BookingEntity> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isPresent()) {
            bookingRepository.delete(bookingOptional.get());
            return true;
        }
        return false;
    }




    public List<BookingDTO> getByDateAndIdblog(Date date, Integer idBlog){
        List<BookingEntity> result = bookingRepository.findByDate(date,idBlog);
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

    public List<BookingDTO> getBookingManager(int id_user){
        List<BookingEntity> bookingEntities = bookingRepository.findByUserCreate(id_user);

        List<BookingDTO> bookingDTOList = new ArrayList<>();
        for(BookingEntity booking : bookingEntities){
            BlogDTO blogDTO = blogService.getBlogById(booking.getBlogEntity_BookingEntity().getId());
            UserDTO userDTO = userService.getUserById(booking.getUserEntity_BookingEntity().getId());
            BookingDTO bookingDTO = new BookingDTO();
            bookingDTO.setId(booking.getId());
            bookingDTO.setBookingDate(booking.getBookingDate());
            bookingDTO.setBookingTime(booking.getBookingTime());
            bookingDTO.setTotal_price(booking.getTotal_price());
            bookingDTO.setPaying_method(booking.getPaying_method());
            bookingDTO.setBlogDTO(blogDTO);
            bookingDTO.setUserDTO(userDTO);
            bookingDTOList.add(bookingDTO);
        }
        return bookingDTOList;
    }

    public List<BookingDTO> findByUserBooking(int id_user) {
        List<BookingEntity> bookingEntities = bookingRepository.findByUserBooking(id_user);

        List<BookingDTO> bookingDTOList = new ArrayList<>();
        for(BookingEntity booking : bookingEntities){
            BlogDTO blogDTO = blogService.getBlogById(booking.getBlogEntity_BookingEntity().getId());
            UserDTO userDTO = userService.getUserById(booking.getUserEntity_BookingEntity().getId());
            BookingDTO bookingDTO = new BookingDTO();
            bookingDTO.setId(booking.getId());
            bookingDTO.setBookingDate(booking.getBookingDate());
            bookingDTO.setBookingTime(booking.getBookingTime());
            bookingDTO.setTotal_price(booking.getTotal_price());
            bookingDTO.setPaying_method(booking.getPaying_method());
            bookingDTO.setBlogDTO(blogDTO);
            bookingDTO.setUserDTO(userDTO);
            bookingDTOList.add(bookingDTO);
        }
        return bookingDTOList;
    }
}
