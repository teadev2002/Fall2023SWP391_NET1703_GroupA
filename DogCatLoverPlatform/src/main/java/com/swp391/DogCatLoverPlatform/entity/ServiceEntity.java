package com.swp391.DogCatLoverPlatform.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name= "service")
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "schedule")
    private String schedule;

    @OneToOne
    @JoinColumn(name = "id_blog") // Checked
    private BlogEntity blog_service;

    @ManyToOne
    @JoinColumn(name = "id_service_cate")
    private ServiceCategoryEntity service_category;
}
