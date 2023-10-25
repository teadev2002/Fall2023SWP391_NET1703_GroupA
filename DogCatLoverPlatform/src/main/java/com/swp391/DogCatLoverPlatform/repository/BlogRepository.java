package com.swp391.DogCatLoverPlatform.repository;

import com.swp391.DogCatLoverPlatform.entity.BlogEntity;
import com.swp391.DogCatLoverPlatform.entity.BlogTypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity, Integer> {

        List<BlogEntity> findAll();
        List<BlogEntity> findFirst3ByConfirmAndStatusTrueOrderByCreateDateDesc(boolean confirm);
        Page<BlogEntity> findByBlogTypeEntityAndConfirmAndStatusTrue(BlogTypeEntity blogTypeEntity, boolean confirm, Pageable pageable);
        Page<BlogEntity> findByTitleContainingAndConfirmAndStatusTrue(String title, boolean confirm, Pageable pageable);


        @Query("SELECT b FROM blog b WHERE b.userEntity.id = :userId AND b.confirm = :confirm AND b.status = true")
        Page<BlogEntity> findByUserEntityIdAndConfirm(int userId, boolean confirm, Pageable pageable);

        List<BlogEntity> findByConfirm(Boolean confirm);
        List<BlogEntity> findByConfirmAndStatusTrue(Boolean confirm);
        Page<BlogEntity> findByConfirmAndStatusTrue(boolean confirm, Pageable pageable);

        @Transactional
        @Modifying
        @Query("UPDATE blog b SET b.status = false WHERE b.id = :id")
        void updateStatus(int id);

        List<BlogEntity> findByUserEntityIdAndConfirmAndStatusTrue(Integer userId, Boolean confirm);

}
