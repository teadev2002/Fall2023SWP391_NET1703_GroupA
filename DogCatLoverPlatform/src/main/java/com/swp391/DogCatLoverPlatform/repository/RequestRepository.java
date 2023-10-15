package com.swp391.DogCatLoverPlatform.repository;

import com.swp391.DogCatLoverPlatform.entity.BookingEntity;
import com.swp391.DogCatLoverPlatform.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<RequestEntity, Integer> {

    @Query(value = "SELECT r.* ,u.user_name, b.title  FROM request r \n" +
            "JOIN blog b ON r.id_blog = b.id \n" +
            "JOIN users u  ON r.id_user  = u.id \n" +
            "WHERE b.id_user_created = :id_user_created", nativeQuery = true)

    List<RequestEntity> findAllRequestToBlogOwner(Integer id_user_created);

    //Kiểm tra nếu request đã tồn tại trong danh sách
    @Query(value = "SELECT r.*, u.user_name, b.title  FROM request r \n" +
            "JOIN blog b ON r.id_blog = b.id \n" +
            "JOIN users u  ON r.id_user  = u.id \n" +
            "WHERE r.id_blog = :blogId AND r.id_user = :userId ", nativeQuery = true)
    Optional <RequestEntity> findByUserIdAndBlogId(int userId, int blogId);
}
