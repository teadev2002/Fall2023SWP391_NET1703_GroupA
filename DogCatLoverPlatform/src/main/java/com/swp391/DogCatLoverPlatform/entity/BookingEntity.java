package com.swp391.DogCatLoverPlatform.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne
    @JoinColumn(name="id_user") // tên cột khóa ngoại trong database   //Checked
    private UserEntity userEntity_BookingEntity; // tên Entity tham chiếu tới

    @ManyToOne
    @JoinColumn(name="id_blog") // tên cột khóa ngoại trong database   //Checked
    private BlogEntity blogEntity_BookingEntity; // tên Entity tham chiếu tới


    @OneToMany(mappedBy = "bookingEntity")
    List<BookingHistoryEntity> listBookingHistory;


}
