package com.swp391.DogCatLoverPlatform.controller;

import com.swp391.DogCatLoverPlatform.dto.BlogDTO;
import com.swp391.DogCatLoverPlatform.dto.BlogUpdateDTO;
import com.swp391.DogCatLoverPlatform.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;

@Controller
public class BlogController {

    @Autowired
    BlogService blogService;

    @GetMapping("/view")
    public String GetAllBlogs(Model model) {
        List<BlogDTO> list = blogService.GetAllBlog();
        model.addAttribute("listBlog", list);
        return "blog-standard";
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBlog(@PathVariable int id, @RequestBody BlogUpdateDTO newBlog){
        blogService.updateBlog(id,newBlog);
        return new ResponseEntity<>("Update successfull",HttpStatus.OK);
    }

}
