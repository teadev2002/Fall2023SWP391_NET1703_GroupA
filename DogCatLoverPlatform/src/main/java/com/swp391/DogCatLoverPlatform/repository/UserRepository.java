package com.swp391.DogCatLoverPlatform.repository;

import com.swp391.DogCatLoverPlatform.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer>  {
    UserEntity findByEmailAndPassword(String email, String password);
    UserEntity findByEmail(String email);


    @Query("SELECT u FROM users u " +
            "WHERE u.roleEntity.name = 'ROLE_STAFF' OR u.roleEntity.name = 'ROLE_NULL' ")
    List<UserEntity> findAllStaffAndNull();


    @Transactional
    @Modifying
    @Query("UPDATE users u SET u.otp = :otp WHERE u.id = :id")
    void updateOtpInUser(@Param("otp") String otp, @Param("id") int id);

    @Transactional
    @Modifying
    @Query("UPDATE users u SET u.password = :password WHERE u.id = :id")
    void updatePassInUser(@Param("password") String password, @Param("id") int id);

}
