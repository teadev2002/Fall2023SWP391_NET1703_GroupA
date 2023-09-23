package com.swp391.DogCatLoverPlatform.controller;


import com.swp391.DogCatLoverPlatform.dto.BlogDTO;
import com.swp391.DogCatLoverPlatform.entity.BlogEntity;
import com.swp391.DogCatLoverPlatform.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/testblog")
public class BlogController {
    @Autowired
    BlogService blogService;

    @GetMapping("/blogs")
    public String GetAllBlogs(Model model) {
        List<BlogDTO> list = blogService.GetAllBlog();
        model.addAttribute("listBlog",list);
        return "blog-standard" ;

    }

    @PostMapping("/blogs")
    @ResponseBody
    public ResponseEntity<?> GetBlogsByTitle(@RequestParam String search) {
        return ResponseEntity.ok(blogService.GetBlogsByTitle(search));
    }

    @PostMapping("/blog")
    @ResponseBody
    public ResponseEntity<?> CreateBlog(@RequestBody BlogEntity newBlog) {
        return ResponseEntity.ok(blogService.CreateBlog(newBlog));
    }

//    @PutMapping("/blog")
//    @ResponseBody
//    public ResponseEntity<?> updateBlog(@RequestBody UpdateBlogRequest updatedBlog) {
//        BlogDTO dtoResponse = blogService.UpdateBlog(updatedBlog);
//        if(dtoResponse != null){
//            return  ResponseEntity.ok(dtoResponse);
//        }else {
//            return ResponseEntity.ok(new ResponseMessage("Exception in updating"));
//        }
//    }

    @DeleteMapping("/blog/{blogId}")
    @ResponseBody
    public ResponseEntity<?> DeleteBlog(@PathVariable("blogId") int id) {
        try {
            blogService.DeleteBlog(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something wrong while deleting", HttpStatus.OK);
        }
    }

}
