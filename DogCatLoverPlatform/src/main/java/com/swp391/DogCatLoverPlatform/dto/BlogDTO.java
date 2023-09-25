package com.swp391.DogCatLoverPlatform.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BlogDTO {

    private int id;
    private String title;
    private String content;
    private String image;
    private double price;
    private boolean status;
    private Date createDate;
    private boolean confirm;
    private UserDTO userDTO;
    private BlogTypeDTO blogTypeDTO;


}
