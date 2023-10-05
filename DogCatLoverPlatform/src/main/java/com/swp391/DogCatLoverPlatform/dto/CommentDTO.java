package com.swp391.DogCatLoverPlatform.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentDTO {

    private int id;
    private String description;
    private Date createDate;
    private int rating;
    private UserDTO userDTO;
    private String userName;
}
