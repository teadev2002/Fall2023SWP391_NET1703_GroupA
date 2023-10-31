//package com.swp391.DogCatLoverPlatform.service;
//
//import com.swp391.DogCatLoverPlatform.dto.*;
//import com.swp391.DogCatLoverPlatform.entity.BlogEntity;
//import com.swp391.DogCatLoverPlatform.entity.BookingEntity;
//import com.swp391.DogCatLoverPlatform.entity.ServiceEntity;
//import com.swp391.DogCatLoverPlatform.repository.BlogRepository;
//import com.swp391.DogCatLoverPlatform.repository.BookingEntityRepository;
//import com.swp391.DogCatLoverPlatform.repository.ServiceRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.DayOfWeek;
//import java.time.LocalDate;
//import java.time.temporal.TemporalAdjusters;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Service
//public class ChartService {
//    @Autowired
//    private BookingEntityRepository bookingRepository;
//
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
//
//    public List<ChartDTO> getAllBlogChart() {
//        List<BookingEntity> listbookingEn = bookingRepository.findAll();
//        List<ChartDTO> listBookingDTO = new ArrayList<>();
//        for (BookingEntity o : listbookingEn) {
//            ChartDTO listBook = new ChartDTO();
//            listBook.setCreate_date(o.getCreate_date());
//            listBook.setServiceCountByWeek(o.getListBookingHistory().size());
//            listBook.setBlogCountByWeek(o.getListBookingHistory().size());
//            listBookingDTO.add(listBook);
//
//        }
//        return listBookingDTO;
//    }
//
//    @Autowired
//    private BlogRepository blogRepository;
//
//    public List<BlogDTO> countAllBlog() {
//        List<BlogEntity> blogEntityList = blogRepository.findAll();
//        List<BlogDTO> blogDTOList = new ArrayList<>();
//
//        for (BlogEntity b : blogEntityList) {
//            BlogDTO blogDTO = new BlogDTO();
//            blogDTO.setContent(b.getContent());
//            blogDTOList.add(blogDTO);
//        }
//        return blogDTOList;
//    }
//
//
//    @Autowired
//    private ServiceRepository serviceRepository;
//
//    public int getServiceCountByWeek() {
//        LocalDate startOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
//        LocalDate endOfWeek = startOfWeek.plusDays(6);
//
//        Date startOfWeekDate = java.sql.Date.valueOf(startOfWeek);
//        Date endOfWeekDate = java.sql.Date.valueOf(endOfWeek);
//
//        List<ServiceEntity> servicesThisWeek = serviceRepository.findByCreateDateBetween(startOfWeekDate, endOfWeekDate);
//
//        return servicesThisWeek.size();
//
//
//    }
//
//    public int getBlogAndService() {
//        List<BlogEntity> blogsThisWeek = blogRepository.findAll();
//        return blogsThisWeek.size();
//    }
//
//    public int getBlogCount() {
//        List<ServiceEntity> serviceEntities = serviceRepository.findAll();
//        int countService = getBlogAndService() - serviceEntities.size();
//
//        return countService;
//    }
//
//    public int getServiceCount() {
//        int blogCountByWeek = getBlogAndService() - getBlogCount();
//        return blogCountByWeek;
//    }
//
//
//    public List<ChartDTO> getTopCustomer() {
//        List<BookingEntity> bookingEntityList = bookingRepository.findAll();
//        List<ChartDTO> topCustomerList = new ArrayList<>();
//
//        for (BookingEntity b : bookingEntityList) {
//            ChartDTO user = new ChartDTO();
//            user.setUserName(b.getUserEntity_BookingEntity().getFullName());
//            user.setId_blog(b.getBlogEntity_BookingEntity().getListBooking_BlogEntity().size());
//            topCustomerList.add(user);
//
//        }
//        return topCustomerList;
//
//    }
//
//
//}
