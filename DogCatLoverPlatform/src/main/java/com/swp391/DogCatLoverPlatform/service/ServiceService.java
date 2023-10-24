package com.swp391.DogCatLoverPlatform.service;

import com.swp391.DogCatLoverPlatform.config.ModelMapperConfig;
import com.swp391.DogCatLoverPlatform.dto.BlogDTO;
import com.swp391.DogCatLoverPlatform.dto.ServiceCategoryDTO;
import com.swp391.DogCatLoverPlatform.dto.ServiceDTO;
import com.swp391.DogCatLoverPlatform.entity.BlogEntity;
import com.swp391.DogCatLoverPlatform.entity.ServiceCategoryEntity;
import com.swp391.DogCatLoverPlatform.entity.ServiceEntity;
import com.swp391.DogCatLoverPlatform.entity.UserEntity;
import com.swp391.DogCatLoverPlatform.repository.BlogRepository;
import com.swp391.DogCatLoverPlatform.repository.ServiceCategoryRepository;
import com.swp391.DogCatLoverPlatform.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServiceService {

    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    BlogRepository blogRepository;

    @Autowired
    ModelMapperConfig modelMapperConfig;

    public ServiceEntity createService(String Content, int price, String title, int id_user, int serviceCategory, String image){
        ServiceEntity serviceEntity = new ServiceEntity();

        ServiceCategoryEntity serviceCategoryEntity = new ServiceCategoryEntity();
        serviceCategoryEntity.setId(serviceCategory);
        serviceEntity.setService_category(serviceCategoryEntity);

        BlogEntity blogEntity = new BlogEntity();
        Date date = new Date();
        blogEntity.setCreateDate(date);
        blogEntity.setConfirm(null);
        blogEntity.setStatus(null);
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

    public Page<ServiceDTO> getAllService(int pageNo, int pageSize) {
        // Định nghĩa trường sắp xếp là "createDate" trong đối tượng BlogDTO của ServiceDTO.
        Sort sort = Sort.by(Sort.Order.desc("b.create_date"));

        // Sử dụng PageRequest để tạo Pageable với sắp xếp theo trường createDate giảm dần.
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Page<ServiceEntity> serviceEntityList = serviceRepository.findByConfirm(true, pageable);

        // Tạo một danh sách chứa các đối tượng ServiceDTO.
        List<ServiceDTO> serviceDTOList = new ArrayList<>();

        for (ServiceEntity s : serviceEntityList) {
            if (s.getBlog_service().getConfirm() != null && s.getBlog_service().getConfirm().equals(true)) {
                ServiceDTO serviceDTO = new ServiceDTO();

                // Truy cập đối tượng BlogDTO trong ServiceDTO để lấy createDate.
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

        // Sử dụng PageImpl để tạo một trang mới từ danh sách ServiceDTO và pageable.
        return new PageImpl<>(serviceDTOList, pageable, serviceEntityList.getTotalElements());
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

    @Autowired
    ServiceCategoryRepository serviceCategoryRepository;

    public List<ServiceCategoryDTO> getServiceCategoryEntityList(){
        List<ServiceCategoryEntity> serviceCategoryEntityList = serviceCategoryRepository.findAll();
        List<ServiceCategoryDTO> serviceCategoryDTOList = new ArrayList<>();

        for (ServiceCategoryEntity s : serviceCategoryEntityList){
            ServiceCategoryDTO serviceCategoryDTO = new ServiceCategoryDTO();
            serviceCategoryDTO.setId(s.getId());
            serviceCategoryDTO.setName(s.getName());

            serviceCategoryDTOList.add(serviceCategoryDTO);
        }
        return  serviceCategoryDTOList;
    }


    public List<ServiceDTO> getThreeLatestBlogs() {
        List<ServiceEntity> latestApprovedServices = serviceRepository.findFirst3OrderByCreateDateDesc();
        List<ServiceDTO> latestApprovedServiceDTOs = latestApprovedServices.stream()
                .map(serviceEntity -> modelMapperConfig.modelMapper().map(serviceEntity, ServiceDTO.class))
                .collect(Collectors.toList());

        return latestApprovedServiceDTOs;
    }

}
