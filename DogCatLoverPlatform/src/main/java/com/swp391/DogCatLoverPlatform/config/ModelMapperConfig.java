package com.swp391.DogCatLoverPlatform.config;

import com.swp391.DogCatLoverPlatform.dto.BlogDTO;
import com.swp391.DogCatLoverPlatform.dto.BlogTypeDTO;
import com.swp391.DogCatLoverPlatform.dto.UserDTO;
import com.swp391.DogCatLoverPlatform.entity.BlogEntity;
import com.swp391.DogCatLoverPlatform.entity.BlogTypeEntity;
import com.swp391.DogCatLoverPlatform.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}

//        ModelMapper modelMapper = new ModelMapper();
//        // Ánh xạ từ BlogEntity sang BlogDTO
//        modelMapper.createTypeMap(BlogEntity.class, BlogDTO.class)
//                .addMapping(src -> src.getBlogTypeEntity().getName(), BlogDTO::setBlogTypeName)
//                .addMapping(src -> src.getUserEntity().getUserName(), BlogDTO::setUserName);
