package com.swp391.DogCatLoverPlatform.repository;

import com.swp391.DogCatLoverPlatform.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Integer> {
    @Query("SELECT s FROM service s  JOIN blog b ON s.blog_service = b.id WHERE b.confirm = :confirm")
    List<ServiceEntity> findByConfirm( Boolean confirm);
}
