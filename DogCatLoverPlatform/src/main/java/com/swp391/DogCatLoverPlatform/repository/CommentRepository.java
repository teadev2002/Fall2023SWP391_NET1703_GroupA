package com.swp391.DogCatLoverPlatform.repository;

import com.swp391.DogCatLoverPlatform.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    @Query(value = "SELECT c.*, u.user_name, u.full_name, u.email, u.phone, u.address, u.image\n" +
            "FROM comment c\n" +
            "JOIN users u ON c.id_user = u.id\n" +
            "WHERE c.id_blog = :id_blog", nativeQuery = true)
    List<CommentEntity> findCommentsByBlogId(@Param("id_blog") Integer id_blog);
}


