package com.swp391.DogCatLoverPlatform.repository;

import com.swp391.DogCatLoverPlatform.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer>  {

//    public static List<UserEntity> listUser = new ArrayList<>();
//
//    public void add(UserEntity userEntity){
//        listUser.add(userEntity);
//    }
//
//    public List<UserEntity> getAll(){
//        return listUser;
//    }
//
//    public void delete(int id) {
//        for(UserEntity user : listUser ){
//            if(user.getId() == id){
//                listUser.remove(user);
//                break;
//            }
//        }
//    }


}
