package com.swp391.DogCatLoverPlatform.service;

import com.swp391.DogCatLoverPlatform.dto.BlogDTO;
import com.swp391.DogCatLoverPlatform.dto.BlogTypeDTO;
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

    public List<BlogDTO> getAllBlog() {
        List<BlogEntity> listBlog = blogRepository.findAll();
        List<BlogDTO> listBlogDTO = new ArrayList<>();

        for(BlogEntity data : listBlog){
            BlogDTO blogDTO = new BlogDTO();
            blogDTO.setId(data.getId());
            blogDTO.setTitle(data.getTitle());
            blogDTO.setContent(data.getContent());
            blogDTO.setImage(data.getImage());
            blogDTO.setCreateDate(data.getCreateDate());

            BlogTypeDTO blogTypeDTO = new BlogTypeDTO();
            blogTypeDTO.setName(data.getBlogTypeEntity().getName());
            blogDTO.setBlogType(blogTypeDTO);

            listBlogDTO.add(blogDTO);
        }
        return listBlogDTO;
    }
}
