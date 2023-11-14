package com.example.izohlilugatmanalit.response;


import com.example.izohlilugatmanalit.entity.DayWord;
import com.example.izohlilugatmanalit.entity.WordInSentence;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordResponse {
    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    private String transcript;
    private Integer categoryId;
    private Integer audioId;
    private int numView;
    private int numLike;
    private int numShare;

    private List<WordTypeResponse> wordType;
    private List<NoteResponse>note;
    private List<WordInSentenceResponse>wordInSentence;
    private List<DayWordResponse>dayWord;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
