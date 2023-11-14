package com.example.izohlilugatmanalit.test;

import com.example.izohlilugatmanalit.dto.ErrorDto;
import com.example.izohlilugatmanalit.dto.ResponseDto;
import com.example.izohlilugatmanalit.entity.Category;
import com.example.izohlilugatmanalit.repository.CategoryRepository;
import com.example.izohlilugatmanalit.request.CategoryRequest;
import com.example.izohlilugatmanalit.response.CategoryResponse;
import com.example.izohlilugatmanalit.service.CategoryService;
import com.example.izohlilugatmanalit.service.mapper.CategoryMapper;
import com.example.izohlilugatmanalit.service.validation.CategoryValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CategoryTest {
    private CategoryValidation categoryValidation;
    private CategoryMapper categoryMapper;
    private CategoryRepository categoryRepository;
    private CategoryService categoryService;

    @BeforeEach
    public void initObject() {
        this.categoryMapper = Mockito.mock(CategoryMapper.class);
        this.categoryRepository = Mockito.mock(CategoryRepository.class);
        this.categoryValidation = Mockito.mock(CategoryValidation.class);
        this.categoryService = new CategoryService(categoryValidation, categoryRepository, categoryMapper);
    }

    @Test
    void createValidation() {

        when(this.categoryValidation.validation(any()))
                .thenReturn(List.of(new ErrorDto("categoery", "validation error")));

        ResponseDto<CategoryResponse> response = this.categoryService.create(any());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -3);

        verify(this.categoryValidation, times(1)).validation(any());
    }

    @Test
    void createPositive() {
        CategoryResponse response = CategoryResponse.builder()
                .name("apple")
                .description("description")
                .build();

        Category category = Category.builder()
                .description("description")
                .name("name")
                .build();

        when(this.categoryMapper.toDto(any()))
                .thenReturn(response);

        when(this.categoryMapper.toEntity(any()))
                .thenReturn(category);

        ResponseDto<CategoryResponse> responseDto = this.categoryService.create(any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.categoryMapper, times(1)).toDto(any());
        verify(this.categoryMapper, times(1)).toEntity(any());
        verify(this.categoryRepository, times(1)).save(any());

    }

    @Test
    void createException() {
        when(this.categoryMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<CategoryResponse> response = this.categoryService.create(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.categoryMapper, times(1)).toEntity(any());
    }

    @Test
    void getPositive() {
        CategoryResponse response = CategoryResponse.builder()
                .name("apple")
                .description("description")
                .build();

        Category category = Category.builder()
                .description("description")
                .name("name")
                .build();

        when(this.categoryMapper.toDto(any()))
                .thenReturn(response);


        when(this.categoryRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(category));

        ResponseDto<CategoryResponse> responseDto = this.categoryService.get(any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.categoryMapper, times(1)).toDto(any());
        verify(this.categoryRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void getNegative() {
        when(this.categoryRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<CategoryResponse> responseDto = this.categoryService.get(any());
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.categoryRepository, times(1)).findByIdAndDeletedAtIsNull(any());

    }

    @Test
    void getException() {
        when(this.categoryMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<CategoryResponse> response = this.categoryService.get(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.categoryRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void updateValidation() {
        CategoryRequest request = new CategoryRequest();
        when(this.categoryValidation.validation(any()))
                .thenReturn(List.of(new ErrorDto("categoery", "validation error")));

        ResponseDto<CategoryResponse> response = this.categoryService.update(request, any());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -3);

        verify(this.categoryValidation, times(1)).validation(any());
    }

    @Test
    void updatePositive() {
        CategoryResponse response = CategoryResponse.builder()
                .name("apple")
                .description("description")
                .build();

        CategoryRequest request = CategoryRequest.builder()
                .name("apple")
                .description("description")
                .build();

        Category category = Category.builder()
                .description("description")
                .name("name")
                .build();

        when(this.categoryMapper.toDto(any()))
                .thenReturn(response);


        when(this.categoryRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(category));

        ResponseDto<CategoryResponse> responseDto = this.categoryService.update(request, any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.categoryMapper, times(1)).toDto(any());
        verify(this.categoryRepository, times(1)).findByIdAndDeletedAtIsNull(any());
        verify(this.categoryRepository, times(1)).save(any());
    }

    @Test
    void updateNegative() {
        CategoryRequest request = new CategoryRequest();
        when(this.categoryRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<CategoryResponse> responseDto = this.categoryService.update(request, any());
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.categoryRepository, times(1)).findByIdAndDeletedAtIsNull(any());

    }

    @Test
    void updateException() {
        CategoryRequest request = new CategoryRequest();
        when(this.categoryMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<CategoryResponse> response = this.categoryService.update(request, any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.categoryRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void deletePositive() {
        CategoryResponse response = CategoryResponse.builder()
                .name("apple")
                .description("description")
                .build();

        Category category = Category.builder()
                .description("description")
                .name("name")
                .build();

        when(this.categoryMapper.toDto(any()))
                .thenReturn(response);


        when(this.categoryRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(category));

        ResponseDto<CategoryResponse> responseDto = this.categoryService.delete(any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.categoryMapper, times(1)).toDto(any());
        verify(this.categoryRepository, times(1)).findByIdAndDeletedAtIsNull(any());
        verify(this.categoryRepository, times(1)).save(any());
    }

    @Test
    void deleteNegative() {
        when(this.categoryRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<CategoryResponse> responseDto = this.categoryService.delete(any());
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.categoryRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void deleteException() {
        when(this.categoryMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<CategoryResponse> response = this.categoryService.delete(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.categoryRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void getAllPositive() {
        CategoryResponse response = CategoryResponse.builder()
                .name("apple")
                .description("description")
                .build();

        Category category = Category.builder()
                .description("description")
                .name("name")
                .build();

        when(this.categoryMapper.toDto(any()))
                .thenReturn(response);


        when(this.categoryRepository.getAllByQuery())
                .thenReturn(List.of(category));

        ResponseDto<List<CategoryResponse>> responseDto = this.categoryService.getAllType();
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.categoryMapper, times(1)).toDto(any());
        verify(this.categoryRepository, times(1)).getAllByQuery();
    }

    @Test
    void getAllNegative() {
        when(this.categoryRepository.getAllByQuery())
                .thenReturn(List.of());

        ResponseDto<List<CategoryResponse>> responseDto = this.categoryService.getAllType();
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.categoryRepository, times(1)).getAllByQuery();
    }
}
