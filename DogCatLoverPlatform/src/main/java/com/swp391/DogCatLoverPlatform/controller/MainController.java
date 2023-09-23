package com.swp391.DogCatLoverPlatform.controller;

import com.swp391.DogCatLoverPlatform.entity.UserEntity;
import com.swp391.DogCatLoverPlatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/demoTestingNTimes")
public class MainController {

    @Autowired
    UserService  userService;

    @GetMapping("/home")
    public String hello(){
        return "index";
    }

    @GetMapping("/Login")
    public String login(){
        return "login";
    }

    @GetMapping("/Blog-Standard")
    public String blogStandard(){
        return "blog-standard";
    }

    @GetMapping("/Blog-Details")
    public String blogDetails(){
        return "blog-details";
    }

    @GetMapping("/About")
    public String about(){
        return "about";
    }

    @GetMapping("/Error")
    public String error(){
        return "error";
    }

    @GetMapping("/Contact")
    public String contact(){
        return "contact";
    }

    @GetMapping("/Faq")
    public String faq(){
        return "faq";
    }

    @GetMapping("/Pricing-Plan")
    public String pricingPlan(){
        return "pricing-plan";
    }

    @GetMapping("/Service-Details")
    public String serviceDetail(){
        return "service-details";
    }

    @GetMapping("/Cart")
    public String cart(){
        return "cart";
    }

    @GetMapping("/Check-out")
    public String checkOut(){
        return "check-out";
    }

    @GetMapping("/3col-gallery")
    public String gallery(){
        return "3col-gallery";
    }

    @GetMapping("/Team")
    public String team(){
        return "team";
    }


    @GetMapping("/Sign-up")
    public String listTodo(Model model){
        List<UserEntity> listUser = userService.getAllUser();
        model.addAttribute("listUser",listUser);
        return "sign-up";
    }

    @PostMapping (value = "/Sign-up-add")
    public String addTodo(HttpServletRequest req){
        String email = req.getParameter("email");
        String fullname = req.getParameter("fullName");
        String password = req.getParameter("password");
        String username = req.getParameter("userName");
        userService.addUser(fullname,password,email,username);
        return "redirect:/demoTestingNTimes/Sign-up";
    }

    @GetMapping("/delete-user")
    public String deleteUser(HttpServletRequest req){
        int id = Integer.parseInt(req.getParameter("id"));
        userService.deleteUser(id);
        return "redirect:/demoTestingNTimes/Sign-up";
    }

}
