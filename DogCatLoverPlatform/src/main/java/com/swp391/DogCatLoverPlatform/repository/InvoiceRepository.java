package com.swp391.DogCatLoverPlatform.repository;

import com.swp391.DogCatLoverPlatform.entity.InvoiceEntity;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Integer> {
    @Query(value = "SELECT i.* FROM invoice i INNER JOIN blog b on b.id  = i.id_blog where i.id_user = ?1",nativeQuery = true)
    public List<InvoiceEntity> findByUserCreate(Integer idUser);

}
