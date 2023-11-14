package com.example.izohlilugatmanalit.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordInSentenceRequest {
    private Integer wordId;
    private Integer sentenceId;
    private Integer orders;
}
