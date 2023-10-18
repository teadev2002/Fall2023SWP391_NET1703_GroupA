package com.swp391.DogCatLoverPlatform.repository;

import com.swp391.DogCatLoverPlatform.entity.UserNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotificationEntity, Integer> {

    @Query(value="SELECT un.*, u.id, u.user_name, b.title, un.message \n" +
            "FROM users_notification un \n" +
            "JOIN users u ON un.id_sender = u.id \n" +
            "JOIN request r ON r.id = un.id_request \n" +
            "JOIN blog b ON b.id = r.id_blog \n" +
            "WHERE un.id_receiver = :idUser", nativeQuery = true)
    List<UserNotificationEntity> findAllByIdUser(Integer idUser);
}
