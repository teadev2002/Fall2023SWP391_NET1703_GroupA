package com.swp391.DogCatLoverPlatform.controller;

import com.swp391.DogCatLoverPlatform.dto.BlogDTO;
import com.swp391.DogCatLoverPlatform.dto.BlogUpdateDTO;
import com.swp391.DogCatLoverPlatform.dto.CommentDTO;
import com.swp391.DogCatLoverPlatform.dto.UserDTO;
import com.swp391.DogCatLoverPlatform.entity.BlogTypeEntity;
import com.swp391.DogCatLoverPlatform.entity.CommentEntity;
import com.swp391.DogCatLoverPlatform.entity.UserEntity;
import com.swp391.DogCatLoverPlatform.service.BlogService;
import com.swp391.DogCatLoverPlatform.service.BlogTypeService;
//import com.swp391.DogCatLoverPlatform.service.CommentService;
import com.swp391.DogCatLoverPlatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/blog")

public class BlogController {

    @Autowired
    BlogService blogService;

    @Autowired
    UserService userService;

//    @Autowired
//    CommentService commentService;

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

    @GetMapping("/create/{id_user}")
    public String showCreateForm(@PathVariable("id_user") int id_user, Model model) {
        List<BlogTypeEntity> listBlogType =  blogTypeService.getAllBlogType();
        UserDTO userDTO = userService.getUserById(id_user);
        model.addAttribute("user", userDTO);
        model.addAttribute("blogTypes", listBlogType);
        model.addAttribute("blog", new BlogDTO());
        return "create-blog-form";
    }

    @PostMapping("/create/{id_user}")
    public String createBlog(@ModelAttribute("blog") BlogDTO blogDTO, @PathVariable("id_user") int id_user, @RequestParam("blogTypeId") int blogTypeId, @RequestParam("file") MultipartFile file) throws IOException {
        // Lưu hình ảnh vào cơ sở dữ liệu và lấy đường dẫn
        String imagePath = blogService.saveImageAndReturnPath(file);
        blogDTO.setImage(imagePath);
        BlogDTO createdBlog = blogService.createBlog(blogDTO, blogTypeId, id_user);
        return "redirect:/blog/view/myblog/{id_user}" ;
    }

    @PostMapping("/{id}")
    public String deleteBlog(@PathVariable int id, @RequestParam(name = "id_user") int id_user) {
        blogService.deleteBlogById(id);
        return "redirect:/blog/view/myblog/" + id_user;
    }

    @GetMapping("/{id}/detail")
    public String viewDetailsBlog(@PathVariable("id") int id, Model model) {
        BlogDTO blogDTO = blogService.getBlogById(id);
        List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();

//        // Get comments for the blog by its ID
//        List<CommentDTO> comments = commentService.getCommentsByBlogId(id);

        model.addAttribute("latestBlogs", latestBlogs);
        model.addAttribute("blog", blogDTO);

//        // Add the comments to the model
//        model.addAttribute("comments", comments);

        return "blog-details";
    }

    @GetMapping("/{id}/detail/myblog")
    public String viewMyBlogDetails(@PathVariable("id") int id, Model model) {
        BlogDTO blogDTO = blogService.getBlogById(id);
        List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();

//        // Get comments for the blog by its ID
//        List<CommentDTO> comments = commentService.getCommentsByBlogId(id);

        model.addAttribute("latestBlogs", latestBlogs);
        model.addAttribute("blog", blogDTO);

//        // Add the comments to the model
//        model.addAttribute("comments", comments);

        return "blog-details-myblog";
    }


   /* @GetMapping("/blog/{id_blog}")
    public String viewBlogWithComments(@PathVariable int id_blog, Model model) {
        List<CommentDTO> comments = commentService.getCommentsByBlogId(id_blog);
        model.addAttribute("comments", comments);
        return "blog-details";
    }*/

//   @GetMapping("/blog/{id_blog}")
//    public String viewBlogWithComments(@PathVariable int id_blog, Model model) {
//        List<CommentDTO> comments = commentService.getCommentsByBlogId(id_blog);
//        model.addAttribute("comments", comments);
//
//        BlogDTO blogDTO = blogService.getBlogById(id_blog);
//        List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();
//        model.addAttribute("latestBlogs", latestBlogs);
//        model.addAttribute("blog", blogDTO);
//
//        return "blog-details";
//    }


    @GetMapping("/search")
    public String viewSearch(Model model){
        List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();
        model.addAttribute("latestBlogs", latestBlogs);
       return "redirect:/blog/view";
    }

    @PostMapping("/search")
    public String searchBlogByTitle(@RequestParam("title") String title ,Model model){
        List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();
        model.addAttribute("latestBlogs", latestBlogs);

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

    @GetMapping("/view/myblog/{id_user}")
    public String viewMyBlog(@PathVariable("id_user") int id_user, Model model, HttpServletResponse response){
       List<BlogDTO> list = blogService.GetAllMyBlog(id_user);

        String id_users = String.valueOf(id_user);
        Cookie cookie = new Cookie("id_user", id_users);

        // Đặt các thuộc tính cho cookie (nếu cần)
        cookie.setMaxAge(24 * 60 * 60); // Đặt thời gian sống là 24 giờ (số giây)
        cookie.setPath("/"); // Đặt path cho cookie, "/" có nghĩa là toàn bộ ứng dụng

        // Thêm cookie vào phản hồi (response)
        response.addCookie(cookie);

       model.addAttribute("listBlog", list);

       List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();
       model.addAttribute("latestBlogs", latestBlogs);
//      model.addAttribute("id_user", id_user);
       return "myblog";
    }

    //Test-Cookies
    @GetMapping("/test")
    public String viewTest(HttpServletRequest request){

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("id_user")) {
                    String email = cookie.getValue();
                    System.out.println(email);
                }
            }
        }

        return "blog-standard";
    }

}
