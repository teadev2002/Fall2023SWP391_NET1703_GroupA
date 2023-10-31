package com.swp391.DogCatLoverPlatform.service;

import com.swp391.DogCatLoverPlatform.dto.BlogDTO;
import com.swp391.DogCatLoverPlatform.dto.BookingDTO;
import com.swp391.DogCatLoverPlatform.entity.BlogEntity;
import com.swp391.DogCatLoverPlatform.entity.BookingEntity;
import com.swp391.DogCatLoverPlatform.repository.BlogRepository;
import com.swp391.DogCatLoverPlatform.repository.BookingEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChartService {
    @Autowired
    private BookingEntityRepository bookingRepository;

    public List<BookingDTO> getBookingChart() {
        List<BookingEntity> listbookingEn  = bookingRepository.findAll();
        List<BookingDTO> listBookingDTO = new ArrayList<>();
        for (BookingEntity o : listbookingEn){
            BookingDTO listBook = new BookingDTO();
            listBook.setTotal_price(o.getTotal_price());
            listBook.setCreate_date(o.getCreate_date());

            listBookingDTO.add(listBook);

        }
        return listBookingDTO;
    }

    @Autowired
    private BlogRepository blogRepository;

    public int countAllBlog(){
        List<BlogEntity> blogEntityList = blogRepository.findAll();
        List<BlogDTO> blogDTOList = new ArrayList<>();
        int countBlog = 0;
        for (BlogEntity b : blogEntityList){
            BlogDTO blogDTO = new BlogDTO();
            blogDTO.setContent(b.getContent());
            blogDTOList.add(blogDTO);
            countBlog++;
        }
        return countBlog;
    }
}
