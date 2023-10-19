package com.swp391.DogCatLoverPlatform.repository;

import com.swp391.DogCatLoverPlatform.entity.BookingEntity;
import com.swp391.DogCatLoverPlatform.entity.RequestEntity;
import com.swp391.DogCatLoverPlatform.entity.UserEntity;
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
            "WHERE b.id_user_created = :id_user_created AND r.status = 1", nativeQuery = true)
    List<RequestEntity> findAllRequestToBlogOwner(Integer id_user_created);

    //Kiểm tra nếu request đã tồn tại trong danh sách
    @Query(value = "SELECT r.*, u.user_name, b.title  FROM request r \n" +
            "JOIN blog b ON r.id_blog = b.id \n" +
            "JOIN users u  ON r.id_user  = u.id \n" +
            "WHERE r.id_blog = :blogId AND r.id_user = :userId ", nativeQuery = true)
    Optional <RequestEntity> findByUserIdAndBlogId(int userId, int blogId);


    @Query(value = "SELECT r.* FROM request r \n" +
                    "WHERE id_blog = :id_blog ", nativeQuery = true)
    List<RequestEntity> findAllByIdBlog(int id_blog);


//    @Query(value="SELECT un.*, b.title, u.user_name  FROM users_notification un \n" +
//            "JOIN users u ON un.id_sender = u.id \n" +
//            "JOIN blog b ON b.id_user_created = un.id_sender \n" +
//            "WHERE un.id_receiver =:idUser", nativeQuery = true)
//    String findBlogOwnerName(int idUser);

}
