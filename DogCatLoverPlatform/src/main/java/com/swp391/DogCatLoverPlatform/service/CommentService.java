package com.swp391.DogCatLoverPlatform.service;

import com.swp391.DogCatLoverPlatform.config.ModelMapperConfig;
import com.swp391.DogCatLoverPlatform.dto.CommentDTO;
import com.swp391.DogCatLoverPlatform.entity.BlogEntity;
import com.swp391.DogCatLoverPlatform.entity.CommentEntity;
import com.swp391.DogCatLoverPlatform.entity.UserEntity;
import com.swp391.DogCatLoverPlatform.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapperConfig modelMapperConfig;

    public List<CommentDTO> getCommentsByBlogId(Integer blogId) {
        List<CommentEntity> commentEntities = commentRepository.findCommentsByBlogId(blogId);

        List<CommentDTO> commentDTOs = commentEntities.stream()
                .map(entity -> modelMapperConfig.modelMapper().map(entity, CommentDTO.class))
                .collect(Collectors.toList());


        Collections.sort(commentDTOs, (comment1, comment2) ->
                comment2.getCreateDate().compareTo(comment1.getCreateDate()));

        return commentDTOs;
    }

    public void deleteCmtById(int id) {

        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else {
        }
    }

    public void createComment(String description, int idBlog, int idUser){
        CommentEntity commentEntity = new CommentEntity();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(idUser);
        commentEntity.setUserEntity_CommentEntity(userEntity);


        BlogEntity blogEntity = new BlogEntity();
        blogEntity.setId(idBlog);
        commentEntity.setBlogEntity_CommentEntity(blogEntity);

        commentEntity.setDescription(description);
        commentRepository.save(commentEntity);
    }


}


