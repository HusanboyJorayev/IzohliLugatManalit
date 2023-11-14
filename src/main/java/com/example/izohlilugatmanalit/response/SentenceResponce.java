package com.example.izohlilugatmanalit.response;

import com.example.izohlilugatmanalit.entity.WordInSentence;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SentenceResponce {
    private Integer id;
    @NotBlank
    private String content;

    private List<WordInSentenceResponse> wordInSentence;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
