package com.swp391.DogCatLoverPlatform.repository;

import com.swp391.DogCatLoverPlatform.entity.BlogEntity;
import com.swp391.DogCatLoverPlatform.entity.BlogTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity, Integer> {

        List<BlogEntity> findAll();
        List<BlogEntity> findTop3ByConfirmOrderByCreateDateDesc(boolean confirm);
        List<BlogEntity> findByBlogTypeEntity(BlogTypeEntity blogTypeEntity);

        //List<BlogEntity> findByPriceBetween(double minPrice, double maxPrice);

        List<BlogEntity> findByTitleContaining(String title);

        @Query("SELECT b FROM blog b WHERE b.userEntity.id = :userId")
        List<BlogEntity> findByUserEntityId(int userId);

        List<BlogEntity> findByConfirm(boolean confirm);

}
