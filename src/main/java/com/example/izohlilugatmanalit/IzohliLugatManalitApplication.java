package com.example.izohlilugatmanalit;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        tags = @Tag(name = "Izohli lug`at project in manalit")
)
public class IzohliLugatManalitApplication {

    public static void main(String[] args) {
        SpringApplication.run(IzohliLugatManalitApplication.class, args);

    }

}
