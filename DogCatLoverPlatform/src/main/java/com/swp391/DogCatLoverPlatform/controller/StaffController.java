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
    public String viewDashboard(){
        return "index-staff";
    }

    //Quản lý Blog bên Staff
    @GetMapping("/view/pending")
    public String viewPendingBlog(Model model){
        List<BlogDTO> pendingBlogs = blogService.getBlogsPendingApproval();
        model.addAttribute("pendingBlogs", pendingBlogs);
        return "table-staff";
    }

    @GetMapping("/staff-sign-up")
    public String staffSignup(Model model){return "staff-sign-up";}

    @PostMapping(value = "/sign-up-staff-account")
    public ResponseEntity<?> signup(@Valid @RequestBody UserDTO signUpRequest) {
        boolean isSuccess = userService.addStaff(signUpRequest);
        BaseRespone baseRespone = new BaseRespone();
        baseRespone.setStatusCode(200);
        baseRespone.setMessage("");
        baseRespone.setData(isSuccess);
        return new ResponseEntity<>(baseRespone, HttpStatus.OK);
    }


}
