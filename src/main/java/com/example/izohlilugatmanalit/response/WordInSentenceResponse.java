package com.example.izohlilugatmanalit.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordInSentenceResponse {
    private Integer id;
    @NotNull
    private Integer wordId;
    @NotNull
    private Integer sentenceId;
    private Integer orders;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
