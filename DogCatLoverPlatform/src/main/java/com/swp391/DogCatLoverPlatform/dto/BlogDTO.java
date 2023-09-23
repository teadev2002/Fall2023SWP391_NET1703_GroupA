package com.swp391.DogCatLoverPlatform.dto;

import com.swp391.DogCatLoverPlatform.entity.BlogTypeEntity;
import com.swp391.DogCatLoverPlatform.entity.UserEntity;


import java.util.Date;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public BlogTypeDTO getBlogTypeDTO() {
        return blogTypeDTO;
    }

    public void setBlogTypeDTO(BlogTypeDTO blogTypeDTO) {
        this.blogTypeDTO = blogTypeDTO;
    }
}
