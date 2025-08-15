package com.victorxavier.product_catalog.domain.mapper;

import com.victorxavier.product_catalog.domain.dto.UserDTO;
import com.victorxavier.product_catalog.domain.entity.User;

public interface UserDomainMapper {
    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);
}