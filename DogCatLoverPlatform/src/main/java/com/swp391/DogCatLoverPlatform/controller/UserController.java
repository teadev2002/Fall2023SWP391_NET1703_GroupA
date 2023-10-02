package com.swp391.DogCatLoverPlatform.controller;


import com.google.gson.Gson;
import com.swp391.DogCatLoverPlatform.dto.Root;
import com.swp391.DogCatLoverPlatform.dto.UserDTO;
import com.swp391.DogCatLoverPlatform.entity.UserEntity;
import com.swp391.DogCatLoverPlatform.service.UserService;
import com.swp391.DogCatLoverPlatform.util.JwtHelper;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

//@RestController
@Controller
@RequestMapping("/index")
public class UserController {

    @Autowired
    private JwtHelper jwtHelper;


    @Autowired
    UserService userService;


    private Gson gson = new Gson();

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
            return "redirect:/index/login";
        }
        return "redirect:/index/login";
    }

    @PostMapping(value ="/profile-update")
    public String profileUpdate(Model model, HttpServletRequest req){
        String fullname = req.getParameter("fullName");
        String username = req.getParameter("userName");
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");

        boolean isSuccess = userService.updateUser(fullname,username,phone,address,email);


        return "redirect:/index/profile";
    }

//    @GetMapping("/signinggoogle")
//    public Map<String, Object>currentUser(OAuth2AuthenticationToken oAuth2AuthenticationToken){
//        System.out.println(toPerson(oAuth2AuthenticationToken.getPrincipal().getAttributes()).getEmail());
//        System.out.println(toPerson(oAuth2AuthenticationToken.getPrincipal().getAttributes()).getName());
//        System.out.println(toPerson(oAuth2AuthenticationToken.getPrincipal().getAttributes()).getPicture());
//        return oAuth2AuthenticationToken.getPrincipal().getAttributes();
//    }
//
//    public Root toPerson(Map<String, Object> map){
//        if(map== null){
//            return null;
//        }
//        Root root = new Root();
//        root.setEmail((String) map.get("email"));
//        root.setName((String) map.get("name"));
//        root.setPicture((String) map.get("picture"));
//        return root;
//    }


    @GetMapping("/sign-up")
    public String listTodo(Model model){
        List<UserEntity> listUser = userService.getAllUser();
        model.addAttribute("listUser",listUser);
        return "sign-up";
    }

    @PostMapping (value = "/sign-up-add")
    public String addTodo(HttpServletRequest req,Model model){
        boolean isSuccess = false;
        String email = req.getParameter("email");
        String fullName = req.getParameter("fullName");
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String password2 = req.getParameter("password2");

        if(password2.equals(password) && userService.checkEmailExist(email) == false){
            userService.addUser(fullName, password, email, userName);
            isSuccess = true;
            model.addAttribute("isSuccess", isSuccess);
            return "redirect:/index/login";
        }

        model.addAttribute("isSuccess", isSuccess);
        return "redirect:/index/sign-up?isSuccess";
    }



    @PostMapping ( "/login")
    public String loginInto(HttpServletRequest req, HttpServletResponse resp, Model model){
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        boolean check = false;
        UsernamePasswordAuthenticationToken authen = new UsernamePasswordAuthenticationToken(email,password);
        authenticationManager.authenticate(authen);
        if(authenticationManager != null){
            Cookie cookie = new Cookie("User", email);
            cookie.setMaxAge(3600);
            resp.addCookie(cookie);
            //Lấy danh sách role đã lưu từ security context folder khi AuthenManager chứng thực thành công
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            List<SimpleGrantedAuthority> roles = (List<SimpleGrantedAuthority>) authentication.getAuthorities();


            String jsonRole = gson.toJson(roles);

            String token = jwtHelper.generateToken(jsonRole);
            System.out.println(token);
            return "redirect:/index/home";
        }
        return "redirect:/index/login?check";

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
        return "redirect:/index/login";
    }


    @GetMapping("/delete-user")
    public String deleteUser(HttpServletRequest req){
        int id = Integer.parseInt(req.getParameter("id"));
        userService.deleteUser(id);
        return "redirect:/DogCatLoverPlatform/Sign-up";
    }


    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestParam String email, @RequestParam String password){
        UsernamePasswordAuthenticationToken authen = new UsernamePasswordAuthenticationToken(email,password);
        authenticationManager.authenticate(authen);
        return new ResponseEntity<>("Hello Signup", HttpStatus.OK);
    }

}
