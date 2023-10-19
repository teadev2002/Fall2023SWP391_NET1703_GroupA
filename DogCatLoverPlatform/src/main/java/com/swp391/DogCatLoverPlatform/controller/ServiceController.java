package com.swp391.DogCatLoverPlatform.controller;

import com.swp391.DogCatLoverPlatform.dto.BookingDTO;
import com.swp391.DogCatLoverPlatform.dto.ServiceDTO;
import com.swp391.DogCatLoverPlatform.dto.UserDTO;
import com.swp391.DogCatLoverPlatform.entity.ServiceEntity;
import com.swp391.DogCatLoverPlatform.service.BlogService;
import com.swp391.DogCatLoverPlatform.service.BookingService;
import com.swp391.DogCatLoverPlatform.service.ServiceService;
import com.swp391.DogCatLoverPlatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    ServiceService serviceService;
    @Autowired
    UserService userService;
    @Autowired
    BookingService bookingService;
    @Autowired
    BlogService blogService;


    @GetMapping("/view")
    public String viewAllService(Model model){
        List<ServiceDTO>  serviceDTOList = serviceService.getAllService();
        model.addAttribute("listService",serviceDTOList);
        return "service-standard";
    }

    @GetMapping("/detail")
    public String viewDetailService(Model model, HttpServletRequest request){
        int id  = Integer.parseInt(request.getParameter("id"));
        ServiceDTO  serviceDTO = serviceService.getServiceDetail(id);
        model.addAttribute("service",serviceDTO);
        return "service-details";
    }

    @PostMapping("/create")
    public ResponseEntity<?> createService(@RequestParam("file")MultipartFile file, HttpServletRequest request) throws IOException {

        String image = blogService.saveImageAndReturnPath(file);
        String Content = request.getParameter("content");
        int price = Integer.parseInt(request.getParameter("price"));
        String title = request.getParameter("title");
        UserDTO userDTO = getUserIdFromCookie(request);
        System.out.println(userDTO.getId());
        int serviceCategory = Integer.parseInt(request.getParameter("serviceCategory"));

        ServiceEntity serviceEntity = serviceService.createService(Content,price,title,userDTO.getId(),serviceCategory,image);

        return new ResponseEntity<>(serviceEntity, HttpStatus.OK);
    }

    @GetMapping("cart")
    public String cart(HttpServletRequest request, Model model){
        UserDTO userDTO = getUserIdFromCookie(request);
        if(userDTO == null){
            return "redirect:/index/login";
        }
        List<BookingDTO> list = bookingService.findByUserBooking(userDTO.getId());

        double price = 0;
        for (BookingDTO booking : list) {
            price += booking.getTotal_price();
            System.out.println(price);
        }
        model.addAttribute("listBooking", list);
        model.addAttribute("quantity", list.size());
        model.addAttribute("totalPrice", price);
        return "service_cart";
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
