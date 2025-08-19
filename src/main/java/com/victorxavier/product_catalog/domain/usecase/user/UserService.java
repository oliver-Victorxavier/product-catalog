package com.victorxavier.product_catalog.domain.usecase.user;

import com.victorxavier.product_catalog.domain.dto.UserDTO;
import com.victorxavier.product_catalog.domain.dto.UserInsertDTO;
import com.victorxavier.product_catalog.domain.dto.UserUpdateDto;
import com.victorxavier.product_catalog.domain.pagination.Page;
import com.victorxavier.product_catalog.domain.pagination.Pageable;

public interface UserService {
    Page<UserDTO> findAllPaged(Pageable pageable);
    Page<UserDTO> findAdminsPaged(Pageable pageable);
    UserDTO findById(String id);
    UserDTO findMe(String username);
    UserDTO registerUser(UserInsertDTO dto);
    UserDTO insert(UserInsertDTO dto);
    UserDTO insertAdmin(UserInsertDTO dto);
    UserDTO update(String id, UserUpdateDto dto);
    void delete(String id);
}