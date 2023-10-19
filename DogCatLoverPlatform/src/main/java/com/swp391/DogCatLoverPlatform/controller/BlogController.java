package com.swp391.DogCatLoverPlatform.controller;

import com.swp391.DogCatLoverPlatform.dto.*;
import com.swp391.DogCatLoverPlatform.entity.BlogEntity;
import com.swp391.DogCatLoverPlatform.entity.BlogTypeEntity;
import com.swp391.DogCatLoverPlatform.service.*;
//import com.swp391.DogCatLoverPlatform.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    RequestService requestService;

    @Autowired
    CommentService commentService;

    @Autowired
    BlogTypeService blogTypeService;

    @Autowired
    EmailService emailService;

    @Autowired
    UserNotificationService userNotificationService;
//ong sua them id cho du method pót nay di, sao requestId = 6 ma no cu nhan 3
    @PostMapping("/accepted-request")
    public String acceptedRequest(@RequestParam(name="requestId") int requestId,
                                  @RequestParam(name="currentUserId") int userIdAccepted,
                                  @RequestParam(name="requestUserId") int userIdRequest,
                                  @RequestParam(name="blogId") int blogId,
                                  @ModelAttribute("request") UserNotificationDTO userNotificationDTO){

        requestService.acceptedRequest(userNotificationDTO, userIdRequest, userIdAccepted, requestId, blogId);


        return "redirect:/blog/view/view-request";
    }


    @PostMapping("/view/send-request")
    public String addRequest(
            @RequestParam(name = "userId") int userId,
            @RequestParam(name = "blogId") int blogId,
            RedirectAttributes redirectAttributes,
            @ModelAttribute("request") RequestDTO requestDTO) {

       boolean isExist = requestService.checkExistRequest(userId, blogId);
       if(isExist){
           redirectAttributes.addFlashAttribute("error", "Your request is on pending!");
       }else{
           requestService.AddRequest(requestDTO,userId, blogId);
           redirectAttributes.addFlashAttribute("sent", "You've already sent request!");
       }

        return "redirect:/blog/"+blogId+"/detail/myblog";
    }

    //View List Request
    @GetMapping("/view/view-request")
    public String viewListRequest(Model model, HttpServletRequest req){
        UserDTO user  = getUserIdFromCookie(req);

        if(user != null){
            List<UserNotificationDTO> userNotificationDTOS = userNotificationService.viewAllNotification(user.getId());
            //User đăng nhập hiện tại sẽ được xem danh sách những user khác đã gửi request trong bài Blog của mình.
            List<RequestDTO> bookingDTOS = requestService.viewSendRequest(user.getId());

            //Đếm số lượng thông báo và hiển thị
            int totalCount = bookingDTOS.size() + userNotificationDTOS.size();
            model.addAttribute("count", totalCount);
            model.addAttribute("listBlog", bookingDTOS);
            model.addAttribute("listNotification", userNotificationDTOS);
            model.addAttribute("currentUserId", user.getId());
        }
        return "list-request";
    }


    //Test phân trang
    @GetMapping("/view")
    public String GetPaginatedBlogs(Model model,
                                    @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "3") int size
                                    ,HttpServletRequest req) {

        Page<BlogDTO> list = blogService.GetApprovedBlogs(page, size);
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("listBlog", list);

        UserDTO user  = getUserIdFromCookie(req);
        model.addAttribute("user", user);

        //Hiện số lượng list
        if(user != null){
            List<UserNotificationDTO> userNotificationDTOS = userNotificationService.viewAllNotification(user.getId());
            List<RequestDTO> bookingDTOS = requestService.viewSendRequest(user.getId());
            int totalCount = bookingDTOS.size() + userNotificationDTOS.size();
            model.addAttribute("count", totalCount);
        }

        List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();
        model.addAttribute("latestBlogs", latestBlogs);


        return "blog-standard";
    }



    @GetMapping("/view/myblog")
    public String viewMyBlog(Model model,
                             @RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "3") int size,
                             HttpServletResponse response,
                             HttpServletRequest req) {

        UserDTO user  = getUserIdFromCookie(req);

        if(user != null){
            List<UserNotificationDTO> userNotificationDTOS = userNotificationService.viewAllNotification(user.getId());
            List<RequestDTO> bookingDTOS = requestService.viewSendRequest(user.getId());
            int totalCount = bookingDTOS.size() + userNotificationDTOS.size();
            model.addAttribute("count", totalCount);
        }
        model.addAttribute("user", user);

        Page<BlogDTO> list = blogService.GetAllMyBlog(user.getId(), page, size);
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("listBlog", list);


        List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();
        model.addAttribute("latestBlogs", latestBlogs);
        return "myblog";
    }

    @GetMapping("/search")
    public String viewSearch(Model model, @RequestParam("title") String title,
                             @RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "3") int size,
                             HttpServletRequest req) {

        Page<BlogDTO> list = blogService.GetApprovedBlogs(page, size);
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("listBlog", list);
        model.addAttribute("title", title);

        List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();
        model.addAttribute("latestBlogs", latestBlogs);

        UserDTO user  = getUserIdFromCookie(req);
        if(user != null){
            List<UserNotificationDTO> userNotificationDTOS = userNotificationService.viewAllNotification(user.getId());
            List<RequestDTO> bookingDTOS = requestService.viewSendRequest(user.getId());
            int totalCount = bookingDTOS.size() + userNotificationDTOS.size();
            model.addAttribute("count", totalCount);
        }
        model.addAttribute("user", user);

        return "redirect:/blog/view";
    }

    @GetMapping("/searchTitle")
    public String viewSearchTitle(Model model, @RequestParam("title") String title,
                             @RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "3") int size,
                             HttpServletRequest req) {

        Page<BlogDTO> lists = blogService.GetBlogsByTitle(title, page, size);
        model.addAttribute("totalPage", lists.getTotalPages()); // Thêm biến totalPage vào Model
        model.addAttribute("currentPage", page);
        model.addAttribute("listBlogs", lists);
        model.addAttribute("title",title);

        List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();
        model.addAttribute("latestBlogs", latestBlogs);

        UserDTO user  = getUserIdFromCookie(req);

        if(user != null){
            List<UserNotificationDTO> userNotificationDTOS = userNotificationService.viewAllNotification(user.getId());
            List<RequestDTO> bookingDTOS = requestService.viewSendRequest(user.getId());
            int totalCount = bookingDTOS.size() + userNotificationDTOS.size();
            model.addAttribute("count", totalCount);
        }
        model.addAttribute("user", user);

        return "blog-search-title";
    }

    @PostMapping("/search")
    public String searchBlogByTitle(
            @RequestParam("title") String title,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "3") int size,
            Model model, HttpServletRequest req) {

        UserDTO user  = getUserIdFromCookie(req);

        if(user != null){
            List<UserNotificationDTO> userNotificationDTOS = userNotificationService.viewAllNotification(user.getId());
            List<RequestDTO> bookingDTOS = requestService.viewSendRequest(user.getId());
            int totalCount = bookingDTOS.size() + userNotificationDTOS.size();
            model.addAttribute("count", totalCount);
        }
        model.addAttribute("user", user);

        if (title.trim().isEmpty()) {
           //Trường hợp tiêu đề rỗng hoặc khoảng trắng
            Page<BlogDTO> list = blogService.GetApprovedBlogs(page, size);
            model.addAttribute("totalPage", list.getTotalPages());
            model.addAttribute("currentPage", page);
            model.addAttribute("listBlog", list);

            if(user != null){
                List<UserNotificationDTO> userNotificationDTOS = userNotificationService.viewAllNotification(user.getId());
                List<RequestDTO> bookingDTOS = requestService.viewSendRequest(user.getId());
                int totalCount = bookingDTOS.size() + userNotificationDTOS.size();
                model.addAttribute("count", totalCount);
            }

            return "redirect:/blog/view";

        } else {
            // Trường hợp có tiêu đề, phân trang theo tiêu đề
            Page<BlogDTO> lists = blogService.GetBlogsByTitle(title, page, size);
            model.addAttribute("totalPage", lists.getTotalPages()); // Thêm biến totalPage vào Model
            model.addAttribute("currentPage", page);
            model.addAttribute("listBlogs", lists);
            model.addAttribute("title", title);
            if(user != null){
                List<UserNotificationDTO> userNotificationDTOS = userNotificationService.viewAllNotification(user.getId());
                List<RequestDTO> bookingDTOS = requestService.viewSendRequest(user.getId());
                int totalCount = bookingDTOS.size() + userNotificationDTOS.size();
                model.addAttribute("count", totalCount);
            }

            if (lists.isEmpty()) {
                model.addAttribute("msg", "Searching result is not found!!");
                if(user != null){
                    List<UserNotificationDTO> userNotificationDTOS = userNotificationService.viewAllNotification(user.getId());
                    List<RequestDTO> bookingDTOS = requestService.viewSendRequest(user.getId());
                    int totalCount = bookingDTOS.size() + userNotificationDTOS.size();
                    model.addAttribute("count", totalCount);
                }
            } else {
                model.addAttribute("listBlogs", lists);
                model.addAttribute("title", title);
                if(user != null){
                    List<UserNotificationDTO> userNotificationDTOS = userNotificationService.viewAllNotification(user.getId());
                    List<RequestDTO> bookingDTOS = requestService.viewSendRequest(user.getId());
                    int totalCount = bookingDTOS.size() + userNotificationDTOS.size();
                    model.addAttribute("count", totalCount);
                }
            }
        }

        // Lấy danh sách 3 bài viết mới nhất
        List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();
        model.addAttribute("latestBlogs", latestBlogs);

        return "blog-search-title";
    }

    @GetMapping("/byType")
    public String showBlogsByType(@RequestParam("type") String type,
                                  @RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "3") int size,
                                  Model model, HttpServletRequest req) {

        model.addAttribute("blogType", type);
        Page<BlogDTO> blogs = blogService.getBlogsByType(type, page, size);
        model.addAttribute("totalPage", blogs.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("blogs", blogs);

        List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();
        model.addAttribute("latestBlogs", latestBlogs);

        UserDTO user  = getUserIdFromCookie(req);

        if(user != null){
            List<UserNotificationDTO> userNotificationDTOS = userNotificationService.viewAllNotification(user.getId());
            List<RequestDTO> bookingDTOS = requestService.viewSendRequest(user.getId());
            int totalCount = bookingDTOS.size() + userNotificationDTOS.size();
            model.addAttribute("count", totalCount);
        }
        model.addAttribute("user", user);
        return "blog-type";
    }

    @GetMapping("/{id}/edit")
    public String viewUpdateForm(@PathVariable("id") int id, Model model, HttpServletRequest req) {
        BlogDTO blogDTO = blogService.getBlogById(id);
        model.addAttribute("blog", blogDTO);

        UserDTO user  = getUserIdFromCookie(req);

        if(user != null){
            List<UserNotificationDTO> userNotificationDTOS = userNotificationService.viewAllNotification(user.getId());
            List<RequestDTO> bookingDTOS = requestService.viewSendRequest(user.getId());
            int totalCount = bookingDTOS.size() + userNotificationDTOS.size();
            model.addAttribute("count", totalCount);
        }
        model.addAttribute("user", user);
        return "update-blog-form";
    }


    @PostMapping("/{id}/edit")
    public String updateBlog(@PathVariable("id") int id, @ModelAttribute("blog") BlogUpdateDTO blogUpdateDTO) throws ParseException {
        blogService.updateBlog(id, blogUpdateDTO);
        return "redirect:/blog/view";
    }

    @GetMapping("/create")
    public String viewCreateForm(Model model, HttpServletRequest req) {
        List<BlogTypeEntity> listBlogType = blogTypeService.getAllBlogType();

        UserDTO user  = getUserIdFromCookie(req);

        if(user != null){
            List<UserNotificationDTO> userNotificationDTOS = userNotificationService.viewAllNotification(user.getId());
            List<RequestDTO> bookingDTOS = requestService.viewSendRequest(user.getId());
            int totalCount = bookingDTOS.size() + userNotificationDTOS.size();
            model.addAttribute("count", totalCount);
        }
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
        blogDTO.setConfirm(null);

  /*      // Create a PetCategoryDTO based on the petCateId

        // Check if petType is not null
        if (petType != null) {
            blogDTO.setPet_type(petType);
        } else {
            blogDTO.setPet_type(false); // For example, setting it to false as a default
        }*/

        blogService.createBlog(blogDTO, blogTypeId, user.getId());

        // Chuyển hướng thành viên đến trang viewStaff
        return "redirect:/blog/view";
    }




    @PostMapping("/create_comment")
    public String createComment(@ModelAttribute("comment") CommentDTO commentDTO, HttpServletRequest req){
        UserDTO user  = getUserIdFromCookie(req);
        String description = req.getParameter("description");

        int id_blog = commentDTO.getId();
        BlogDTO blog = blogService.getBlogById(id_blog);
        commentService.createComment(commentDTO, description, id_blog, user.getId());

        // Chuyển hướng người dùng đến trang chi tiết của bài blog
        return "redirect:/blog/" + id_blog + "/detail";
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
    public String viewDetailsBlog(@PathVariable("id") int id, Model model, HttpServletRequest req) {
        BlogDTO blogDTO = blogService.getBlogById(id);
        List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();

        // Get comments for the blog by its ID
        List<CommentDTO> comments = commentService.getCommentsByBlogId(id);

        model.addAttribute("latestBlogs", latestBlogs);
        model.addAttribute("blog", blogDTO);

        UserDTO user  = getUserIdFromCookie(req);

        if(user != null){
            List<UserNotificationDTO> userNotificationDTOS = userNotificationService.viewAllNotification(user.getId());
            List<RequestDTO> bookingDTOS = requestService.viewSendRequest(user.getId());
            int totalCount = bookingDTOS.size() + userNotificationDTOS.size();
            model.addAttribute("count", totalCount);
        }
        model.addAttribute("user", user);

        // Add the comments to the model
        model.addAttribute("comments", comments);

        return "blog-details";
    }


    @GetMapping("/{id}/detail/myblog")
    public String viewMyBlogDetails(@PathVariable("id") int id, Model model, HttpServletRequest req, RedirectAttributes redirectAttributes) {
        BlogDTO blogDTO = blogService.getBlogById(id);
        List<BlogDTO> latestBlogs = blogService.getThreeLatestBlogs();

        model.addAttribute("latestBlogs", latestBlogs);
        model.addAttribute("blog", blogDTO);

        UserDTO user  = getUserIdFromCookie(req);

        if(user != null){
            List<UserNotificationDTO> userNotificationDTOS = userNotificationService.viewAllNotification(user.getId());
            List<RequestDTO> bookingDTOS = requestService.viewSendRequest(user.getId());
            int totalCount = bookingDTOS.size() + userNotificationDTOS.size();
            model.addAttribute("count", totalCount);
        }
        model.addAttribute("user", user);

        // Get comments for the blog by its ID
        // Add the comments to the model
        List<CommentDTO> comments = commentService.getCommentsByBlogId(id);
        model.addAttribute("comments", comments);

        String error = (String) redirectAttributes.getFlashAttributes().get("error");
        String sent = (String) redirectAttributes.getFlashAttributes().get("sent");
        if (error != null) {
            model.addAttribute("error", error);
        }

        if(sent != null){
            model.addAttribute("sent", sent);
        }

        return "blog-details-myblog";
    }


    @PostMapping("/delete_comment")
    public String deleteComment(@RequestParam("commentId") int commentId, HttpServletRequest req) {
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
    //Quản lý Blog bên Staff
    @GetMapping("/staff")
    public String viewBlogManagement(Model model){
        List<BlogDTO> pendingBlogs = blogService.getBlogsPendingApproval();
        model.addAttribute("pendingBlogs", pendingBlogs);
        return "table-staff";
    }


    @PostMapping("/staff/process")
    public String processBlog(
            @RequestParam("blogId") int blogId,
            @RequestParam("action") String action,
            @RequestParam(value = "reason", required = false) String reason,
            HttpServletRequest req
    ) {
        UserDTO user = getUserIdFromCookie(req);
        if ("approve".equals(action)) {
            // Approve the blog
            blogService.approveBlog(blogId);
            emailService.sendEmail(user.getEmail(),reason);

        } else if ("reject".equals(action)) {
            // Reject the blog
            blogService.rejectBlog(blogId, reason); // Add this method to your BlogService
            // Send a rejection email

            if (user != null) {
                emailService.sendEmail(user.getEmail(), reason);
            }
        }
        return "redirect:/blog/staff";
    }

    @GetMapping("/trash")
    public String viewBlogReject(Model model, @RequestParam(value = "updated", required = false) String updated) {
        List<BlogDTO> rejectBlog = blogService.getBlogsReject();
        model.addAttribute("rejectBlog", rejectBlog);
        model.addAttribute("updated", updated);
        return "trash";
    }


    @PostMapping("/trash")
    public String updateAndResubmitOrDeleteBlog(
            @RequestParam("blogId") int blogId,
            @ModelAttribute("blog") BlogUpdateDTO blogUpdateDTO,
            @RequestParam("action") String action) {

        if ("resubmit".equals(action)) {
            // Update the blog and set its confirmation status to null
            blogService.updateAndSetConfirmToNull(blogId, blogUpdateDTO);
        } else if ("delete".equals(action)) {
            // Delete the blog
            blogService.deleteBlogById(blogId);
        }

        return "redirect:/blog/trash";
    }

}