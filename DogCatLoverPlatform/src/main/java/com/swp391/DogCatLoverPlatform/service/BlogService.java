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
import org.springframework.stereotype.Service;

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

        for (BlogEntity i : listBlog) {
            BlogDTO blogDTO = modelMapperConfig.modelMapper().map(i, BlogDTO.class);
            listBlogDTO.add(blogDTO);
        }
        return listBlogDTO;
    }


/*    public List<BlogDTO> GetBlogsPriceRange(double minPrice, double maxPrice) {
        // TODO Auto-generated method stub
        return null;
    }*/
    public BlogDTO getBlogById(int id){
        BlogEntity blogEntity = blogRepository.findById(id).orElseThrow();
        return modelMapperConfig.modelMapper().map(blogEntity,BlogDTO.class);
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

    public void updateBlog(int id, BlogUpdateDTO blogUpdateDTO){
        BlogEntity blogEntity = blogRepository.findById(id).orElseThrow();

        modelMapperConfig.modelMapper().map(blogUpdateDTO,blogEntity);
        blogRepository.save(blogEntity);
    }

//    public List<BlogDTO> GetBlogsByTitle(String title) {
//        List<BlogEntity> listBlog = blogRepository.findByTitleContaining(title);
//        List<BlogDTO> listBlogDTO = new ArrayList<>();
//        // mapper
//        for (BlogEntity i : listBlog) {
//            BlogDTO blogDTO = new BlogDTO();
//            blogDTO.setId(i.getId());
//            blogDTO.setImage(i.getImage());
//            blogDTO.setPrice(i.getMaxPrice());
//            blogDTO.setTitle(i.getTitle());
//
//            UserDTO userDTO = new UserDTO();
//            userDTO.setId(i.getUserEntity().getId());
//            userDTO.setUserName(i.getUserEntity().getUserName());
//            blogDTO.setUserDTO(userDTO);
//
//            BlogTypeDTO blogTypeDTO = new BlogTypeDTO();
//            blogTypeDTO.setId(i.getBlogTypeEntity().getId());
//            blogTypeDTO.setName(i.getBlogTypeEntity().getName());
//            blogDTO.setBlogTypeDTO(blogTypeDTO);
//
//            listBlogDTO.add(blogDTO);
//        }
//        return listBlogDTO;
//    }
//
//    public List<BlogDTO> GetProfileBlogs(String userName) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    public List<BlogDTO> GetBlogByTypeName(String blogTypeName) {
//        // TODO Auto-generated method stub
//        return null;
//    }


    public void deleteBlogById(int id) {
        // Check if the blog with the given ID exists
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



}
