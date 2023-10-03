package com.swp391.DogCatLoverPlatform.service;

import com.swp391.DogCatLoverPlatform.config.ModelMapperConfig;
import com.swp391.DogCatLoverPlatform.dto.BlogDTO;
import com.swp391.DogCatLoverPlatform.dto.BlogTypeDTO;
import com.swp391.DogCatLoverPlatform.dto.BlogUpdateDTO;
import com.swp391.DogCatLoverPlatform.dto.UserDTO;
import com.swp391.DogCatLoverPlatform.entity.BlogEntity;
import com.swp391.DogCatLoverPlatform.entity.BlogTypeEntity;
import com.swp391.DogCatLoverPlatform.repository.BlogRepository;
import com.swp391.DogCatLoverPlatform.repository.BlogTypeRepository;
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
    ModelMapperConfig modelMapperConfig;


    public List<BlogDTO> GetAllBlog() {
        List<BlogEntity> listBlog = blogRepository.findAll();
        Collections.sort(listBlog, (blog1, blog2) -> blog2.getCreateDate().compareTo(blog1.getCreateDate()));
        List<BlogDTO> listBlogDTO = new ArrayList<>();

        for (BlogEntity blogEntity : listBlog) {
            BlogDTO blogDTO = modelMapperConfig.modelMapper().map(blogEntity, BlogDTO.class);
            listBlogDTO.add(blogDTO);
        }
        return listBlogDTO;
    }


    public BlogDTO createBlog(BlogDTO blogDTO, int blogTypeId) {
        BlogEntity blogEntity = modelMapperConfig.modelMapper().map(blogDTO, BlogEntity.class);
        blogEntity.setBlogTypeEntity(new BlogTypeEntity()); // -- Quan trọng
        blogEntity.getBlogTypeEntity().setId(blogTypeId);

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

    public BlogDTO getBlogById(int id) {
        BlogEntity blogEntity = blogRepository.findById(id).orElseThrow();
        BlogDTO blogDTO = modelMapperConfig.modelMapper().map(blogEntity, BlogDTO.class);
        return blogDTO;
    }

    public List<BlogDTO> GetBlogsByTitle(String title) {
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

}
