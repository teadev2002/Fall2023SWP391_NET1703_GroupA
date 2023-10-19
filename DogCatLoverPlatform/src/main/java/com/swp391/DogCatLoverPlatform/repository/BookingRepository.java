package com.swp391.DogCatLoverPlatform.repository;

import com.swp391.DogCatLoverPlatform.dto.BookingDTO;
import com.swp391.DogCatLoverPlatform.entity.BookingEntity;
import com.swp391.DogCatLoverPlatform.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity,Integer> {

    @Query(value = "select * from booking where booking_date = ?1 and id_blog = ?2",nativeQuery = true)
    public List<BookingEntity> findByDate(Date bookingDate,Integer idBlog);

    @Query(value = "select * from booking b b.id_user = ?1",nativeQuery = true)
    public List<BookingEntity> findByUser(Integer idUser);

    @Query(value = "select b.* from booking b inner JOIN blog bl on bl.id = b.id_blog where bl.id_user_created = ?1",nativeQuery = true)
    public List<BookingEntity> findByUserCreate(Integer idUser);

    @Query(value = "select b.* from booking b inner JOIN blog bl on bl.id = b.id_blog where b.id_user = ?1",nativeQuery = true)
    public List<BookingEntity> findByUserBooking(int idUser);
}
