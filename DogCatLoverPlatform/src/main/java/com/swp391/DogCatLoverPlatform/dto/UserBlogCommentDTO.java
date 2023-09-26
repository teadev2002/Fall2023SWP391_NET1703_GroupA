package com.swp391.DogCatLoverPlatform.dto;

import com.swp391.DogCatLoverPlatform.entity.BlogEntity;
import com.swp391.DogCatLoverPlatform.entity.CommentEntity;
import com.swp391.DogCatLoverPlatform.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserBlogCommentDTO {

    private UserDTO userDTO;
    private BlogDTO blogDTO;
    private CommentDTO commentDTO;
}
