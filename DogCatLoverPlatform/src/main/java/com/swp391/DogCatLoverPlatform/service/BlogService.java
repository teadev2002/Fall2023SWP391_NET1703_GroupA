package com.swp391.DogCatLoverPlatform.service;

import com.swp391.DogCatLoverPlatform.config.ModelMapperConfig;
import com.swp391.DogCatLoverPlatform.dto.*;
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

//    @Autowired
//    private PetCategoryRepository petCategoryRepository;

    //Test Phân trang
    public Page<BlogDTO> GetApprovedBlogs(int pageNo, int pageSize) {
        // Định nghĩa trường sắp xếp là "createdAt" (hoặc trường bạn sử dụng cho thời gian tạo).
        Sort sort = Sort.by(Sort.Order.desc("createDate"));

        // Sử dụng PageRequest để tạo Pageable với sắp xếp theo trường createDate giảm dần.
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Page<BlogEntity> blogPage = blogRepository.findByConfirmAndStatusTrue(true, pageable);

        Page<BlogDTO> pageOfBlogDTO = blogPage.map(blogEntity -> modelMapperConfig.modelMapper().map(blogEntity, BlogDTO.class));

        return pageOfBlogDTO;

    }


    public Page<BlogDTO> GetBlogsByTitle(String title, int pageNo, int pageSize) {
        // Định nghĩa trường sắp xếp là "createdAt" (hoặc trường bạn sử dụng cho thời gian tạo).
        Sort sort = Sort.by(Sort.Order.desc("createDate"));

        // Sử dụng PageRequest để tạo Pageable với sắp xếp theo trường createDate giảm dần.
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Page<BlogEntity> blogPage = blogRepository.findByTitleContainingAndConfirmAndStatusTrue(title, true, pageable);

        return blogPage.map(blogEntity -> modelMapperConfig.modelMapper().map(blogEntity, BlogDTO.class));
    }

    public Page<BlogDTO> GetAllMyBlog(int id_user, int pageNo, int pageSize) {
        // Định nghĩa trường sắp xếp là "createdAt" (hoặc trường bạn sử dụng cho thời gian tạo).
        Sort sort = Sort.by(Sort.Order.desc("createDate"));

        // Sử dụng PageRequest để tạo Pageable với sắp xếp theo trường createDate giảm dần.
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Page<BlogEntity> listBlog = blogRepository.findByUserEntityIdAndConfirm(id_user, true, pageable);

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
/*
        PetCategoryDTO petCategoryDTO = new PetCategoryDTO();
        PetCategoryEntity petCategoryEntity = new PetCategoryEntity();
        petCategoryEntity.setName(petCategoryDTO.getName());
        petCategoryEntity.setBreed(petCategoryDTO.getBreed());
        petCategoryEntity.setAge(petCategoryDTO.getAge());
        petCategoryEntity.setColor(petCategoryDTO.getColor());
        petCategoryEntity.setWeight(petCategoryDTO.getWeight());

        // Save the petCategory entity to the database
        PetCategoryEntity savedPetCategory = petCategoryRepository.save(petCategoryEntity);*/

        // Retrieve the UserEntity by ID
        UserEntity userEntity = userRepository.findById(idUserCreated).orElseThrow();

        // Set the confirm field to a suitable value (e.g., false)
        blogEntity.setConfirm(null);

        blogEntity.setStatus(true);

        // Set the createDate field to the current date and time
        Date createDateTime = new Date();
        blogEntity.setCreateDate(createDateTime);

/*
        // Associate the saved petCategoryEntity with the blogEntity
        blogEntity.setPetCategoryEntity(savedPetCategory);
*/

        // Set the userEntity
        blogEntity.setUserEntity(userEntity);

        // Save the BlogEntity
        BlogEntity savedBlogEntity = blogRepository.save(blogEntity);

        // Map the saved BlogEntity back to a BlogDTO
        BlogDTO createdBlog = modelMapperConfig.modelMapper().map(savedBlogEntity, BlogDTO.class);

        // Set the createDate field in the createdBlog
        createdBlog.setCreateDate(createDateTime);

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
        List<BlogEntity> latestApprovedBlogs = blogRepository.findFirst3ByConfirmAndStatusTrueOrderByCreateDateDesc(true);
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
            Page<BlogEntity> blogPage = blogRepository.findByBlogTypeEntityAndConfirmAndStatusTrue(blogTypeEntity, true, pageable);

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
        List<BlogEntity> pendingBlogs = blogRepository.findByConfirmAndStatusTrue(null);
        List<BlogDTO> pendingBlogDTOs = new ArrayList<>();

        for (BlogEntity blogEntity : pendingBlogs) {
            BlogDTO blogDTO = modelMapperConfig.modelMapper().map(blogEntity, BlogDTO.class);
            pendingBlogDTOs.add(blogDTO);
        }

        return pendingBlogDTOs;
    }

    public List<BlogDTO> getBlogsReject() {
        List<BlogEntity> rejectBlogs = blogRepository.findByConfirmAndStatusTrue(false);
        List<BlogDTO> rejectBlogDTOs = new ArrayList<>();

        for (BlogEntity blogEntity : rejectBlogs) {
            BlogDTO blogDTO = modelMapperConfig.modelMapper().map(blogEntity, BlogDTO.class);
            rejectBlogDTOs.add(blogDTO);
        }

        return rejectBlogDTOs;
    }

    public void approveBlog(int blogId) {
        BlogEntity blogEntity = blogRepository.findById(blogId).orElseThrow();
        blogEntity.setConfirm(true);
        blogRepository.save(blogEntity);
    }


    public void rejectBlog(int blogId, String reason) {
        BlogEntity blogEntity = blogRepository.findById(blogId).orElseThrow();
        blogEntity.setConfirm(false);
        blogEntity.setReason(reason);
        blogRepository.save(blogEntity);
    }

    public void updateAndSetConfirmToNull(int blogId, BlogUpdateDTO blogUpdateDTO) {

        BlogEntity blogEntity = blogRepository.findById(blogId)
                .orElseThrow(() -> new NoSuchElementException("Blog not found with ID: " + blogId));

        // Set the 'confirm' property to null
        blogEntity.setConfirm(null);

        // Update the remaining properties using ModelMapper
        modelMapperConfig.modelMapper().map(blogUpdateDTO, blogEntity);

        // Save the updated entity

        blogRepository.save(blogEntity);
    }





}







