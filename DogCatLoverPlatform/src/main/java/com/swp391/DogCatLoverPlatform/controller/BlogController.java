package com.swp391.DogCatLoverPlatform.controller;

import com.swp391.DogCatLoverPlatform.dto.BlogDTO;
import com.swp391.DogCatLoverPlatform.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

@Controller
public class BlogController {

    @Autowired
    BlogService blogService;

    @GetMapping("/view")
    public ResponseEntity<?> getAllBlog(Model model){
        List<BlogDTO> listBlog =   blogService.getAllBlog();
//        model.addAttribute("listBlog",listBlog);
//        return "blog";
        return new ResponseEntity<>(listBlog, HttpStatus.OK);

    }
}
