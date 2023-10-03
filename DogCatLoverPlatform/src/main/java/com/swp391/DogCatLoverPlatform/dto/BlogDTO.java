package com.swp391.DogCatLoverPlatform.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogDTO {

    private String title;
    private String content;
    private String image;
    private String imageSidebar;
    private Date createDate;
    private String userName;
    private String blogTypeName;
    private int id;
    private int blogTypeId;
    private double price;
    private boolean status;
    //private boolean confirm;

}
