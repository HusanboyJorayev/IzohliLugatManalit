package com.example.izohlilugatmanalit.swagger;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public GroupedOpenApi audioApi() {
        return GroupedOpenApi.builder()
                .pathsToMatch("/audio/**")
                .group("Audio")
                .build();
    }

    @Bean
    public GroupedOpenApi categoryApi() {
        return GroupedOpenApi.builder()
                .pathsToMatch("/category/**")
                .group("Category")
                .build();
    }

    @Bean
    public GroupedOpenApi dayWordApi() {
        return GroupedOpenApi.builder()
                .pathsToMatch("/dayWord/**")
                .group("DayWord")
                .build();
    }

    @Bean
    public GroupedOpenApi noteApi() {
        return GroupedOpenApi.builder()
                .pathsToMatch("/note/**")
                .group("Note")
                .build();
    }

    @Bean
    public GroupedOpenApi sentenceApi() {
        return GroupedOpenApi.builder()
                .pathsToMatch("/sentence/**")
                .group("Sentence")
                .build();
    }

    @Bean
    public GroupedOpenApi typeApi() {
        return GroupedOpenApi.builder()
                .pathsToMatch("/type/**")
                .group("Type")
                .build();
    }

    @Bean
    public GroupedOpenApi wordApi() {
        return GroupedOpenApi.builder()
                .pathsToMatch("/word/**")
                .group("Word")
                .build();
    }

    @Bean
    public GroupedOpenApi wordInSentenceApi() {
        return GroupedOpenApi.builder()
                .pathsToMatch("/wordInSentence/**")
                .group("WordInSentence")
                .build();
    }

    @Bean
    public GroupedOpenApi wordTypeApi() {
        return GroupedOpenApi.builder()
                .pathsToMatch("/wordType/**")
                .group("WordType")
                .build();
    }
}
