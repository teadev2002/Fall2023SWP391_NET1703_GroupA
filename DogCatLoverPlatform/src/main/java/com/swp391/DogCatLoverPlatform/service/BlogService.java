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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

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


    public List<BlogDTO> getPaginatedBlogs(int page, int size) {
        List<BlogEntity> listBlog = blogRepository.findAll();
        Collections.sort(listBlog, (blog1, blog2) -> blog2.getCreateDate().compareTo(blog1.getCreateDate()));

        int totalBlogs = listBlog.size();
        int totalPages = (int) Math.ceil((double) totalBlogs / size);

        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, totalBlogs);

        List<BlogEntity> paginatedBlogEntities = listBlog.subList(fromIndex, toIndex);

        List<BlogDTO> listBlogDTO = new ArrayList<>();

        for (BlogEntity blogEntity : paginatedBlogEntities) {
            BlogDTO blogDTO = modelMapperConfig.modelMapper().map(blogEntity, BlogDTO.class);
            listBlogDTO.add(blogDTO);
        }
        return listBlogDTO;
    }

    public List<BlogDTO> GetAllMyBlog(int id_user) {
        List<BlogEntity> listBlog = blogRepository.findByUserEntityId(id_user);
        Collections.sort(listBlog, (blog1, blog2) -> blog2.getCreateDate().compareTo(blog1.getCreateDate()));
        List<BlogDTO> listBlogDTO = new ArrayList<>();


        for (BlogEntity blogEntity : listBlog) {
            BlogDTO blogDTO = modelMapperConfig.modelMapper().map(blogEntity, BlogDTO.class);
            listBlogDTO.add(blogDTO);
        }
        return listBlogDTO;
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

    public List<BlogDTO> GetBlogsByTitle(String title, int page, int size) {
        List<BlogEntity> listBlog = blogRepository.findByTitleContaining(title);
        Collections.sort(listBlog, (blog1, blog2) -> blog2.getCreateDate().compareTo(blog1.getCreateDate()));
        List<BlogDTO> listBlogDTO = new ArrayList<>();

        // mapper
        for (BlogEntity i : listBlog) {
            BlogDTO blogDTO = modelMapperConfig.modelMapper().map(i, BlogDTO.class);
            listBlogDTO.add(blogDTO);
        }
        return listBlogDTO;
    }

    public void deleteBlogById(int id) {
        if (blogRepository.existsById(id)) {
            blogRepository.deleteById(id);
        } else {

        }
    }

    public List<BlogDTO> getThreeLatestBlogs() {
        List<BlogEntity> latestBlogs = blogRepository.findTop3ByOrderByCreateDateDesc();
        List<BlogDTO> latestBlogDTOs = new ArrayList<>();

        for (BlogEntity blogEntity : latestBlogs) {
            BlogDTO blogDTO = modelMapperConfig.modelMapper().map(blogEntity, BlogDTO.class);
            latestBlogDTOs.add(blogDTO);
        }

        return latestBlogDTOs;
    }


    public List<BlogDTO> getBlogsByType(String name) {
        BlogTypeEntity blogTypeEntity = blogTypeRepository.findByName(name);

        if (blogTypeEntity != null) {
            List<BlogEntity> blogEntities = blogRepository.findByBlogTypeEntity(blogTypeEntity);
            Collections.sort(blogEntities, (blog1, blog2) -> blog2.getCreateDate().compareTo(blog1.getCreateDate()));
            List<BlogDTO> blogDTOs = new ArrayList<>();
            for (BlogEntity blogEntity : blogEntities) {
                BlogDTO blogDTO = modelMapperConfig.modelMapper().map(blogEntity, BlogDTO.class);
                blogDTOs.add(blogDTO);
            }

            return blogDTOs;
        } else {

            return new ArrayList<>();
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



    public List<BlogDTO> GetApprovedBlogs(int page, int size) {
        List<BlogEntity> approvedBlogs = blogRepository.findByConfirm(true);
        Collections.sort(approvedBlogs, (blog1, blog2) -> blog2.getCreateDate().compareTo(blog1.getCreateDate()));
        List<BlogDTO> approvedBlogDTOs = new ArrayList<>();

        for (BlogEntity blogEntity : approvedBlogs) {
            BlogDTO blogDTO = modelMapperConfig.modelMapper().map(blogEntity, BlogDTO.class);
            approvedBlogDTOs.add(blogDTO);
        }

        return approvedBlogDTOs;
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





