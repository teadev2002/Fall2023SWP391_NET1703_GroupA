package com.swp391.DogCatLoverPlatform.controller;


import com.swp391.DogCatLoverPlatform.dto.UserDTO;
import com.swp391.DogCatLoverPlatform.entity.UserEntity;
import com.swp391.DogCatLoverPlatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    @GetMapping("/profile")
    public String profile(Model model, HttpServletRequest req){
        Cookie[] cookies = req.getCookies();
        String email = null;
        try{
            for(Cookie c : cookies){
                email = c.getValue();
                UserDTO user = userService.getUserByEmail(email);
                model.addAttribute("user", user);
                return "profile";
            }

        }catch (Exception e){
            model.addAttribute("error","You didn't Login");
            return "redirect:/test/login";
        }
        return "redirect:/test/login";
    }

    @PostMapping(value ="/profile-update")
    public String profileUpdate(Model model, HttpServletRequest req){
        String fullname = req.getParameter("fullName");
        String username = req.getParameter("userName");
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");

        boolean isSuccess = userService.updateUser(fullname,username,phone,address,email);


        return "redirect:/test/profile";
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
    public String loginInto(HttpServletRequest req, HttpServletResponse resp, Model model){
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        boolean check = userService.checkLogin(email,password);
        if (check == true){
            Cookie userCookie = new Cookie("User",email);
            userCookie.setMaxAge(3600); // Cookie will expire in 1 hour (you can adjust this as needed)
            resp.addCookie(userCookie);
            return "redirect:/test/home";
        }
        return "redirect:/test/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest req, HttpServletResponse resp) {
        // Get the user's session
        HttpSession session = req.getSession(false);

        if (session != null) {
            // Invalidate the session to log out the user
            session.invalidate();
        }

        // Remove the "User" cookie by setting its max age to 0
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("User".equals(cookie.getName())) {
                    cookie.setMaxAge(0); // Setting max age to 0 deletes the cookie
                    resp.addCookie(cookie);
                    break;
                }
            }
        }

        // Redirect to the login page after logout
        return "redirect:/test/login";
    }


    @GetMapping("/delete-user")
    public String deleteUser(HttpServletRequest req){
        int id = Integer.parseInt(req.getParameter("id"));
        userService.deleteUser(id);
        return "redirect:/DogCatLoverPlatform/Sign-up";
    }

}
