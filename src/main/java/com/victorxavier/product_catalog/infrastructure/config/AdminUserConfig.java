package com.victorxavier.product_catalog.infrastructure.config;

import com.victorxavier.product_catalog.domain.entity.Role;
import com.victorxavier.product_catalog.domain.entity.User;
import com.victorxavier.product_catalog.domain.repository.RoleRepository;
import com.victorxavier.product_catalog.domain.repository.UserRepository;
import com.victorxavier.product_catalog.domain.service.PasswordService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordService passwordService;

    public AdminUserConfig(RoleRepository roleRepository,
                          UserRepository userRepository,
                          PasswordService passwordService) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Create roles if they don't exist
        Role roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name())
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(Role.Values.ADMIN.name());
                    return roleRepository.save(newRole);
                });

        Role roleBasic = roleRepository.findByName(Role.Values.BASIC.name())
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(Role.Values.BASIC.name());
                    return roleRepository.save(newRole);
                });

        // Create admin user if it doesn't exist
        var userAdmin = userRepository.findByUsername("admin");
        final Role finalRoleAdmin = roleAdmin;

        userAdmin.ifPresentOrElse(
                user -> {
                    System.out.println("Admin user already exists");
                },
                () -> {
                    // Gera salt e hash para a senha do admin
                    String salt = passwordService.generateSalt();
                    String hash = passwordService.hashPassword("123456", salt);
                    
                    var user = new User();
                    user.setUsername("admin");
                    user.setPasswordSalt(salt);
                    user.setPasswordHash(hash);
                    user.setRoles(Set.of(finalRoleAdmin));
                    user.setCreationTimestamp(Instant.now());
                    userRepository.save(user);
                    System.out.println("Admin user created successfully");
                }
        );
    }
}