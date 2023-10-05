package com.swp391.DogCatLoverPlatform.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private String description;
    private int id_user;
    private int id_blog;
    private String userName;
    private Date createDate;
    private int id;
//    private int rating;

}
