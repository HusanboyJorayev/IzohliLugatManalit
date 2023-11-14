package com.example.izohlilugatmanalit.response;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AudioResponse {
    private Integer id;
    @NotBlank
    private String path;
    private byte[] data;

    private List<WordResponse> word;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
