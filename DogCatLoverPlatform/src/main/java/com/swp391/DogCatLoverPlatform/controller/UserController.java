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
@RequestMapping("/test")
public class UserController {


    @Autowired
    UserService userService;

    @GetMapping("/home")
    public String hello(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/blog")
    public String blogStandard(){
        return "blog-standard";
    }

    @GetMapping("/blog-details")
    public String blogDetails(){
        return "blog-details";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }

    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }

    @GetMapping("/faq")
    public String faq(){
        return "faq";
    }

    @GetMapping("/pricing-plan")
    public String pricingPlan(){
        return "pricing-plan";
    }

    @GetMapping("/service-details")
    public String serviceDetail(){
        return "service-details";
    }

    @GetMapping("/cart")
    public String cart(){
        return "cart";
    }

    @GetMapping("/check-out")
    public String checkOut(){
        return "check-out";
    }

    @GetMapping("/3col-gallery")
    public String gallery(){
        return "3col-gallery";
    }

    @GetMapping("/team")
    public String team(){
        return "team";
    }


    @GetMapping("/sign-up")
    public String listTodo(Model model){
        List<UserEntity> listUser = userService.getAllUser();
        model.addAttribute("listUser",listUser);
        return "sign-up";
    }

    @PostMapping (value = "/sign-up-add")
    public String addTodo(HttpServletRequest req,Model model){
        boolean isSucces = false;
        String email = req.getParameter("email");
        String fullName = req.getParameter("fullName");
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String password2 = req.getParameter("password2");

        if(password2.equals(password) && userService.checkEmailExist(email) == false){
            userService.addUser(fullName, password, email, userName);
            isSucces = true;
        }

        model.addAttribute("isSuccess", isSucces);
        return "redirect:/test/sign-up";
    }

    @PostMapping (value = "/login")
    public String loginInto(HttpServletRequest req,Model model){
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        boolean check = userService.checkLogin(email,password);
        if (check == true){
            return "redirect:/test/home";
        }
            return "redirect:/test/login";
    }

    @GetMapping("/delete-user")
    public String deleteUser(HttpServletRequest req){
        int id = Integer.parseInt(req.getParameter("id"));
        userService.deleteUser(id);
        return "redirect:/DogCatLoverPlatform/Sign-up";
    }

}
