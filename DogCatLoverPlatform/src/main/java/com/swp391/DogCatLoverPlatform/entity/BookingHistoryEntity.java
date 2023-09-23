package com.swp391.DogCatLoverPlatform.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "booking_history")
public class BookingHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="create_date")
    private Date createDate;

    @Column(name="total_price")
    private double totalPrice;

    @Column(name = "paying_method")
    private String payingMethod;

    @Column(name="status")
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "id_booking")    //Checked
    private BookingEntity bookingEntity;



}
