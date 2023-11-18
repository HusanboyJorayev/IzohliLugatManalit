package com.example.izohlilugatmanalit.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DayWordRequest {
    //private LocalDate date;
    private Integer wordId;
}
