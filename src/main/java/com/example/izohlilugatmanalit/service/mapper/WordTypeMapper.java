package com.example.izohlilugatmanalit.service.mapper;

import com.example.izohlilugatmanalit.entity.WordType;
import com.example.izohlilugatmanalit.request.WordTypeRequest;
import com.example.izohlilugatmanalit.response.WordTypeResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public abstract class WordTypeMapper {
    public abstract WordType toEntity(WordTypeRequest request);

    public abstract WordTypeResponse toDto(WordType type);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(@MappingTarget WordType type, WordTypeRequest request);
}
