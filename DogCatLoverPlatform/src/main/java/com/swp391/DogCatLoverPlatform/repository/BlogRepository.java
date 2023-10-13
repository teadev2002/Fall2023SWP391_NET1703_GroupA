package com.swp391.DogCatLoverPlatform.repository;

import com.swp391.DogCatLoverPlatform.entity.BlogEntity;
import com.swp391.DogCatLoverPlatform.entity.BlogTypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity, Integer> {

        List<BlogEntity> findAll();
        List<BlogEntity> findFirst3ByConfirmOrderByCreateDateDesc(boolean confirm);
        Page<BlogEntity> findByBlogTypeEntityAndConfirm(BlogTypeEntity blogTypeEntity, boolean confirm, Pageable pageable);
        Page<BlogEntity> findByTitleContainingAndConfirm(String title, boolean confirm, Pageable pageable);


        @Query("SELECT b FROM blog b WHERE b.userEntity.id = :userId AND b.confirm = :confirm")
        Page<BlogEntity> findByUserEntityIdAndConfirm(int userId, boolean confirm, Pageable pageable);

        List<BlogEntity> findByConfirm(Boolean confirm);
        Page<BlogEntity> findByConfirm(boolean confirm, Pageable pageable);

}
