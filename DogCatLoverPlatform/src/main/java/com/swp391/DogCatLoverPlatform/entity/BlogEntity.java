package com.swp391.DogCatLoverPlatform.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name= "blog")
public class BlogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "image")
    private String image;

    @Column(name = "min_price")
    private double minPrice;

    @Column(name = "max_price")
    private double maxPrice;

    @Column(name = "status")
    private boolean status;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "confirm")
    private boolean confirm;

    @ManyToOne
    @JoinColumn(name="id_user_create")     //Checked
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name="id_blog_type") //Checked
    private BlogTypeEntity blogTypeEntity;  

    @OneToMany(mappedBy = "blogEntity_UserBlogCommentEntity")   //Checked
    List<UserBlogCommentEntity> listUserBlogComment_BlogEntity;

    @OneToMany(mappedBy = "blogEntity_BookingEntity")
    List<BookingEntity> listBooking_BlogEntity;

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

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
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

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public BlogTypeEntity getBlogTypeEntity() {
        return blogTypeEntity;
    }

    public void setBlogTypeEntity(BlogTypeEntity blogTypeEntity) {
        this.blogTypeEntity = blogTypeEntity;
    }

    public List<UserBlogCommentEntity> getListUserBlogComment_BlogEntity() {
        return listUserBlogComment_BlogEntity;
    }

    public void setListUserBlogComment_BlogEntity(List<UserBlogCommentEntity> listUserBlogComment_BlogEntity) {
        this.listUserBlogComment_BlogEntity = listUserBlogComment_BlogEntity;
    }

    public List<BookingEntity> getListBooking_BlogEntity() {
        return listBooking_BlogEntity;
    }

    public void setListBooking_BlogEntity(List<BookingEntity> listBooking_BlogEntity) {
        this.listBooking_BlogEntity = listBooking_BlogEntity;
    }
}
