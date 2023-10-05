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
import com.swp391.DogCatLoverPlatform.service.CommentService;
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
    public String showCreateForm(Model model, HttpServletRequest req) {
        List<BlogTypeEntity> listBlogType = blogTypeService.getAllBlogType();
        model.addAttribute("blogTypes", listBlogType);
        model.addAttribute("blog", new BlogDTO());
        return "create-blog-form";
    }

    @PostMapping("/create")
    public String createBlog(@ModelAttribute("blog") BlogDTO blogDTO, @RequestParam("blogTypeId") int blogTypeId, @RequestParam("file") MultipartFile file, HttpServletRequest req) throws IOException {
        // Lưu hình ảnh vào cơ sở dữ liệu và lấy đường dẫn
        String imagePath = blogService.saveImageAndReturnPath(file);
        UserDTO user  = getUserIdFromCookie(req);
        blogDTO.setImage(imagePath);
        BlogDTO createdBlog = blogService.createBlog(blogDTO, blogTypeId, user.getId());
        return "redirect:/blog/view/myblog";
    }

    @PostMapping("/create_comment")
    public String createComment(@ModelAttribute("comment") CommentDTO commentDTO, HttpServletRequest req){
        UserDTO user  = getUserIdFromCookie(req);
        String description = req.getParameter("description");
        int id_blog = Integer.parseInt(req.getParameter("id"));
        CommentDTO comment = commentService.createComment(commentDTO, description, id_blog, user.getId());

        return "redirect:/blog/"+id_blog+"/detail";
    }

    @PostMapping("/delete")
    public String deleteBlog(HttpServletRequest req) {
        // Extract the user's ID from the cookies (if available)
        int idBlog = Integer.parseInt(req.getParameter("idBlog"));
        UserDTO user  = getUserIdFromCookie(req);
        blogService.deleteBlogById(idBlog);
        return "redirect:/blog/view/myblog";
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

    @GetMapping("/{id}/detail/myblog")
    public String viewMyBlogDetails(@PathVariable("id") int id, Model model) {
        BlogDTO blogDTO = blogService.getBlogById(id);
        List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();

        // Get comments for the blog by its ID
        List<CommentDTO> comments = commentService.getCommentsByBlogId(id);

        model.addAttribute("latestBlogs", latestBlogs);
        model.addAttribute("blog", blogDTO);

        // Add the comments to the model
        model.addAttribute("comments", comments);

        return "blog-details-myblog";
    }


    @GetMapping("/search")
    public String viewSearch(Model model) {
        List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();
        model.addAttribute("latestBlogs", latestBlogs);
        return "redirect:/blog/view";
    }

    @PostMapping("/search")
    public String searchBlogByTitle(@RequestParam("title") String title, Model model) {
        List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();
        model.addAttribute("latestBlogs", latestBlogs);

        if (title.trim().isEmpty()) {
            List<BlogDTO> list = blogService.GetAllBlog();
            model.addAttribute("listBlog", list);
        } else {
            List<BlogDTO> list = blogService.GetBlogsByTitle(title);
            if (list.isEmpty()) {

                model.addAttribute("msg", "Không tìm thấy kết quả!!");
            } else {
                model.addAttribute("listBlogs", list);
            }

        }
        return "blog-standard";
    }

    @GetMapping("/view/myblog")
    public String viewMyBlog(Model model, HttpServletResponse response, HttpServletRequest req) {
        UserDTO user  = getUserIdFromCookie(req);
        List<BlogDTO> list = blogService.GetAllMyBlog(user.getId());
        model.addAttribute("listBlog", list);
        List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();
        model.addAttribute("latestBlogs", latestBlogs);
        model.addAttribute("user", user);
        return "myblog";
    }

    @PostMapping("/delete_comment")
    public String deleteComment(@RequestParam("commentId") int commentId, HttpServletRequest req) {
        UserDTO user = getUserIdFromCookie(req);
        if (user != null) {
            commentService.deleteComment(commentId);
        }
        int blogId = Integer.parseInt(req.getParameter("blogId"));
        return "redirect:/blog/" + blogId + "/detail";
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
