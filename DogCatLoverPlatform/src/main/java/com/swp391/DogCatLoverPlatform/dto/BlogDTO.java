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
    private Date createDate;
    private String userName;
    private String blogTypeName;
    private int id;
    private int blogTypeId;
    private int userId;
    private double price;
    private boolean status;
    private boolean pet_type;
    private UserDTO userDTO;
    private String emailUserCreate;
    private Boolean confirm;
    private String reason;
    private String schedule;


    private PetCategoryDTO petCategoryDTO;
    private String petName;
    private String breed;
    private int age;
    private String color;
    private double weight;


}
