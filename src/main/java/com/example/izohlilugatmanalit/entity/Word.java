package com.example.izohlilugatmanalit.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "word")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String transcript;
    private Integer categoryId;
    private Integer audioId;
    private int numView;
    private int numLike;
    private int numShare;

    @OneToMany(mappedBy = "wordId",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<WordType>wordType;

    @OneToMany(mappedBy = "wordId",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Note>note;

    @OneToMany(mappedBy = "wordId",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<WordInSentence>wordInSentence;

    @OneToMany(mappedBy = "wordId",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<DayWord>dayWord;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
