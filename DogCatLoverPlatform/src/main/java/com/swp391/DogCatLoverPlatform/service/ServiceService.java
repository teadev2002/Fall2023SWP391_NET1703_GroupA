package com.swp391.DogCatLoverPlatform.service;

import com.swp391.DogCatLoverPlatform.dto.BlogDTO;
import com.swp391.DogCatLoverPlatform.dto.ServiceDTO;
import com.swp391.DogCatLoverPlatform.entity.BlogEntity;
import com.swp391.DogCatLoverPlatform.entity.ServiceCategoryEntity;
import com.swp391.DogCatLoverPlatform.entity.ServiceEntity;
import com.swp391.DogCatLoverPlatform.entity.UserEntity;
import com.swp391.DogCatLoverPlatform.repository.BlogRepository;
import com.swp391.DogCatLoverPlatform.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ServiceService {

    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    BlogRepository blogRepository;

    public ServiceEntity createService(String Content, int price, String title, int id_user, int serviceCategory, String image){
        ServiceEntity serviceEntity = new ServiceEntity();

        ServiceCategoryEntity serviceCategoryEntity = new ServiceCategoryEntity();
        serviceCategoryEntity.setId(serviceCategory);
        serviceEntity.setService_category(serviceCategoryEntity);

        BlogEntity blogEntity = new BlogEntity();
        Date date = new Date();
        blogEntity.setCreateDate(date);
        blogEntity.setConfirm(null);
        blogEntity.setStatus(false);
        blogEntity.setContent(Content);
        blogEntity.setImage(image);
        blogEntity.setPrice(price);
        blogEntity.setTitle(title);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id_user);
        blogEntity.setUserEntity(userEntity);
        blogRepository.save(blogEntity);

        serviceEntity.setBlog_service(blogEntity);

        serviceRepository.save(serviceEntity);
        return serviceEntity;
    }

    public List<ServiceDTO> getAllService(){
        List<ServiceEntity> serviceEntityList = serviceRepository.findByConfirm(true);
        List<ServiceDTO> serviceDTOList = new ArrayList<>();
        for(ServiceEntity s : serviceEntityList){
            if(s.getBlog_service().getConfirm().equals(true) && s.getBlog_service().getConfirm() != null) {
                ServiceDTO serviceDTO = new ServiceDTO();
                serviceDTO.setUserName(s.getBlog_service().getUserEntity().getName());
                serviceDTO.setEmailUserCreate(s.getBlog_service().getUserEntity().getEmail());
                serviceDTO.setContent(s.getBlog_service().getContent());
                serviceDTO.setPrice(s.getBlog_service().getPrice());
                serviceDTO.setTitle(s.getBlog_service().getTitle());
                serviceDTO.setImage(s.getBlog_service().getImage());
                serviceDTO.setConfirm(s.getBlog_service().getConfirm());
                serviceDTO.setCreateDate(s.getBlog_service().getCreateDate());
                serviceDTO.setServiceCateName(s.getService_category().getName());
                serviceDTO.setId(s.getId());
                serviceDTOList.add(serviceDTO);
            }
        }
        return serviceDTOList;
    }

    public ServiceDTO getServiceDetail(int id_service){
        Optional<ServiceEntity> s = serviceRepository.findById(id_service);
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setUserName(s.get().getBlog_service().getUserEntity().getName());
        serviceDTO.setEmailUserCreate(s.get().getBlog_service().getUserEntity().getEmail());
        serviceDTO.setContent(s.get().getBlog_service().getContent());
        serviceDTO.setPrice(s.get().getBlog_service().getPrice());
        serviceDTO.setTitle(s.get().getBlog_service().getTitle());
        serviceDTO.setImage(s.get().getBlog_service().getImage());
        serviceDTO.setConfirm(s.get().getBlog_service().getConfirm());
        serviceDTO.setCreateDate(s.get().getBlog_service().getCreateDate());
        serviceDTO.setServiceCateName(s.get().getService_category().getName());
        serviceDTO.setId_blog(s.get().getBlog_service().getId());
        serviceDTO.setId(s.get().getId());
        return serviceDTO ;
    }


}
