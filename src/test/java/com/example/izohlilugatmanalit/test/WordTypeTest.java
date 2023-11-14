package com.example.izohlilugatmanalit.test;

import com.example.izohlilugatmanalit.dto.ErrorDto;
import com.example.izohlilugatmanalit.dto.ResponseDto;
import com.example.izohlilugatmanalit.entity.WordType;
import com.example.izohlilugatmanalit.repository.WordTypeRepository;
import com.example.izohlilugatmanalit.request.WordTypeRequest;
import com.example.izohlilugatmanalit.response.WordTypeResponse;
import com.example.izohlilugatmanalit.service.WordTypeService;
import com.example.izohlilugatmanalit.service.mapper.WordTypeMapper;
import com.example.izohlilugatmanalit.service.validation.WordTypeValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class WordTypeTest {
    private WordTypeMapper wordTypeMapper;
    private WordTypeRepository wordTypeRepository;
    private WordTypeValidation wordTypeValidation;
    private WordTypeService wordTypeService;

    @BeforeEach()
    void initObject() {
        this.wordTypeMapper = Mockito.mock(WordTypeMapper.class);
        this.wordTypeRepository = Mockito.mock(WordTypeRepository.class);
        this.wordTypeValidation = Mockito.mock(WordTypeValidation.class);
        this.wordTypeService = new WordTypeService(wordTypeValidation, wordTypeMapper, wordTypeRepository);
    }

    @Test
    void createValidation() {

        when(this.wordTypeValidation.validation(any()))
                .thenReturn(List.of(new ErrorDto("categoery", "validation error")));

        ResponseDto<WordTypeResponse> response = this.wordTypeService.create(any());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -3);

        verify(this.wordTypeValidation, times(1)).validation(any());
    }

    @Test
    void createPositive() {
        WordTypeResponse response = WordTypeResponse.builder()
                .wordId(1)
                .build();

        WordType wordType = WordType.builder()
                .wordId(1)
                .build();

        when(this.wordTypeMapper.toDto(any()))
                .thenReturn(response);

        when(this.wordTypeMapper.toEntity(any()))
                .thenReturn(wordType);

        ResponseDto<WordTypeResponse> responseDto = this.wordTypeService.create(any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.wordTypeMapper, times(1)).toDto(any());
        verify(this.wordTypeMapper, times(1)).toEntity(any());
        verify(this.wordTypeRepository, times(1)).save(any());

    }

    @Test
    void createException() {
        when(this.wordTypeMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<WordTypeResponse> response = this.wordTypeService.create(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.wordTypeMapper, times(1)).toEntity(any());
    }

    @Test
    void getPositive() {
        WordTypeResponse response = WordTypeResponse.builder()
                .wordId(1)
                .build();

        WordType wordType = WordType.builder()
                .wordId(1)
                .build();

        when(this.wordTypeMapper.toDto(any()))
                .thenReturn(response);


        when(this.wordTypeRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(wordType));

        ResponseDto<WordTypeResponse> responseDto = this.wordTypeService.get(any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.wordTypeMapper, times(1)).toDto(any());
        verify(this.wordTypeRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void getNegative() {
        when(this.wordTypeRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<WordTypeResponse> responseDto = this.wordTypeService.get(any());
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.wordTypeRepository, times(1)).findByIdAndDeletedAtIsNull(any());

    }

    @Test
    void getException() {
        when(this.wordTypeMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<WordTypeResponse> response = this.wordTypeService.get(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.wordTypeRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void updateValidation() {
        WordTypeRequest request = new WordTypeRequest();
        when(this.wordTypeValidation.validation(any()))
                .thenReturn(List.of(new ErrorDto("categoery", "validation error")));

        ResponseDto<WordTypeResponse> response = this.wordTypeService.update(request, any());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -3);

        verify(this.wordTypeValidation, times(1)).validation(any());
    }

    @Test
    void updatePositive() {
        WordTypeResponse response = WordTypeResponse.builder()
                .wordId(1)
                .build();

        WordType wordType = WordType.builder()
                .wordId(1)
                .build();

        WordTypeRequest request = WordTypeRequest.builder()
                .wordId(1)
                .build();


        when(this.wordTypeMapper.toDto(any()))
                .thenReturn(response);


        when(this.wordTypeRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(wordType));

        ResponseDto<WordTypeResponse> responseDto = this.wordTypeService.update(request, any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.wordTypeMapper, times(1)).toDto(any());
        verify(this.wordTypeRepository, times(1)).findByIdAndDeletedAtIsNull(any());
        verify(this.wordTypeRepository, times(1)).save(any());
    }

    @Test
    void updateNegative() {
        WordTypeRequest request = new WordTypeRequest();
        when(this.wordTypeRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<WordTypeResponse> responseDto = this.wordTypeService.update(request, any());
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.wordTypeRepository, times(1)).findByIdAndDeletedAtIsNull(any());

    }

    @Test
    void updateException() {
        WordTypeRequest request = new WordTypeRequest();
        when(this.wordTypeMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<WordTypeResponse> response = this.wordTypeService.update(request, any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.wordTypeRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void deletePositive() {
        WordTypeResponse response = WordTypeResponse.builder()
                .wordId(1)
                .build();

        WordType wordType = WordType.builder()
                .wordId(1)
                .build();

        when(this.wordTypeMapper.toDto(any()))
                .thenReturn(response);


        when(this.wordTypeRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(wordType));

        ResponseDto<WordTypeResponse> responseDto = this.wordTypeService.delete(any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.wordTypeMapper, times(1)).toDto(any());
        verify(this.wordTypeRepository, times(1)).findByIdAndDeletedAtIsNull(any());
        verify(this.wordTypeRepository, times(1)).save(any());
    }

    @Test
    void deleteNegative() {
        when(this.wordTypeRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<WordTypeResponse> responseDto = this.wordTypeService.delete(any());
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.wordTypeRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void deleteException() {
        when(this.wordTypeMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<WordTypeResponse> response = this.wordTypeService.delete(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.wordTypeRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void getAllPositive() {
        WordTypeResponse response = WordTypeResponse.builder()
                .wordId(1)
                .build();

        WordType wordType = WordType.builder()
                .wordId(1)
                .build();

        when(this.wordTypeMapper.toDto(any()))
                .thenReturn(response);


        when(this.wordTypeRepository.getAllByQuery())
                .thenReturn(List.of(wordType));

        ResponseDto<List<WordTypeResponse>> responseDto = this.wordTypeService.getAllType();
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.wordTypeMapper, times(1)).toDto(any());
        verify(this.wordTypeRepository, times(1)).getAllByQuery();
    }

    @Test
    void getAllNegative() {
        when(this.wordTypeRepository.getAllByQuery())
                .thenReturn(List.of());

        ResponseDto<List<WordTypeResponse>> responseDto = this.wordTypeService.getAllType();
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.wordTypeRepository, times(1)).getAllByQuery();
    }
}
