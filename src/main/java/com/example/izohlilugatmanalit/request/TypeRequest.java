package com.example.izohlilugatmanalit.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TypeRequest {
    private String name;
    private int orders;
}
