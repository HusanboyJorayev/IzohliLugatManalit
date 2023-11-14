package com.example.izohlilugatmanalit.response;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteResponse {
    private Integer id;
    private Integer wordId;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String source;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
