package com.example.izohlilugatmanalit.test;

import com.example.izohlilugatmanalit.dto.ErrorDto;
import com.example.izohlilugatmanalit.dto.ResponseDto;
import com.example.izohlilugatmanalit.entity.Category;
import com.example.izohlilugatmanalit.entity.Word;
import com.example.izohlilugatmanalit.repository.WordRepository;
import com.example.izohlilugatmanalit.request.CategoryRequest;
import com.example.izohlilugatmanalit.request.WordRequest;
import com.example.izohlilugatmanalit.response.CategoryResponse;
import com.example.izohlilugatmanalit.response.WordResponse;
import com.example.izohlilugatmanalit.service.WordService;
import com.example.izohlilugatmanalit.service.mapper.WordMapper;
import com.example.izohlilugatmanalit.service.validation.WordValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class WordTest {
    private WordMapper wordMapper;
    private WordValidation wordValidation;
    private WordRepository wordRepository;
    private WordService wordService;

    @BeforeEach()
    void initObject() {
        this.wordMapper = Mockito.mock(WordMapper.class);
        this.wordRepository = Mockito.mock(WordRepository.class);
        this.wordValidation = Mockito.mock(WordValidation.class);
        this.wordService = new WordService(wordMapper, wordRepository, wordValidation);
    }

    @Test
    void createValidation() {

        when(this.wordValidation.validation(any()))
                .thenReturn(List.of(new ErrorDto("categoery", "validation error")));

        ResponseDto<WordResponse> response = this.wordService.create(any());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -3);

        verify(this.wordValidation, times(1)).validation(any());
    }

    @Test
    void createPositive() {
        WordResponse response = WordResponse.builder()
                .audioId(1)
                .build();

        Word word = Word.builder()
                .audioId(1)
                .build();

        when(this.wordMapper.toDto(any()))
                .thenReturn(response);

        when(this.wordMapper.toEntity(any()))
                .thenReturn(word);

        ResponseDto<WordResponse> responseDto = this.wordService.create(any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.wordMapper, times(1)).toDto(any());
        verify(this.wordMapper, times(1)).toEntity(any());
        verify(this.wordRepository, times(1)).save(any());

    }

    @Test
    void createException() {
        when(this.wordMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<WordResponse> response = this.wordService.create(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.wordMapper, times(1)).toEntity(any());
    }

    @Test
    void getPositive() {
        WordResponse response = WordResponse.builder()
                .audioId(1)
                .build();

        Word word = Word.builder()
                .audioId(1)
                .build();

        when(this.wordMapper.toDto(any()))
                .thenReturn(response);


        when(this.wordRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(word));

        ResponseDto<WordResponse> responseDto = this.wordService.get(any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.wordMapper, times(1)).toDto(any());
        verify(this.wordRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void getNegative() {
        when(this.wordRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<WordResponse> responseDto = this.wordService.get(any());
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.wordRepository, times(1)).findByIdAndDeletedAtIsNull(any());

    }

    @Test
    void getException() {
        when(this.wordMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<WordResponse> response = this.wordService.get(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.wordRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void updateValidation() {
        WordRequest request = new WordRequest();
        when(this.wordValidation.validation(any()))
                .thenReturn(List.of(new ErrorDto("categoery", "validation error")));

        ResponseDto<WordResponse> response = this.wordService.update(request, any());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -3);

        verify(this.wordValidation, times(1)).validation(any());
    }

    @Test
    void updatePositive() {
        WordResponse response = WordResponse.builder()
                .audioId(1)
                .build();

        Word word = Word.builder()
                .audioId(1)
                .build();

        WordRequest request = WordRequest.builder()
                .audioId(1)
                .build();

        when(this.wordMapper.toDto(any()))
                .thenReturn(response);


        when(this.wordRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(word));

        ResponseDto<WordResponse> responseDto = this.wordService.update(request, any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.wordMapper, times(1)).toDto(any());
        verify(this.wordRepository, times(1)).findByIdAndDeletedAtIsNull(any());
        verify(this.wordRepository, times(1)).save(any());
    }

    @Test
    void updateNegative() {
        WordRequest request = new WordRequest();
        when(this.wordRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<WordResponse> responseDto = this.wordService.update(request, any());
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.wordRepository, times(1)).findByIdAndDeletedAtIsNull(any());

    }

    @Test
    void updateException() {
        WordRequest request = new WordRequest();
        when(this.wordMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<WordResponse> response = this.wordService.update(request, any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.wordRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void deletePositive() {
        WordResponse response = WordResponse.builder()
                .audioId(1)
                .build();

        Word word = Word.builder()
                .audioId(1)
                .build();

        when(this.wordMapper.toDto(any()))
                .thenReturn(response);


        when(this.wordRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(word));

        ResponseDto<WordResponse> responseDto = this.wordService.delete(any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.wordMapper, times(1)).toDto(any());
        verify(this.wordRepository, times(1)).findByIdAndDeletedAtIsNull(any());
        verify(this.wordRepository, times(1)).save(any());
    }

    @Test
    void deleteNegative() {
        when(this.wordRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<WordResponse> responseDto = this.wordService.delete(any());
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.wordRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void deleteException() {
        when(this.wordMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<WordResponse> response = this.wordService.delete(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.wordRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void getAllPositive() {
        WordResponse response = WordResponse.builder()
                .audioId(1)
                .build();

        Word word = Word.builder()
                .audioId(1)
                .build();

        when(this.wordMapper.toDto(any()))
                .thenReturn(response);


        when(this.wordRepository.getAllByQuery())
                .thenReturn(List.of(word));

        ResponseDto<List<WordResponse>> responseDto = this.wordService.getAllType();
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.wordMapper, times(1)).toDto(any());
        verify(this.wordRepository, times(1)).getAllByQuery();
    }

    @Test
    void getAllNegative() {
        when(this.wordRepository.getAllByQuery())
                .thenReturn(List.of());

        ResponseDto<List<WordResponse>> responseDto = this.wordService.getAllType();
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.wordRepository, times(1)).getAllByQuery();
    }
}
