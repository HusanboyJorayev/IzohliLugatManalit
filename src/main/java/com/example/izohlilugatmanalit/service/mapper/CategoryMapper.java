package com.example.izohlilugatmanalit.service.mapper;

import com.example.izohlilugatmanalit.entity.Category;
import com.example.izohlilugatmanalit.request.CategoryRequest;
import com.example.izohlilugatmanalit.response.CategoryResponse;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring",imports = Collectors.class)
public abstract class CategoryMapper {
    @Autowired
    protected WordMapper wordMapper;
    public abstract Category toEntity(CategoryRequest request);

    public abstract CategoryResponse toDto(Category category);

    @Mapping(target = "word",expression = "java(category.getWord().stream().map(this.wordMapper::toDto).toList())")
    public abstract CategoryResponse toDtoWithWord(Category category);

   @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(@MappingTarget Category category,CategoryRequest request);

   public void view(CategoryResponse response,Category category){
       response.setWord(category.getWord().stream().map(this.wordMapper::toDto).toList());
   }
}
