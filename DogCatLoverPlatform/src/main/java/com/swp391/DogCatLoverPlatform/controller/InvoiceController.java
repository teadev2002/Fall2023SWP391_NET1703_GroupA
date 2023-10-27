package com.swp391.DogCatLoverPlatform.controller;

import com.swp391.DogCatLoverPlatform.dto.InvoiceDTO;
import com.swp391.DogCatLoverPlatform.dto.UserDTO;
import com.swp391.DogCatLoverPlatform.service.BlogService;
import com.swp391.DogCatLoverPlatform.service.InvoiceService;
import com.swp391.DogCatLoverPlatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    UserService userService;

    @Autowired
    BlogService blogService;


    @GetMapping("")
    public String showInvoice(@RequestParam("id") int invoiceId, Model model) {
        // Fetch invoice details by ID (You should implement this logic in your service)
        InvoiceDTO invoice = invoiceService.getInvoiceById(invoiceId);

        if (invoice != null) {
            // Add invoice details to the model
            model.addAttribute("invoiceDate", invoice.getInvoice_date());
            model.addAttribute("totalAmount", invoice.getTotal_amount());
            model.addAttribute("blogId", invoice.getIdBlog());
            model.addAttribute("userId", invoice.getIdUser());
            model.addAttribute("invoice",invoice);

            // Lấy email từ UserEntity trong InvoiceDTO
            String email = userService.getUserById(invoice.getIdUser()).getEmail();
            model.addAttribute("email", email);

            String title = blogService.getBlogById(invoice.getIdBlog()).getTitle();
            model.addAttribute("title", title);
        }

        // Return the invoice template (invoice.html)
        return "invoice";
    }

    @GetMapping("/manager")
    public String manager(Model model, HttpServletRequest req) {
        UserDTO userDTO = getUserIdFromCookie(req);

        if(userDTO == null){
            return "redirect:/index/login";
        }
        List<InvoiceDTO> list = invoiceService.getSellManager(userDTO.getId());
        model.addAttribute("list", list);
        model.addAttribute("quantity", list.size());

        return "invoice-manager";
    }

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
