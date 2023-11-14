package com.example.izohlilugatmanalit.test;

import com.example.izohlilugatmanalit.dto.ErrorDto;
import com.example.izohlilugatmanalit.dto.ResponseDto;
import com.example.izohlilugatmanalit.entity.Type;
import com.example.izohlilugatmanalit.repository.TypeRepository;
import com.example.izohlilugatmanalit.request.TypeRequest;
import com.example.izohlilugatmanalit.response.TypeResponse;
import com.example.izohlilugatmanalit.service.TypeService;
import com.example.izohlilugatmanalit.service.mapper.TypeMapper;
import com.example.izohlilugatmanalit.service.validation.TypeValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TypeTest {
    private TypeMapper typeMapper;
    private TypeValidation typeValidation;
    private TypeRepository typeRepository;
    private TypeService typeService;

    @BeforeEach()
    void initObject() {
        this.typeMapper = Mockito.mock(TypeMapper.class);
        this.typeRepository = Mockito.mock(TypeRepository.class);
        this.typeValidation = Mockito.mock(TypeValidation.class);
        this.typeService = new TypeService(typeMapper, typeRepository, typeValidation);
    }

    @Test
    void createValidation() {

        when(this.typeValidation.validation(any()))
                .thenReturn(List.of(new ErrorDto("categoery", "validation error")));

        ResponseDto<TypeResponse> response = this.typeService.create(any());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -3);

        verify(this.typeValidation, times(1)).validation(any());
    }

    @Test
    void createPositive() {
        TypeResponse response = TypeResponse.builder()
                .name("type")
                .build();

        Type type = Type.builder()
                .name("type")
                .build();

        when(this.typeMapper.toDto(any()))
                .thenReturn(response);

        when(this.typeMapper.toEntity(any()))
                .thenReturn(type);

        ResponseDto<TypeResponse> responseDto = this.typeService.create(any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.typeMapper, times(1)).toDto(any());
        verify(this.typeMapper, times(1)).toEntity(any());
        verify(this.typeRepository, times(1)).save(any());

    }

    @Test
    void createException() {
        when(this.typeMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<TypeResponse> response = this.typeService.create(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.typeMapper, times(1)).toEntity(any());
    }

    @Test
    void getPositive() {
        TypeResponse response = TypeResponse.builder()
                .name("type")
                .build();

        Type type = Type.builder()
                .name("type")
                .build();

        when(this.typeMapper.toDto(any()))
                .thenReturn(response);


        when(this.typeRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(type));

        ResponseDto<TypeResponse> responseDto = this.typeService.get(any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.typeMapper, times(1)).toDto(any());
        verify(this.typeRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void getNegative() {
        when(this.typeRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<TypeResponse> responseDto = this.typeService.get(any());
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.typeRepository, times(1)).findByIdAndDeletedAtIsNull(any());

    }

    @Test
    void getException() {
        when(this.typeMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<TypeResponse> response = this.typeService.get(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.typeRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void updateValidation() {
        TypeRequest request = new TypeRequest();
        when(this.typeValidation.validation(any()))
                .thenReturn(List.of(new ErrorDto("categoery", "validation error")));

        ResponseDto<TypeResponse> response = this.typeService.update(request, any());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -3);

        verify(this.typeValidation, times(1)).validation(any());
    }

    @Test
    void updatePositive() {
        TypeResponse response = TypeResponse.builder()
                .name("type")
                .build();

        Type type = Type.builder()
                .name("type")
                .build();

        TypeRequest request = TypeRequest.builder()
                .name("type")
                .build();

        when(this.typeMapper.toDto(any()))
                .thenReturn(response);


        when(this.typeRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(type));

        ResponseDto<TypeResponse> responseDto = this.typeService.update(request, any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.typeMapper, times(1)).toDto(any());
        verify(this.typeRepository, times(1)).findByIdAndDeletedAtIsNull(any());
        verify(this.typeRepository, times(1)).save(any());
    }

    @Test
    void updateNegative() {
        TypeRequest request = new TypeRequest();
        when(this.typeRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<TypeResponse> responseDto = this.typeService.update(request, any());
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.typeRepository, times(1)).findByIdAndDeletedAtIsNull(any());

    }

    @Test
    void updateException() {
        TypeRequest request = new TypeRequest();
        when(this.typeMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<TypeResponse> response = this.typeService.update(request, any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.typeRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void deletePositive() {
        TypeResponse response = TypeResponse.builder()
                .name("type")
                .build();

        Type type = Type.builder()
                .name("type")
                .build();

        when(this.typeMapper.toDto(any()))
                .thenReturn(response);


        when(this.typeRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(type));

        ResponseDto<TypeResponse> responseDto = this.typeService.delete(any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.typeMapper, times(1)).toDto(any());
        verify(this.typeRepository, times(1)).findByIdAndDeletedAtIsNull(any());
        verify(this.typeRepository, times(1)).save(any());
    }

    @Test
    void deleteNegative() {
        when(this.typeRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<TypeResponse> responseDto = this.typeService.delete(any());
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.typeRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void deleteException() {
        when(this.typeMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<TypeResponse> response = this.typeService.delete(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.typeRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void getAllPositive() {
        TypeResponse response = TypeResponse.builder()
                .name("type")
                .build();

        Type type = Type.builder()
                .name("type")
                .build();

        when(this.typeMapper.toDto(any()))
                .thenReturn(response);


        when(this.typeRepository.getAllByQuery())
                .thenReturn(List.of(type));

        ResponseDto<List<TypeResponse>> responseDto = this.typeService.getAllType();
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.typeMapper, times(1)).toDto(any());
        verify(this.typeRepository, times(1)).getAllByQuery();
    }

    @Test
    void getAllNegative() {
        when(this.typeRepository.getAllByQuery())
                .thenReturn(List.of());

        ResponseDto<List<TypeResponse>> responseDto = this.typeService.getAllType();
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.typeRepository, times(1)).getAllByQuery();
    }
}
