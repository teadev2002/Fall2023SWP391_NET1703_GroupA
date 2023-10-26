package com.swp391.DogCatLoverPlatform.controller;

import com.swp391.DogCatLoverPlatform.dto.BlogDTO;
import com.swp391.DogCatLoverPlatform.dto.UserDTO;
import com.swp391.DogCatLoverPlatform.service.BlogService;
import com.swp391.DogCatLoverPlatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    BlogService blogService;

    @Autowired
    UserService userService;

    @GetMapping("/view")
    public String viewDashboard(HttpServletRequest req, Model model){
        UserDTO user  = getUserIdFromCookie(req);
        model.addAttribute("user", user);
        return "index-staff";
    }

    //Quản lý Blog bên Staff
    @GetMapping("/view/pending")
    public String viewPendingBlog(HttpServletRequest req, Model model){
        List<BlogDTO> pendingBlogs = blogService.getBlogsPendingApproval();
        model.addAttribute("pendingBlogs", pendingBlogs);

        UserDTO user  = getUserIdFromCookie(req);
        model.addAttribute("user", user);
        return "table-staff";
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
