package com.swp391.DogCatLoverPlatform.controller;


import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.swp391.DogCatLoverPlatform.dto.Order;
import com.swp391.DogCatLoverPlatform.dto.PaymentDTO;
import com.swp391.DogCatLoverPlatform.dto.UserDTO;
import com.swp391.DogCatLoverPlatform.entity.DispositHistory;
import com.swp391.DogCatLoverPlatform.entity.UserEntity;
import com.swp391.DogCatLoverPlatform.repository.DispositHistoryRepository;
import com.swp391.DogCatLoverPlatform.repository.UserRepository;
import com.swp391.DogCatLoverPlatform.service.PaypalService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Time;
import java.util.Optional;

@Controller
@RequestMapping("/deposite-history")
public class DepositHistoryController {

    @Autowired
    private DispositHistoryRepository dispositHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PaypalService service;


    // tạo link nạp tiền, truyền vào số tiền cần nạp-> trả link nạp tiền
    @PostMapping("/create")
    public ResponseEntity<?> getUrlPayment(@RequestBody PaymentDTO paymentDto){
        Order order = new Order();
        order.setMethod("PAYPAL");
        order.setDescription("Nap tien vao vi");
        order.setIntent("sale");
        order.setPrice(paymentDto.getAmount());
        order.setCurrency("USD");
        String url = "";
        try {
            Payment payment = service.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),

                    order.getIntent(), order.getDescription(), "http://localhost:8080/paymethod/pay/cancel",
                    "http://localhost:8080/deposite-history/check-deposit" );

            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    url =link.getHref();
                }
            }

        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(url, HttpStatus.OK);
    }

    // hiển thị trang nạp tiền
    @GetMapping("/deposit")
    public String viewDeposit(HttpServletRequest request, Model model){
        UserEntity user = getUserIdFromCookie(request);
        if(user == null){
            return "redirect:../index/login";
        }
        model.addAttribute("accountBal", user.getAccountBalance());
        return "deposit";
    }

    // kiểm tra thanh toán nạp tiền thành công
    /*
     * Nếu thành công, kiểm tra tiếp xem transaction này đã lưu hay chưa, tránh người dùng f5 lại, ghi lại tiền
     * Tăng tiền tài khoản người dùng lên, ghi lại lịch sử nạp tiền
     *
     * */
    @GetMapping("/check-deposit")
    public String checkDeposite(@RequestParam("paymentId") String paymentId,@RequestParam("token") String token,
                                @RequestParam("PayerID") String payerID, HttpServletRequest request) throws Exception{
        UserEntity user = getUserIdFromCookie(request);
        Payment payment = service.executePayment(paymentId, payerID);
        System.out.println(payment.toJSON());
        Double amount = Double.valueOf(payment.getTransactions().get(0).getAmount().getTotal());
        System.out.println("amount===: "+amount);
        Optional<DispositHistory> dispositHistory = dispositHistoryRepository.findBypaymentIdAndToken(token, paymentId);
        if(dispositHistory.isPresent()){
            return "redirect:my-deposit";
        }
        DispositHistory dis = new DispositHistory();
        dis.setCreatedDate(new Date(System.currentTimeMillis()));
        dis.setCreatedTime(new Time(System.currentTimeMillis()));
        dis.setToken(token);
        dis.setPaymentId(paymentId);
        dis.setUserEntity(user);
        dis.setTotalAmount(amount);
        dispositHistoryRepository.save(dis);
        if(user.getAccountBalance() == null){
            user.setAccountBalance(amount);
        }
        else{
            user.setAccountBalance(user.getAccountBalance() + amount);
        }
        userRepository.save(user);
        return "redirect:my-deposit";
    }


    /*
     * trả ra lịch sử thanh nạp tiền của người dùng
     * */
    @GetMapping("my-deposit")
    public String myDeposit(HttpServletRequest request, Model model){
        UserEntity user = getUserIdFromCookie(request);
        if(user == null){
            return "redirect:../index/login";
        }
        model.addAttribute("mydeposit",dispositHistoryRepository.findByUser(user.getId()));
        return "my-deposit";
    }

    /*
     * trả ra thông tin user từ cookie
     * */
    private UserEntity getUserIdFromCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("User".equals(cookie.getName())) {
                    String email = cookie.getValue();
                    UserEntity user = userRepository.findByEmail(email);
                    return user;
                }
            }
        }
        return null;
    }

}
