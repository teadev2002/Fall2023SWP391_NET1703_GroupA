package com.swp391.DogCatLoverPlatform.service;

import com.swp391.DogCatLoverPlatform.dto.*;
import com.swp391.DogCatLoverPlatform.entity.BlogEntity;
import com.swp391.DogCatLoverPlatform.entity.BookingEntity;
import com.swp391.DogCatLoverPlatform.entity.ServiceEntity;
import com.swp391.DogCatLoverPlatform.repository.BlogRepository;
import com.swp391.DogCatLoverPlatform.repository.BookingEntityRepository;
import com.swp391.DogCatLoverPlatform.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChartService {
    @Autowired
    private BookingEntityRepository bookingRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private ServiceRepository serviceRepository;

//    public List<ChartDTO> getBookingChart() {
//        List<BookingEntity> listbookingEn = bookingRepository.findAll();
//        List<ChartDTO> listBookingDTO = new ArrayList<>();
//        for (BookingEntity o : listbookingEn) {
//            ChartDTO listBook = new ChartDTO();
//            listBook.setTotal_price(o.getTotal_price());
//            listBook.setCreate_date(o.getCreate_date());
//
//            listBookingDTO.add(listBook);
//
//        }
//        return listBookingDTO;
//    }

    public List<ChartDTO> getAllBlogChart() {
        List<BookingEntity> listbookingEn = bookingRepository.findAll();
        List<ChartDTO> listBookingDTO = new ArrayList<>();
        for (BookingEntity o : listbookingEn) {
            ChartDTO listBook = new ChartDTO();
            listBook.setCreate_date(o.getCreate_date());
            listBook.setServiceCountByWeek(o.getListBookingHistory().size());
            listBook.setBlogCountByWeek(o.getListBookingHistory().size());
            listBookingDTO.add(listBook);

        }
        return listBookingDTO;
    }


    public int getBlogAndService() {
        List<BlogEntity> blogsThisWeek = blogRepository.findAll();
        return blogsThisWeek.size();
    }

    public int getBlogCount() {
        List<ServiceEntity> serviceEntities = serviceRepository.findAll();
        int countService = getBlogAndService() - serviceEntities.size();

        return countService;
    }

    public int getServiceCount() {
        int blogCountByWeek = getBlogAndService() - getBlogCount();
        return blogCountByWeek;
    }





}
