package com.swp391.DogCatLoverPlatform.controller;

import com.swp391.DogCatLoverPlatform.dto.BlogDTO;
import com.swp391.DogCatLoverPlatform.dto.BlogUpdateDTO;
import com.swp391.DogCatLoverPlatform.dto.CommentDTO;
import com.swp391.DogCatLoverPlatform.dto.UserDTO;
import com.swp391.DogCatLoverPlatform.entity.BlogTypeEntity;
import com.swp391.DogCatLoverPlatform.service.BlogService;
import com.swp391.DogCatLoverPlatform.service.BlogTypeService;
//import com.swp391.DogCatLoverPlatform.service.CommentService;
import com.swp391.DogCatLoverPlatform.service.CommentService;
import com.swp391.DogCatLoverPlatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private CommentDTO commentDTO;
    private HttpServletRequest req;

    @GetMapping("/view")

    public String GetPaginatedBlogs(Model model, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "size", defaultValue = "5") int size) {
        List<BlogDTO> list = blogService.GetApprovedBlogs(page, size);

        model.addAttribute("listBlog", list);

        List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();
        model.addAttribute("latestBlogs", latestBlogs);


        return "blog-standard";
    }


    @GetMapping("/byType")
    public String showBlogsByType(@RequestParam("type") String type, Model model, HttpServletRequest req) {
        List<BlogDTO> blogs = blogService.getBlogsByType(type);
        model.addAttribute("blogs", blogs);

        List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();
        model.addAttribute("latestBlogs", latestBlogs);

        UserDTO user = getUserIdFromCookie(req);
        model.addAttribute("user", user);
        return "blog-type";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable("id") int id, Model model, HttpServletRequest req) {
        BlogDTO blogDTO = blogService.getBlogById(id);
        model.addAttribute("blog", blogDTO);

        UserDTO user = getUserIdFromCookie(req);
        model.addAttribute("user", user);
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
        UserDTO user = getUserIdFromCookie(req);

        model.addAttribute("user", user);
        model.addAttribute("blogTypes", listBlogType);
        model.addAttribute("blog", new BlogDTO());
        return "create-blog-form";
    }

    @PostMapping("/create")
    public String createBlog(@ModelAttribute("blog") BlogDTO blogDTO, @RequestParam("blogTypeId") int blogTypeId, @RequestParam("file") MultipartFile file, HttpServletRequest req) throws IOException {
        // Lưu hình ảnh vào cơ sở dữ liệu và lấy đường dẫn
        String imagePath = blogService.saveImageAndReturnPath(file);
        UserDTO user = getUserIdFromCookie(req);
        blogDTO.setImage(imagePath);

        // Đánh dấu bài viết là "đang chờ xét duyệt"
        blogDTO.setConfirm(false);

        BlogDTO createdBlog = blogService.createBlog(blogDTO, blogTypeId, user.getId());

        // Chuyển hướng thành viên đến trang viewStaff
        return "redirect:/blog/view";
    }


    @PostMapping("/create_comment")
    public String createComment(@ModelAttribute("comment") CommentDTO comment, HttpServletRequest req) {
        UserDTO user = getUserIdFromCookie(req);
        String description = comment.getDescription();
        int id_blog = comment.getId(); // Lấy id của bài blog từ trường ẩn
        BlogDTO blog = blogService.getBlogById(id_blog); // Tìm bài blog trong danh sách
        commentService.createComment(comment, description, id_blog, user.getId());

            // Chuyển hướng người dùng đến trang chi tiết của bài blog
            return "redirect:/blog/" + id_blog + "/detail";
        }

        @PostMapping("/delete")
        public String deleteBlog (HttpServletRequest req){
            // Extract the user's ID from the cookies (if available)
            int idBlog = Integer.parseInt(req.getParameter("idBlog"));
            UserDTO user = getUserIdFromCookie(req);
            blogService.deleteBlogById(idBlog);
            return "redirect:/blog/view/myblog";
        }


        @GetMapping("/{id}/detail")
        public String viewDetailsBlog ( @PathVariable("id") int id, Model model, HttpServletRequest req){
            BlogDTO blogDTO = blogService.getBlogById(id);
            List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();

            // Get comments for the blog by its ID
            List<CommentDTO> comments = commentService.getCommentsByBlogId(id);

            model.addAttribute("latestBlogs", latestBlogs);
            model.addAttribute("blog", blogDTO);

            UserDTO user = getUserIdFromCookie(req);
            model.addAttribute("user", user);

            // Add the comments to the model
            model.addAttribute("comments", comments);

            return "blog-details";
        }


        @GetMapping("/{id}/detail/myblog")
        public String viewMyBlogDetails ( @PathVariable("id") int id, Model model, HttpServletRequest req){
            BlogDTO blogDTO = blogService.getBlogById(id);
            List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();

            model.addAttribute("latestBlogs", latestBlogs);
            model.addAttribute("blog", blogDTO);

            UserDTO user = getUserIdFromCookie(req);
            model.addAttribute("user", user);

            // Get comments for the blog by its ID
            // Add the comments to the model
            List<CommentDTO> comments = commentService.getCommentsByBlogId(id);
            model.addAttribute("comments", comments);

            return "blog-details-myblog";
        }


        @GetMapping("/search")
        public String viewSearch (Model model, HttpServletRequest req){
            List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();
            model.addAttribute("latestBlogs", latestBlogs);

            UserDTO user = getUserIdFromCookie(req);
            model.addAttribute("user", user);

            return "redirect:/blog/view";
        }

    @PostMapping("/search")
    public String searchBlogByTitle(
            @RequestParam("title") String title,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            Model model
    ) {
        List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();
        model.addAttribute("latestBlogs", latestBlogs);

            UserDTO user = getUserIdFromCookie(req);
            model.addAttribute("user", user);

            if (title.trim().isEmpty()) {
                List<BlogDTO> list = blogService.getPaginatedBlogs(page, size);
                model.addAttribute("listBlog", list);
            } else {
                List<BlogDTO> list = blogService.GetBlogsByTitle(title, page, size);
                if (list.isEmpty()) {
                    model.addAttribute("msg", "Không tìm thấy kết quả!!");
                } else {
                    model.addAttribute("listBlogs", list);
                }
            }

            return "blog-standard";
        }


        @GetMapping("/view/myblog")
        public String viewMyBlog (Model model, HttpServletResponse response, HttpServletRequest req){
            UserDTO user = getUserIdFromCookie(req);
            List<BlogDTO> list = blogService.GetAllMyBlog(user.getId());
            model.addAttribute("listBlog", list);
            List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();
            model.addAttribute("latestBlogs", latestBlogs);
            model.addAttribute("user", user);
            return "myblog";
        }

        @PostMapping("/delete_comment")
        public String deleteComment ( @RequestParam("commentId") int commentId, HttpServletRequest req){
            UserDTO currentUser = getUserIdFromCookie(req);
            int blogId = Integer.parseInt(req.getParameter("blogId"));
            BlogDTO blogDTO = blogService.getBlogById(blogId);



            if (currentUser != null) {

                if (blogDTO != null && currentUser.getUserName().equals(blogDTO.getUserName()))
                    commentService.deleteComment(commentId);

            } else {

            }


            return "redirect:/blog/" + blogId + "/detail";
        }


        //GetUserIdFromCookie cực kỳ quan trọng!!!
        private UserDTO getUserIdFromCookie (HttpServletRequest req){
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

        @GetMapping("/staff")
        public String viewPendingBlogs (Model model){
            List<BlogDTO> pendingBlogs = blogService.getBlogsPendingApproval();
            model.addAttribute("pendingBlogs", pendingBlogs);
            return "viewStaff";
        }


        @PostMapping("/staff/process")
        public String processBlog ( @RequestParam("blogId") int blogId, @RequestParam("action") String action){
            if ("approve".equals(action)) {
                // Đánh dấu bài viết là đã được duyệt
                blogService.approveBlog(blogId);
            } else if ("reject".equals(action)) {
                // Xóa bài viết
                blogService.deleteBlogById(blogId);
            }
            return "redirect:/blog/staff";
        }
    }





