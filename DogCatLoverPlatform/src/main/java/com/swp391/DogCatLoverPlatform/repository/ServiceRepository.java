package com.swp391.DogCatLoverPlatform.repository;

import com.swp391.DogCatLoverPlatform.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Integer> {

}
