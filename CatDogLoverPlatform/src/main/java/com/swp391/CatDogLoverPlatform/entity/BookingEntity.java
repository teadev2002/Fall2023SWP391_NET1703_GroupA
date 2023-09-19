package com.swp391.CatDogLoverPlatform.entity;


import javax.persistence.*;
import java.util.Date;

@Entity(name = "booking")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "create_date")
    private Date create_date;

    @Column(name = "total_price")
    private double total_price;

    @Column(name = "paying_method")
    private String paying_method;

    @Column(name = "status")
    private boolean status;

    @Column(name = "id_user")
    private int id_user;

    @Column(name = "id_blog")
    private int id_blog;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getPaying_method() {
        return paying_method;
    }

    public void setPaying_method(String paying_method) {
        this.paying_method = paying_method;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_blog() {
        return id_blog;
    }

    public void setId_blog(int id_blog) {
        this.id_blog = id_blog;
    }
}
