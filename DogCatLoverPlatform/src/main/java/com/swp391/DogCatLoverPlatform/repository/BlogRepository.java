package com.swp391.DogCatLoverPlatform.repository;

import com.swp391.DogCatLoverPlatform.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface BlogRepository extends JpaRepository<BlogEntity, Integer> {
}
