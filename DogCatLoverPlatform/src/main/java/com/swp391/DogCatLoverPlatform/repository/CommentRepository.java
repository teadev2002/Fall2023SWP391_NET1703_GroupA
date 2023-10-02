package com.swp391.DogCatLoverPlatform.repository;

import com.swp391.DogCatLoverPlatform.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    @Query(value = "SELECT comment.*, users.user_name " +
            "FROM comment " +
            "JOIN user_blog_comment ON comment.id = user_blog_comment.id_comment " +
            "JOIN users ON user_blog_comment.id_user = users.id " +
            "WHERE user_blog_comment.id_blog = :id_blog", nativeQuery = true)
    List<CommentEntity> findCommentsByBlogId(@Param("id_blog") Integer id_blog);
}


