package com.swp391.DogCatLoverPlatform.service;

import com.swp391.DogCatLoverPlatform.config.ModelMapperConfig;
import com.swp391.DogCatLoverPlatform.dto.*;
import com.swp391.DogCatLoverPlatform.entity.*;

import com.swp391.DogCatLoverPlatform.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
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

    @Autowired
   private PetCategoryRepository petCategoryRepository;

    @Autowired
    private PetTypeRepository petTypeRepository;

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


    public BlogEntity createNewBlog(int blogType, int petType, String image, String title, String content, double price, String petName, String petBreed, int petAge, double petWeight, String petColor, int id_user_create) {
        BlogEntity blogEntity = new BlogEntity();

        //lưu blog type của bài viết
        BlogTypeEntity blogTypeEntity = new BlogTypeEntity();
        blogTypeEntity.setId(blogType);
        blogEntity.setBlogTypeEntity(blogTypeEntity);
        //lưu user create bài viết
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id_user_create);
        blogEntity.setUserEntity(userEntity);
        //lưu thông tin của pet
        PetCategoryEntity petCategoryEntity = new PetCategoryEntity();
        petCategoryEntity.setAge(petAge);
        petCategoryEntity.setBreed(petBreed);
        petCategoryEntity.setName(petName);
        petCategoryEntity.setColor(petColor);
        petCategoryEntity.setWeight(petWeight);
        petCategoryRepository.save(petCategoryEntity);
        blogEntity.setPetCategoryEntity(petCategoryEntity);
        //lưu thông tin loại thú cưng
        PetTypeEntity petTypeEntity = new PetTypeEntity();
        petTypeEntity.setId(petType);
        blogEntity.setPetTypeEntity(petTypeEntity);
        //lưu thông tin của blog
        Date date = new Date();
        blogEntity.setCreateDate(date);
        blogEntity.setConfirm(null);
        blogEntity.setStatus(true);
        blogEntity.setContent(content);
        blogEntity.setImage(image);
        blogEntity.setPrice(price);
        blogEntity.setTitle(title);
        blogRepository.save(blogEntity);
        return blogEntity ;
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

    public void updateAndSetConfirmToNull(int blogId, BlogUpdateDTO blogUpdateDTO,int blogType) {

        BlogEntity blogEntity = blogRepository.findById(blogId)
                .orElseThrow(() -> new NoSuchElementException("Blog not found with ID: " + blogId));

        // Set the 'confirm' property to null
        blogEntity.setConfirm(null);

        // Update the remaining properties using ModelMapper
        modelMapperConfig.modelMapper().map(blogUpdateDTO, blogEntity);

        BlogTypeEntity blogTypeEntity = new BlogTypeEntity();
        blogTypeEntity.setId(blogType);
        blogEntity.setBlogTypeEntity(blogTypeEntity);
        // Save the updated entity

        blogRepository.save(blogEntity);
    }

    public void updateBlogToFalse(int idBlog){
        blogRepository.updateStatus(idBlog);
    }




}







