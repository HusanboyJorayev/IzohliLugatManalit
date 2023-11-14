package com.example.izohlilugatmanalit.service.mapper;

import com.example.izohlilugatmanalit.entity.Sentence;
import com.example.izohlilugatmanalit.request.SentenceRequest;
import com.example.izohlilugatmanalit.response.SentenceResponce;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring",imports = Collectors.class)
public abstract class SentenceMapper {

    @Autowired
    protected WordInSentenceMapper wordInSentenceMapper;
    public abstract Sentence toEntity(SentenceRequest request);

    public abstract SentenceResponce toDto(Sentence sentence);

    @Mapping(target = "wordInSentence",expression = "java(sentence.getWordInSentence().stream().map(this.wordInSentenceMapper::toDto).toList())")
    public abstract SentenceResponce toDtoWithWordInSentence(Sentence sentence);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(@MappingTarget Sentence sentence, SentenceRequest request);

    public void view(SentenceResponce responce,Sentence sentence){
        responce.setWordInSentence(sentence.getWordInSentence().stream().map(this.wordInSentenceMapper::toDto).toList());
    }
}
