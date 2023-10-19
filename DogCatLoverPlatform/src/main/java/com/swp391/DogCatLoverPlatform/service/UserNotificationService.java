package com.swp391.DogCatLoverPlatform.service;

import com.swp391.DogCatLoverPlatform.config.ModelMapperConfig;
import com.swp391.DogCatLoverPlatform.dto.RequestDTO;
import com.swp391.DogCatLoverPlatform.dto.UserNotificationDTO;
import com.swp391.DogCatLoverPlatform.entity.UserNotificationEntity;
import com.swp391.DogCatLoverPlatform.repository.UserNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserNotificationService {
    @Autowired
    private ModelMapperConfig modelMapperConfig;

    @Autowired
    private UserNotificationRepository userNotificationRepository;

    public List<UserNotificationDTO> viewAllNotification(int id_user) {
        List<UserNotificationEntity> listNotification = userNotificationRepository.findAllByIdUser(id_user);

        List<UserNotificationDTO> listNotificated = listNotification.stream()
                .map(entity -> modelMapperConfig.modelMapper().map(entity, UserNotificationDTO.class))
                .collect(Collectors.toList());

        return listNotificated;
    }
}
