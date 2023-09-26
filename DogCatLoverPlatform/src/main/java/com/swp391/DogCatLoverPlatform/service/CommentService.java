package com.swp391.DogCatLoverPlatform.service;

import com.swp391.DogCatLoverPlatform.dto.BlogDTO;
import com.swp391.DogCatLoverPlatform.dto.CommentDTO;
import com.swp391.DogCatLoverPlatform.dto.UserDTO;
import com.swp391.DogCatLoverPlatform.entity.BlogEntity;
import com.swp391.DogCatLoverPlatform.entity.CommentEntity;
import com.swp391.DogCatLoverPlatform.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<CommentDTO> getAllComment(){
        List<CommentEntity> listComment =  commentRepository.findAll();
        List<CommentDTO> listCommnetDTO = new ArrayList<>();
        for(CommentEntity data : listComment){
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(data.getId());
            commentDTO.setCreateDate(data.getCreateDate());
            commentDTO.setDescription(data.getDescription());

            UserDTO userDTO = new UserDTO();
            userDTO.setUserName(data.getUserEntity_CommentEntity().getUserName());

            commentDTO.setUserEntity_CommentDTO(userDTO);

            listCommnetDTO.add(commentDTO);

        }
        return listCommnetDTO;
     }

}


