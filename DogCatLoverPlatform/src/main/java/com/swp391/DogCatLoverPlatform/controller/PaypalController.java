package com.swp391.DogCatLoverPlatform.controller;

import com.swp391.DogCatLoverPlatform.dto.Order;
import com.swp391.DogCatLoverPlatform.dto.UserDTO;
import com.swp391.DogCatLoverPlatform.service.BookingService;
import com.swp391.DogCatLoverPlatform.service.PaypalService;
import com.swp391.DogCatLoverPlatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/paymethod")
public class PaypalController {

    @Autowired
    PaypalService service;
    @Autowired
    BookingService bookingService;
    @Autowired
    UserService userService;

    public static final String SUCCESS_URL = "/pay/success";
    public static final String CANCEL_URL = "/pay/cancel";

    @GetMapping("")
    public String home() {
        return "home";
    }

    @PostMapping("/pay")
    public String payment(@ModelAttribute("order") Order order) {
        try {
            Payment payment = service.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
                    order.getIntent(), order.getDescription(), "http://localhost:8080/paymethod" + CANCEL_URL,
                    "http://localhost:8080/paymethod" + SUCCESS_URL);
            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return "redirect:"+link.getHref();
                }
            }

        } catch (PayPalRESTException e) {

            e.printStackTrace();
        }
        return "redirect:/service/cart";
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "cancel";
    }

    @GetMapping(value = SUCCESS_URL)
    public String successPay(HttpServletRequest request, @RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = service.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                UserDTO userDTO = getUserIdFromCookie(request);
                bookingService.updateStatus(userDTO.getId());
                return "success";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/";
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