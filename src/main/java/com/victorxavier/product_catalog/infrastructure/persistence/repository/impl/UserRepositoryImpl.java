package com.victorxavier.product_catalog.infrastructure.persistence.repository.impl;

import com.victorxavier.product_catalog.domain.mapper.UserMapper;
import com.victorxavier.product_catalog.domain.entity.User;
import com.victorxavier.product_catalog.domain.repository.UserRepository;
import com.victorxavier.product_catalog.infrastructure.persistence.repository.JpaUserRepository;
import com.victorxavier.product_catalog.domain.pagination.Page;
import com.victorxavier.product_catalog.domain.pagination.Pageable;
import com.victorxavier.product_catalog.infrastructure.persistence.adapter.PageableAdapter;
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
        org.springframework.data.domain.Pageable springPageable = PageableAdapter.toSpring(pageable);
        org.springframework.data.domain.Page<com.victorxavier.product_catalog.infrastructure.persistence.entity.UserEntity> springPage = 
            jpaUserRepository.findAll(springPageable);
        List<User> users = springPage.getContent().stream()
            .map(userMapper::toDomain)
            .collect(java.util.stream.Collectors.toList());
        return new Page<>(users, springPage.getNumber(), springPage.getSize(), springPage.getTotalElements());
    }

    @Override
    public boolean existsById(String id) {
        return jpaUserRepository.existsById(id);
    }
}