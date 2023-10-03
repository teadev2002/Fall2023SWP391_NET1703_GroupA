package com.swp391.DogCatLoverPlatform.controller;

import com.swp391.DogCatLoverPlatform.dto.BlogDTO;
import com.swp391.DogCatLoverPlatform.dto.BlogUpdateDTO;
import com.swp391.DogCatLoverPlatform.dto.CommentDTO;
import com.swp391.DogCatLoverPlatform.entity.BlogTypeEntity;
import com.swp391.DogCatLoverPlatform.entity.CommentEntity;
import com.swp391.DogCatLoverPlatform.service.BlogService;
import com.swp391.DogCatLoverPlatform.service.BlogTypeService;
import com.swp391.DogCatLoverPlatform.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/blog")

public class BlogController {

    @Autowired
    BlogService blogService;

    @Autowired
    CommentService commentService;

    @Autowired
    BlogTypeService blogTypeService;

    @GetMapping("/view")
    public String GetAllBlogs(Model model) {
        List<BlogDTO> list = blogService.GetAllBlog();
        model.addAttribute("listBlog", list);

        List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();
        model.addAttribute("latestBlogs", latestBlogs);

        return "blog-standard";
    }
    @GetMapping("/byType")
    public String showBlogsByType(@RequestParam("type") String type, Model model) {
        List<BlogDTO> blogs = blogService.getBlogsByType(type);
        model.addAttribute("blogs", blogs);

        List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();
        model.addAttribute("latestBlogs", latestBlogs);
        return "blog-type";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        BlogDTO blogDTO = blogService.getBlogById(id);
        model.addAttribute("blog", blogDTO);
        return "update-blog-form";
    }


    @PostMapping("/{id}/edit")
    public String updateBlog(@PathVariable("id") int id, @ModelAttribute("blog") BlogUpdateDTO blogUpdateDTO) throws ParseException {
        blogService.updateBlog(id, blogUpdateDTO);
        return "redirect:/blog/view";
    }
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        List<BlogTypeEntity> listBlogType =  blogTypeService.getAllBlogType();
        model.addAttribute("blogTypes", listBlogType);
        model.addAttribute("blog", new BlogDTO());
        return "create-blog-form";
    }

    @PostMapping("/create")
    public String createBlog(@ModelAttribute("blog") BlogDTO blogDTO, @RequestParam("blogTypeId") int blogTypeId, @RequestParam("file") MultipartFile file, @RequestParam("file-sidebar") MultipartFile fileSidebar) throws IOException {
        // Lưu hình ảnh vào cơ sở dữ liệu và lấy đường dẫn
        String imagePath = blogService.saveImageAndReturnPath(file);
        String imageSidebar = blogService.saveImageAndReturnPath(fileSidebar);
        blogDTO.setImage(imagePath);
        blogDTO.setImageSidebar(imageSidebar);
        BlogDTO createdBlog = blogService.createBlog(blogDTO, blogTypeId);
        return "redirect:/blog/view" ;
    }

    @PostMapping("/{id}")
    public String deleteBlog(@PathVariable int id) {
        blogService.deleteBlogById(id);
        return "redirect:/blog/view";
    }

    @GetMapping("/{id}/detail")
    public String viewDetailsBlog(@PathVariable("id") int id, Model model) {
        BlogDTO blogDTO = blogService.getBlogById(id);
        List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();

        // Get comments for the blog by its ID
        List<CommentDTO> comments = commentService.getCommentsByBlogId(id);

        model.addAttribute("latestBlogs", latestBlogs);
        model.addAttribute("blog", blogDTO);

        // Add the comments to the model
        model.addAttribute("comments", comments);

        return "blog-details";
    }


   /* @GetMapping("/blog/{id_blog}")
    public String viewBlogWithComments(@PathVariable int id_blog, Model model) {
        List<CommentDTO> comments = commentService.getCommentsByBlogId(id_blog);
        model.addAttribute("comments", comments);
        return "blog-details";
    }*/

   @GetMapping("/blog/{id_blog}")
    public String viewBlogWithComments(@PathVariable int id_blog, Model model) {
        List<CommentDTO> comments = commentService.getCommentsByBlogId(id_blog);
        model.addAttribute("comments", comments);

        BlogDTO blogDTO = blogService.getBlogById(id_blog);
        List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();
        model.addAttribute("latestBlogs", latestBlogs);
        model.addAttribute("blog", blogDTO);

        return "blog-details";
    }


    @GetMapping("/search")
    public String viewSearch(){
        return "redirect:/blog/view";
    }

    @PostMapping("/search")
    public String searchBlogByTitle(@RequestParam("title") String title ,Model model){
        if(title.trim().isEmpty()){
            List<BlogDTO> list = blogService.GetAllBlog();
            model.addAttribute("listBlog", list);
        }else{
            List<BlogDTO> list = blogService.GetBlogsByTitle(title);
            if(list.isEmpty()){
                model.addAttribute("msg", "Không tìm thấy kết quả!!");
            }else{
                model.addAttribute("listBlogs", list);
            }

        }
        return "blog-standard";
    }


}
