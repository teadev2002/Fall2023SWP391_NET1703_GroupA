package com.swp391.DogCatLoverPlatform.repository;

import com.swp391.DogCatLoverPlatform.dto.StatisticUserDTO;
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

    @Query("SELECT u FROM users u WHERE u.roleEntity.name = 'ROLE_USER' ")
    List<UserEntity> findAllRoleUser();

    @Transactional
    @Modifying
    @Query("UPDATE users u SET u.otp = :otp WHERE u.id = :id")
    void updateOtpInUser(@Param("otp") String otp, @Param("id") int id);

    @Transactional
    @Modifying
    @Query("UPDATE users u SET u.password = :password WHERE u.id = :id")
    void updatePassInUser(@Param("password") String password, @Param("id") int id);


    @Query(nativeQuery = true, value =
            "SELECT u.*, COUNT(b.id) AS num_blogs \n" +
                    "FROM users u \n" +
                    "LEFT JOIN blog b ON u.id = b.id_user_created \n" +
                    "WHERE u.id_role = 1 \n" +
                    "GROUP BY u.id, u.user_name\n" +
                    "ORDER BY num_blogs DESC\n" +
                    "LIMIT 3;")
    List<UserEntity> findTop3UsersWithMostBlogs();


    @Query(nativeQuery = true, value =
            "SELECT COUNT(b.id) FROM blog b WHERE b.id_user_created = :userId")
    int getTotalBlogsByUserId(@Param("userId") int userId);

    @Query(value = "select count(*) from users", nativeQuery = true)
    int getAllUser();

    @Query(value = "select sum(u.account_balance) from booking b right join users u on u.id = b.id_user where u.id_role = 3 or u.id_role = 4", nativeQuery = true)
    int getTotalMoney();

    @Query(value = "SELECT u.*, COUNT(b.id) AS num_blogs FROM users u left join blog b ON u.id = b.id_user_created where u.id_role = 1 or u.id_role = 3 or u.id_role = 4 GROUP by u.id, u.user_name ", nativeQuery = true)
    List<UserEntity> getCountNumBlogByUser();

}
