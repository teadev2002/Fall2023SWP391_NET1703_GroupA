package com.swp391.DogCatLoverPlatform.dto;

import com.swp391.DogCatLoverPlatform.entity.UserBlogCommentEntity;
import com.swp391.DogCatLoverPlatform.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Date;

@Getter
@Setter
public class CommentDTO {

    private int id;
    private String description;
    private Date createDate;
    private int rating;
    UserBlogCommentDTO userBlogCommentDTO;
    private UserDTO userEntity_CommentDTO;
}
