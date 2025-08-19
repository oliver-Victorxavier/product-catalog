package com.victorxavier.product_catalog.infrastructure.persistence.repository.impl;

import com.victorxavier.product_catalog.infrastructure.mapper.UserMapper;
import com.victorxavier.product_catalog.domain.entity.User;
import com.victorxavier.product_catalog.domain.repository.UserRepository;
import com.victorxavier.product_catalog.infrastructure.persistence.repository.JpaUserRepository;
import com.victorxavier.product_catalog.domain.pagination.Page;
import com.victorxavier.product_catalog.domain.pagination.Pageable;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository, UserMapper userMapper) {
        this.jpaUserRepository = jpaUserRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaUserRepository.findByUsername(username)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findById(String id) {
        return jpaUserRepository.findById(id)
                .map(userMapper::toDomain);
    }

    @Override
    public User save(User user) {
        var userEntity = userMapper.toEntity(user);
        var savedEntity = jpaUserRepository.save(userEntity);
        return userMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(String id) {
        jpaUserRepository.deleteById(id);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        // Converter Pageable do domínio para Spring Data
        org.springframework.data.domain.Pageable springPageable = convertToSpringPageable(pageable);
        org.springframework.data.domain.Page<com.victorxavier.product_catalog.infrastructure.persistence.entity.UserEntity> springPage = 
            jpaUserRepository.findAll(springPageable);
        List<User> users = springPage.getContent().stream()
            .map(userMapper::toDomain)
            .collect(java.util.stream.Collectors.toList());
        return new Page<>(users, springPage.getNumber(), springPage.getSize(), springPage.getTotalElements());
    }

    @Override
    public Page<User> findByRoleName(String roleName, Pageable pageable) {
        // Converter Pageable do domínio para Spring Data
        org.springframework.data.domain.Pageable springPageable = convertToSpringPageable(pageable);
        org.springframework.data.domain.Page<com.victorxavier.product_catalog.infrastructure.persistence.entity.UserEntity> springPage = 
            jpaUserRepository.findByRoleName(roleName, springPageable);
        List<User> users = springPage.getContent().stream()
            .map(userMapper::toDomain)
            .collect(java.util.stream.Collectors.toList());
        return new Page<>(users, springPage.getNumber(), springPage.getSize(), springPage.getTotalElements());
    }

    @Override
    public boolean existsById(String id) {
        return jpaUserRepository.existsById(id);
    }
    
    /**
     * Converte Pageable do domínio para Pageable do Spring Data
     */
    private org.springframework.data.domain.Pageable convertToSpringPageable(Pageable domainPageable) {
        if (domainPageable == null) {
            return PageRequest.of(0, 20);
        }

        if (domainPageable.getSort() != null && !domainPageable.getSort().isEmpty()) {
            Sort.Direction direction = domainPageable.getSortDirection() == com.victorxavier.product_catalog.domain.pagination.Sort.Direction.DESC
                ? Sort.Direction.DESC : Sort.Direction.ASC;
            Sort sort = Sort.by(direction, domainPageable.getSort());
            return PageRequest.of(domainPageable.getPageNumber(), domainPageable.getPageSize(), sort);
        }
        return PageRequest.of(domainPageable.getPageNumber(), domainPageable.getPageSize());
    }
}