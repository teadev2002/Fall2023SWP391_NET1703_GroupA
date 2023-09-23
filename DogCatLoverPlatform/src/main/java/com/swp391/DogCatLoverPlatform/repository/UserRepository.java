package com.swp391.DogCatLoverPlatform.repository;

import com.swp391.DogCatLoverPlatform.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer>  {
}
