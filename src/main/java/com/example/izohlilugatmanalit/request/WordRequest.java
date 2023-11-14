package com.example.izohlilugatmanalit.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordRequest {
    private String name;
    private String transcript;
    private Integer categoryId;
    private Integer audioId;
    private int numView;
    private int numLike;
    private int numShare;
}
