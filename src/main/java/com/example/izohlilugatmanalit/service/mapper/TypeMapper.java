package com.example.izohlilugatmanalit.service.mapper;

import com.example.izohlilugatmanalit.entity.Type;
import com.example.izohlilugatmanalit.request.TypeRequest;
import com.example.izohlilugatmanalit.response.TypeResponse;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring",imports = Collectors.class)
public abstract class TypeMapper {
    @Autowired
    protected WordTypeMapper wordTypeMapper;

    public abstract Type toEntity(TypeRequest request);

    public abstract TypeResponse toDto(Type type);

   @Mapping(target = "wordType",expression = "java(type.getWordType().stream().map(this.wordTypeMapper::toDto).toList())")
    public abstract TypeResponse toDtoWithOtherTables(Type type);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(@MappingTarget Type type, TypeRequest request);

    public void view(TypeResponse response,Type type){
        response.setWordType(type.getWordType().stream().map(this.wordTypeMapper::toDto).toList());
    }
}
