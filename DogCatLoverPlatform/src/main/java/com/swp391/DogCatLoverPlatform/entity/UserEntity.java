package com.swp391.DogCatLoverPlatform.entity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name="id_role")         //Checked
    private RoleEntity roleEntity;

    @OneToMany(mappedBy = "userEntity")    //Checked
    List<BlogEntity> listBlogs;

    @OneToMany(mappedBy = "userEntity_UserBlogCommentEntity")     //Checked
    List<UserBlogCommentEntity> listUserBlogComment_UserEntity ;

    @OneToMany(mappedBy = "userEntity_CommentEntity")  //Checked
    List<CommentEntity> listComment_UserEntity ;

    @OneToMany(mappedBy = "userEntity_BookingEntity") //Checked
    List<BookingEntity> listBooking_UserEntity ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public RoleEntity getRoleEntity() {
        return roleEntity;
    }

    public void setRoleEntity(RoleEntity roleEntity) {
        this.roleEntity = roleEntity;
    }

    public List<BlogEntity> getListBlogs() {
        return listBlogs;
    }

    public void setListBlogs(List<BlogEntity> listBlogs) {
        this.listBlogs = listBlogs;
    }

    public List<UserBlogCommentEntity> getListUserBlogComment_UserEntity() {
        return listUserBlogComment_UserEntity;
    }

    public void setListUserBlogComment_UserEntity(List<UserBlogCommentEntity> listUserBlogComment_UserEntity) {
        this.listUserBlogComment_UserEntity = listUserBlogComment_UserEntity;
    }

    public List<CommentEntity> getListComment_UserEntity() {
        return listComment_UserEntity;
    }

    public void setListComment_UserEntity(List<CommentEntity> listComment_UserEntity) {
        this.listComment_UserEntity = listComment_UserEntity;
    }

    public List<BookingEntity> getListBooking_UserEntity() {
        return listBooking_UserEntity;
    }

    public void setListBooking_UserEntity(List<BookingEntity> listBooking_UserEntity) {
        this.listBooking_UserEntity = listBooking_UserEntity;
    }
}
