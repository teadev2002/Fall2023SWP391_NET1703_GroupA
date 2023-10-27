package com.swp391.DogCatLoverPlatform.controller;

import com.swp391.DogCatLoverPlatform.dto.BlogDTO;
import com.swp391.DogCatLoverPlatform.dto.UserDTO;
import com.swp391.DogCatLoverPlatform.payload.BaseRespone;
import com.swp391.DogCatLoverPlatform.service.BlogService;
import com.swp391.DogCatLoverPlatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    @PostMapping(value = "/sign-up-staff-account")
    public ResponseEntity<?> signup(@Valid @RequestBody UserDTO signUpRequest) {
        boolean isSuccess = userService.addStaff(signUpRequest);
        BaseRespone baseRespone = new BaseRespone();
        baseRespone.setStatusCode(200);
        baseRespone.setMessage("");
        baseRespone.setData(isSuccess);
        return new ResponseEntity<>(baseRespone, HttpStatus.OK);
    }

    @GetMapping("/manageStaff")
    public String manageStaff(HttpServletRequest req, Model model){
        List<UserDTO> list = userService.getAccountStaff();
        UserDTO user  = getUserIdFromCookie(req);
        model.addAttribute("listStaff", list);
        model.addAttribute("user", user);
        return "staff-list";
    }

    @PostMapping(value = "/updateStaff")
    public String updateStaff(HttpServletRequest request) {
        String action = request.getParameter("action");
        int idStaff = Integer.parseInt(request.getParameter("idStaff"));
        if(action.equals("Disable")){
            userService.UpdateStaff(idStaff,"ROLE_NULL");
        }else if(action.equals("Active")){
            userService.UpdateStaff(idStaff,"ROLE_STAFF");
        }
        return "redirect:/staff/manageStaff";
    }


}
