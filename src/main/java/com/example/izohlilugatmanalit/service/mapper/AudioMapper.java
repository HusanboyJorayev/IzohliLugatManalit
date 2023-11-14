package com.example.izohlilugatmanalit.service.mapper;

import com.example.izohlilugatmanalit.entity.Audio;
import com.example.izohlilugatmanalit.response.AudioResponse;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring",imports = Collectors.class)
public abstract class AudioMapper {

    @Autowired
    protected WordMapper wordMapper;


    public abstract AudioResponse toDto(Audio audio);

    @Mapping(target = "word",expression = "java(audio.getWord().stream().map(this.wordMapper::toDto).toList())")
    public abstract AudioResponse toDtoWithWord(Audio audio);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(@MappingTarget Audio audio, AudioResponse request);

    public void view(AudioResponse response,Audio audio){
        response.setWord(audio.getWord().stream().map(this.wordMapper::toDto).toList());
    }
}
