package com.example.izohlilugatmanalit.auth;


import com.example.izohlilugatmanalit.user.Role;
import com.example.izohlilugatmanalit.user.User;
import com.example.izohlilugatmanalit.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DefaultAdmin {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @PostConstruct
    public void saveAdmin() {

        userRepository.findByEmail("admin@admin.com").ifPresent(userRepository::delete);

        User admin = User.builder()
                .firstName("admin")
                .lastName("admin")
                .email("admin@admin.com")
                .password(passwordEncoder.encode("admin"))
                .role(Role.ROLE_ADMIN)
                .build();

        this.userRepository.save(admin);

        System.out.println("Admin created");
    }
}
