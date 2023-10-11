package com.swp391.DogCatLoverPlatform.service;

import com.swp391.DogCatLoverPlatform.config.ModelMapperConfig;
import com.swp391.DogCatLoverPlatform.dto.BlogDTO;
import com.swp391.DogCatLoverPlatform.dto.BlogTypeDTO;
import com.swp391.DogCatLoverPlatform.dto.BlogUpdateDTO;
import com.swp391.DogCatLoverPlatform.dto.UserDTO;
import com.swp391.DogCatLoverPlatform.entity.BlogEntity;
import com.swp391.DogCatLoverPlatform.entity.BlogTypeEntity;
import com.swp391.DogCatLoverPlatform.entity.UserEntity;
import com.swp391.DogCatLoverPlatform.repository.BlogRepository;
import com.swp391.DogCatLoverPlatform.repository.BlogTypeRepository;
import com.swp391.DogCatLoverPlatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BlogService {
    @Autowired
    BlogRepository blogRepository;

    @Autowired
    private BlogTypeRepository blogTypeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapperConfig modelMapperConfig;

    //Test Phân trang
    public Page<BlogDTO> GetApprovedBlogs(int pageNo, int pageSize) {
        // Định nghĩa trường sắp xếp là "createdAt" (hoặc trường bạn sử dụng cho thời gian tạo).
        Sort sort = Sort.by(Sort.Order.desc("createDate"));

        // Sử dụng PageRequest để tạo Pageable với sắp xếp theo trường createdAt giảm dần.
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Page<BlogEntity> blogPage = blogRepository.findByConfirm(true, pageable);

        Page<BlogDTO> pageOfBlogDTO = blogPage.map(blogEntity -> modelMapperConfig.modelMapper().map(blogEntity, BlogDTO.class));

        return pageOfBlogDTO;

    }

    public Page<BlogDTO> GetBlogsByTitle(String title, int pageNo, int pageSize) {
        // Định nghĩa trường sắp xếp là "createdAt" (hoặc trường bạn sử dụng cho thời gian tạo).
        Sort sort = Sort.by(Sort.Order.desc("createDate"));

        // Sử dụng PageRequest để tạo Pageable với sắp xếp theo trường createdAt giảm dần.
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Page<BlogEntity> blogPage = blogRepository.findByTitleContainingAndConfirm(title, true, pageable);

        return blogPage.map(blogEntity -> modelMapperConfig.modelMapper().map(blogEntity, BlogDTO.class));
    }

    public Page<BlogDTO> GetAllMyBlog(int id_user, int pageNo, int pageSize) {
        // Định nghĩa trường sắp xếp là "createdAt" (hoặc trường bạn sử dụng cho thời gian tạo).
        Sort sort = Sort.by(Sort.Order.desc("createDate"));

        // Sử dụng PageRequest để tạo Pageable với sắp xếp theo trường createdAt giảm dần.
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Page<BlogEntity> listBlog = blogRepository.findByUserEntityIdAndConfirm(id_user, true, pageable);
        //Collections.sort(listBlog, (blog1, blog2) -> blog2.getCreateDate().compareTo(blog1.getCreateDate()));
        Page<BlogDTO> pageOfBlogDTO = listBlog.map(blogEntity -> modelMapperConfig.modelMapper().map(blogEntity, BlogDTO.class));

        return pageOfBlogDTO;
    }


    public BlogDTO getBlogById(int id) {
        BlogEntity blogEntity = blogRepository.findById(id).orElseThrow();
        BlogDTO blogDTO = modelMapperConfig.modelMapper().map(blogEntity, BlogDTO.class);
        return blogDTO;
    }

    public BlogDTO createBlog(BlogDTO blogDTO, int blogTypeId, int idUserCreated) {
        BlogEntity blogEntity = modelMapperConfig.modelMapper().map(blogDTO, BlogEntity.class);
        blogEntity.setBlogTypeEntity(new BlogTypeEntity()); // -- Quan trọng
        blogEntity.getBlogTypeEntity().setId(blogTypeId);

        UserEntity userEntity = userRepository.findById(idUserCreated).orElseThrow();
        blogEntity.setUserEntity(userEntity);

        // Lấy thời gian tạo hiện tại
        Date createDate = new Date();
        blogEntity.setCreateDate(createDate);

        BlogEntity savedBlogEntity = blogRepository.save(blogEntity);
        BlogDTO createdBlog = modelMapperConfig.modelMapper().map(savedBlogEntity, BlogDTO.class);

        // Đặt thời gian tạo vào createdBlog
        createdBlog.setCreateDate(createDate);
        return createdBlog;
    }

    public void updateBlog(int id, BlogUpdateDTO blogUpdateDTO) {
        BlogEntity blogEntity = blogRepository.findById(id).orElseThrow();

        modelMapperConfig.modelMapper().map(blogUpdateDTO, blogEntity);
        blogRepository.save(blogEntity);
    }



    public void deleteBlogById(int id) {
        if (blogRepository.existsById(id)) {
            blogRepository.deleteById(id);
        } else {

        }
    }

    public List<BlogDTO> getThreeLatestBlogs() {
        List<BlogEntity> latestApprovedBlogs = blogRepository.findFirst3ByConfirmOrderByCreateDateDesc(true);
        List<BlogDTO> latestApprovedBlogDTOs = latestApprovedBlogs.stream()
                .map(blogEntity -> modelMapperConfig.modelMapper().map(blogEntity, BlogDTO.class))
                .collect(Collectors.toList());

        return latestApprovedBlogDTOs;
    }


    public Page<BlogDTO> getBlogsByType(String name, int pageNo, int pageSize) {
        BlogTypeEntity blogTypeEntity = blogTypeRepository.findByName(name);

        if (blogTypeEntity != null) {
            // Định nghĩa trường sắp xếp là "createdAt" (hoặc trường bạn sử dụng cho thời gian tạo).
            Sort sort = Sort.by(Sort.Order.desc("createDate"));

            // Sử dụng PageRequest để tạo Pageable với sắp xếp theo trường createdAt giảm dần.
            Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
            Page<BlogEntity> blogPage = blogRepository.findByBlogTypeEntityAndConfirm(blogTypeEntity, true, pageable);

            return blogPage.map(blogEntity -> modelMapperConfig.modelMapper().map(blogEntity, BlogDTO.class));
        } else {
            // Trả về trang rỗng nếu không tìm thấy loại blog
            return Page.empty();
        }
    }

    public String saveImageAndReturnPath(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String imagePath = fileName;
        File destination = new File(imagePath);
        file.transferTo(destination);
        return imagePath;
    }

    public List<BlogDTO> getBlogsPendingApproval() {
        List<BlogEntity> pendingBlogs = blogRepository.findByConfirm(false);
        List<BlogDTO> pendingBlogDTOs = new ArrayList<>();

        for (BlogEntity blogEntity : pendingBlogs) {
            BlogDTO blogDTO = modelMapperConfig.modelMapper().map(blogEntity, BlogDTO.class);
            pendingBlogDTOs.add(blogDTO);
        }

        return pendingBlogDTOs;
    }

    public void approveBlog(int blogId) {
        BlogEntity blogEntity = blogRepository.findById(blogId).orElseThrow();
        blogEntity.setConfirm(true);
        blogRepository.save(blogEntity);
    }

    public void rejectBlog(int blogId, String reason) {
        // Find the blog by its ID
        BlogEntity blog = blogRepository.findById(blogId).orElse(null);

        if (blog != null) {
            // Delete the blog from the database
            blogRepository.delete(blog);
        } else {
            // Handle the case where the blog is not found by ID
            // You can throw an exception, log an error, or handle it as per your requirements.
        }
    }

}





