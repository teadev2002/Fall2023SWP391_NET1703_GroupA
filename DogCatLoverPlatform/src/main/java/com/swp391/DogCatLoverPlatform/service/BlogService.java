package com.swp391.DogCatLoverPlatform.service;

import com.swp391.DogCatLoverPlatform.config.ModelMapperConfig;
import com.swp391.DogCatLoverPlatform.dto.BlogDTO;
import com.swp391.DogCatLoverPlatform.dto.BlogTypeDTO;
import com.swp391.DogCatLoverPlatform.dto.BlogUpdateDTO;
import com.swp391.DogCatLoverPlatform.dto.UserDTO;
import com.swp391.DogCatLoverPlatform.entity.BlogEntity;
import com.swp391.DogCatLoverPlatform.entity.BlogTypeEntity;
import com.swp391.DogCatLoverPlatform.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogService {
    @Autowired
    BlogRepository blogRepository;

    @Autowired

    ModelMapperConfig modelMapperConfig;


    public List<BlogDTO> GetAllBlog() {
        List<BlogEntity> listBlog = blogRepository.findAll();
        List<BlogDTO> listBlogDTO = new ArrayList<>();

        for (BlogEntity i : listBlog) {
            BlogDTO blogDTO = modelMapperConfig.modelMapper().map(i,BlogDTO.class);



            listBlogDTO.add(blogDTO);
        }
        return listBlogDTO;
    }

    public List<BlogDTO> GetBlogsPriceRange(double minPrice, double maxPrice) {
        // TODO Auto-generated method stub
        return null;
    }
    public BlogDTO getBlogById(int id){
        BlogEntity blogEntity = blogRepository.findById(id).orElseThrow();
        return modelMapperConfig.modelMapper().map(blogEntity,BlogDTO.class);
    }

    public BlogDTO createBlog(BlogDTO blogDTO, int blogTypeId) {
        BlogEntity blogEntity = modelMapperConfig.modelMapper().map(blogDTO, BlogEntity.class);
        blogEntity.setBlogTypeEntity(new BlogTypeEntity()); // -- Quan trọng
        blogEntity.getBlogTypeEntity().setId(blogTypeId);
        BlogEntity savedBlogEntity = blogRepository.save(blogEntity);
        BlogDTO createdBlog = modelMapperConfig.modelMapper().map(savedBlogEntity, BlogDTO.class);
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



//
//    public void DeleteBlog(int id) {
//        // TODO Auto-generated method stub
//        blogRepository.deleteById(id);
//    }
}
