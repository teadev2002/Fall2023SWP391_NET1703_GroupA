package com.swp391.DogCatLoverPlatform.service;

import com.swp391.DogCatLoverPlatform.dto.BlogDTO;
import com.swp391.DogCatLoverPlatform.dto.BlogTypeDTO;
import com.swp391.DogCatLoverPlatform.dto.UserDTO;
import com.swp391.DogCatLoverPlatform.entity.BlogEntity;
import com.swp391.DogCatLoverPlatform.repository.BlogRepository;
import com.swp391.DogCatLoverPlatform.entity.BlogEntity;
import com.swp391.DogCatLoverPlatform.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
public class BlogService {

    @Autowired
    BlogRepository blogRepository;

    public List<BlogDTO> GetAllBlog() {
        List<BlogEntity> listBlog = blogRepository.findAll();
        List<BlogDTO> listDto = new ArrayList<>();
        // mapper
        for (BlogEntity i : listBlog) {
            BlogDTO blogDTO = new BlogDTO();
            blogDTO.setId(i.getId());
            blogDTO.setImage(i.getImage());
            blogDTO.setPrice(i.getMaxPrice());
            blogDTO.setTitle(i.getTitle());

            UserDTO userDTO = new UserDTO();
            userDTO.setId(i.getUserEntity().getId());
            userDTO.setUserName(i.getUserEntity().getUserName());
            blogDTO.setUserDTO(userDTO);

            BlogTypeDTO blogTypeDTO = new BlogTypeDTO();
            blogTypeDTO.setId(i.getBlogTypeEntity().getId());
            blogTypeDTO.setName(i.getBlogTypeEntity().getName());
            blogDTO.setBlogTypeDTO(blogTypeDTO);

            listDto.add(blogDTO);
        }
        return listDto;
    }

    public List<BlogDTO> GetBlogsPriceRange(double minPrice, double maxPrice) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<BlogDTO> GetBlogsByTitle(String title) {
        List<BlogEntity> listBlog = blogRepository.findByTitleContaining(title);
        List<BlogDTO> listDto = new ArrayList<>();
        // mapper
        for (BlogEntity i : listBlog) {
            BlogDTO blogDTO = new BlogDTO();
            blogDTO.setId(i.getId());
            blogDTO.setImage(i.getImage());
            blogDTO.setPrice(i.getMaxPrice());
            blogDTO.setTitle(i.getTitle());

            UserDTO userDTO = new UserDTO();
            userDTO.setId(i.getUserEntity().getId());
            userDTO.setUserName(i.getUserEntity().getUserName());
            blogDTO.setUserDTO(userDTO);

            BlogTypeDTO blogTypeDTO = new BlogTypeDTO();
            blogTypeDTO.setId(i.getBlogTypeEntity().getId());
            blogTypeDTO.setName(i.getBlogTypeEntity().getName());
            blogDTO.setBlogTypeDTO(blogTypeDTO);

            listDto.add(blogDTO);
        }
        return listDto;
    }

    public List<BlogDTO> GetProfileBlogs(String userName) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<BlogDTO> GetBlogByTypeName(String blogTypeName) {
        // TODO Auto-generated method stub
        return null;
    }

    public BlogDTO CreateBlog(BlogEntity createBlogReq) {
        blogRepository.save(createBlogReq);
        return null;
    }

    public void DeleteBlog(int id) {
        // TODO Auto-generated method stub
        blogRepository.deleteById(id);
    }
//    public BlogDTO UpdateBlog(BlogEntity updateBlogRequest) {
//        // TODO Auto-generated method stub
//        Optional<BlogEntity> optionalBlog = blogRepository.findById(updateBlogRequest.getId());
//        if (optionalBlog.isPresent()) {
//            Blog existingBlog = optionalBlog.get();
//            // Update the fields based on the update request
//            existingBlog.setTitle(updateBlogRequest.getTitle());
//            existingBlog.setContent(updateBlogRequest.getContent());
//            existingBlog.setImage(updateBlogRequest.getImage());
//            existingBlog.setPrice(updateBlogRequest.getPrice());
//            existingBlog.setBlogType(updateBlogRequest.getBlogType());
//
//            BlogEntity updatedBlog = blogRepository.save(existingBlog);
//            return modelMapper.map(updatedBlog, BlogDTO.class);
//        } else {
//            // Handle the case where the blog with the given ID is not found
//            return null; // You can return an error message or throw an exception here
//        }
//    }
}
