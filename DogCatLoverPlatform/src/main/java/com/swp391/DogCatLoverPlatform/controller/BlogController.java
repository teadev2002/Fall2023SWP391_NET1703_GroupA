package com.swp391.DogCatLoverPlatform.controller;

import com.swp391.DogCatLoverPlatform.dto.BlogDTO;
import com.swp391.DogCatLoverPlatform.dto.BlogUpdateDTO;
import com.swp391.DogCatLoverPlatform.entity.BlogTypeEntity;
import com.swp391.DogCatLoverPlatform.service.BlogService;
import com.swp391.DogCatLoverPlatform.service.BlogTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class BlogController {

    @Autowired
    BlogService blogService;

    @Autowired
    BlogTypeService blogTypeService;

    @GetMapping("/view")
    public String GetAllBlogs(Model model) {
        List<BlogDTO> list = blogService.GetAllBlog();
        model.addAttribute("listBlog", list);
        return "blog-standard";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        BlogDTO blogDTO = blogService.getBlogById(id);
        model.addAttribute("blog", blogDTO);
        return "update-blog-form";
    }


    @PostMapping("/{id}/edit")
    public String updateBlog(@PathVariable("id") int id, @ModelAttribute("blog") BlogUpdateDTO blogUpdateDTO) {
        blogService.updateBlog(id, blogUpdateDTO);
        return "redirect:/view";
    }
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        List<BlogTypeEntity> listBlogType =  blogTypeService.getAllBlogType();
        model.addAttribute("blogTypes", listBlogType);
        model.addAttribute("blog", new BlogDTO());
        return "create-blog-form";
    }

    // POST request to handle the create blog form submission
    @PostMapping("/create")
    public String createBlog(@ModelAttribute("blog") BlogDTO blogDTO, @RequestParam("blogTypeId") int blogTypeId) {
        BlogDTO createdBlog = blogService.createBlog(blogDTO, blogTypeId);
        return "redirect:/view" ;
    }

    @PostMapping("/{id}")
    public String deleteBlog(@PathVariable int id) {
        blogService.deleteBlogById(id);
        return "redirect:/view";
    }


}
