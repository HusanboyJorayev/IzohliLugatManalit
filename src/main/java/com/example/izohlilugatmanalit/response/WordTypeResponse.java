package com.example.izohlilugatmanalit.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordTypeResponse {
    private Integer id;
    private Integer typeId;
    private Integer wordId;
    private Integer wordIds;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
