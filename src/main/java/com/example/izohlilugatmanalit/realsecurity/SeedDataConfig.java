package com.example.izohlilugatmanalit.realsecurity;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Configuration
@Slf4j
@RequiredArgsConstructor
public class SeedDataConfig {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /*@PostConstruct
    public void saveUser() {

        User admin = User.builder()
                .firstName("admin")
                .lastName("admin")
                .password(passwordEncoder.encode("admin"))
                .email("admin@admin.com")
                .role(Role.ROLE_ADMIN)
                .build();

        if (userRepository.findByEmail(admin.getEmail()).isEmpty())
            userRepository.save(admin);
        else {
            userRepository.deleteAll();
            userRepository.save(admin);
        }
        System.out.println("Admin created");
    }*/


    @PostConstruct
    private void addAuthorityAndUser() {

        this.userRepository.findByEmail("admin")
                .ifPresent(this.userRepository::delete);


        User user = this.userRepository.save(
                User.builder()
                        .firstName("admin")
                        .lastName("admin")
                        .email("admin@admin.com")
                        .createdAt(LocalDateTime.now())
                        .password(passwordEncoder.encode("admin"))
                        .role(Role.ROLE_ADMIN)
                        .build()
        );


        log.info("User and Authority added username {}, authority {}", user.getUsername(), user.getRole());
    }

}

