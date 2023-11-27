package com.swp391.DogCatLoverPlatform.service;

import com.swp391.DogCatLoverPlatform.dto.*;
import com.swp391.DogCatLoverPlatform.entity.BlogEntity;
import com.swp391.DogCatLoverPlatform.entity.ServiceEntity;
import com.swp391.DogCatLoverPlatform.entity.UserEntity;
import com.swp391.DogCatLoverPlatform.repository.*;
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

    public StatisticBlogNServiceDTO countBlogNService() {
        List<Object[]> result = blogRepository.getBlogAndServiceCounts();
        StatisticBlogNServiceDTO getCount = new StatisticBlogNServiceDTO();

        for (Object[] row : result) {
            getCount.setNumBlog(((Number) row[0]).intValue()); // Assuming the first column is numBlog
            getCount.setNumService(((Number) row[1]).intValue()); // Assuming the second column is numService

        }
        return getCount;
    }





    @Autowired
    DispositHistoryRepository dispositHistoryRepository;

    public double getAllMoneyUserRecharge(){
    double money = dispositHistoryRepository.getTotalMoney();
        return money;
    }
    @Autowired
    UserRepository userRepository;

    public int getAllUser(){
        int allUser = userRepository.getAllUser();
        return allUser;
    }

    @Autowired
    BookingEntityRepository bookingEntityRepository;

    public int getTotalBooking(){
        int allBooking = bookingEntityRepository.totalBooking();
        return allBooking;
    }

    @Autowired
    InvoiceRepository invoiceRepository;

    public int getTotalInvoice(){
        int allInvoice = invoiceRepository.getAllInvoice();
        return allInvoice;
    }

    public double getTotalBookingServiceRevenue(){
        double revenue = userRepository.getTotalMoney();
        return revenue;
    }

    public List<StatisticUserDTO> getCountNumBlogByUser(){
        List<UserEntity> getListUser = userRepository.getCountNumBlogByUser();
        List<StatisticUserDTO> getListCount = new ArrayList<>();

        for(UserEntity b : getListUser){
           StatisticUserDTO statisticUserDTO = new StatisticUserDTO();
           statisticUserDTO.setId(b.getId());
           statisticUserDTO.setImage(b.getImage());
           statisticUserDTO.setUserName(b.getName());
           statisticUserDTO.setBalance(b.getAccountBalance());
           statisticUserDTO.setRoleDTO(b.getRoleEntity().getName()); // new
            int totalBlogs = userRepository.getTotalBlogsByUserId(b.getId());
            statisticUserDTO.setNumBlog(totalBlogs);
            getListCount.add(statisticUserDTO);
        }
        return getListCount;
    }


}
