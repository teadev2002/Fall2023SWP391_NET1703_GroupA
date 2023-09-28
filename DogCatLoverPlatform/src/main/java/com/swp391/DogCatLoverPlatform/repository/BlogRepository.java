package com.swp391.DogCatLoverPlatform.repository;

import com.swp391.DogCatLoverPlatform.entity.BlogEntity;
import com.swp391.DogCatLoverPlatform.entity.BlogTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity, Integer> {

        List<BlogEntity> findAll();
        List<BlogEntity> findTop3ByOrderByCreateDateDesc();
        List<BlogEntity> findByBlogTypeEntity(BlogTypeEntity blogTypeEntity);


        //List<BlogEntity> findByPriceBetween(double minPrice, double maxPrice);

        List<BlogEntity> findByTitleContaining(String title);

        List<BlogEntity> findByTitle(String userName);

}
