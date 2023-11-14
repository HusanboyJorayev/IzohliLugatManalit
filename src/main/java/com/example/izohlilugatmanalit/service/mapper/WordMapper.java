package com.example.izohlilugatmanalit.service.mapper;

import com.example.izohlilugatmanalit.entity.Word;
import com.example.izohlilugatmanalit.request.WordRequest;
import com.example.izohlilugatmanalit.response.WordResponse;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring",imports = Collectors.class)
public abstract class WordMapper {
    @Autowired
    protected WordTypeMapper wordTypeMapper;
    @Autowired
    protected NoteMapper noteMapper;
    @Autowired
    protected WordInSentenceMapper wordInSentenceMapper;
    @Autowired
    protected DayWordMapper dayWordMapper;

    public abstract Word toEntity(WordRequest request);

    public abstract WordResponse toDto(Word word);

    @Mapping(target = "wordType",expression = "java(word.getWordType().stream().map(this.wordTypeMapper::toDto).toList())")
    @Mapping(target = "note",expression = "java(word.getNote().stream().map(this.noteMapper::toDto).toList())")
    @Mapping(target = "wordInSentence",expression = "java(word.getWordInSentence().stream().map(this.wordInSentenceMapper::toDto).toList())")
    @Mapping(target = "dayWord",expression = "java(word.getDayWord().stream().map(this.dayWordMapper::toDto).toList())")
    public abstract WordResponse toDtoWithOtherTables(Word word);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(@MappingTarget Word word, WordRequest request);

    public void view(WordResponse wordResponse,Word word){
        wordResponse.setDayWord(word.getDayWord().stream().map(this.dayWordMapper::toDto).toList());
    }
}
