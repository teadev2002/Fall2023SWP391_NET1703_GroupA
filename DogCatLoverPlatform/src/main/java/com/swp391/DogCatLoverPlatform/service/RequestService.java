package com.swp391.DogCatLoverPlatform.service;

import com.swp391.DogCatLoverPlatform.config.ModelMapperConfig;
import com.swp391.DogCatLoverPlatform.dto.RequestDTO;
import com.swp391.DogCatLoverPlatform.entity.BlogEntity;
import com.swp391.DogCatLoverPlatform.entity.BookingEntity;
import com.swp391.DogCatLoverPlatform.entity.RequestEntity;
import com.swp391.DogCatLoverPlatform.entity.UserEntity;
import com.swp391.DogCatLoverPlatform.repository.BlogRepository;
import com.swp391.DogCatLoverPlatform.repository.RequestRepository;
import com.swp391.DogCatLoverPlatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RequestService {

    @Autowired
    private ModelMapperConfig modelMapperConfig;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private RequestRepository requestRepository;

    public boolean checkExistRequest(int userId, int blogId){
        boolean isExist = false;    //true (tồn tại),  false (chưa tồn tại)

        Optional<RequestEntity> requested = requestRepository.findByUserIdAndBlogId(userId, blogId);
        if(requested.isPresent()){
            isExist = true;
        }

        return isExist;
    }

    public RequestDTO AddRequest(RequestDTO requestDTO, int userId, int blogId){

            RequestEntity request = modelMapperConfig.modelMapper().map(requestDTO, RequestEntity.class);

             Date createDate = new Date();
             request.setCreateDate(createDate);

            UserEntity userEntity = userRepository.findById(userId).orElseThrow();
            request.setUserEntity_Request(userEntity);

            BlogEntity blogEntity = blogRepository.findById(blogId).orElseThrow();
            request.setBlogEntity_Request(blogEntity);

            RequestEntity sendRequest =  requestRepository.save(request);
            RequestDTO createdSendRequest = modelMapperConfig.modelMapper().map(sendRequest, RequestDTO.class);

            return createdSendRequest;
    }

    public List<RequestDTO> viewSendRequest(int id_user_created) {
        List<RequestEntity> listRequest = requestRepository.findAllRequestToBlogOwner(id_user_created);

        List<RequestDTO> listRequested = listRequest.stream()
                .map(entity -> modelMapperConfig.modelMapper().map(entity, RequestDTO.class))
                .collect(Collectors.toList());

        return listRequested;
    }
}
