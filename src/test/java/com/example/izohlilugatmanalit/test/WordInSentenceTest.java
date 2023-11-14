package com.example.izohlilugatmanalit.test;

import com.example.izohlilugatmanalit.dto.ErrorDto;
import com.example.izohlilugatmanalit.dto.ResponseDto;
import com.example.izohlilugatmanalit.entity.WordInSentence;
import com.example.izohlilugatmanalit.repository.WordInSentenceRepository;
import com.example.izohlilugatmanalit.request.WordInSentenceRequest;
import com.example.izohlilugatmanalit.response.WordInSentenceResponse;
import com.example.izohlilugatmanalit.service.WordInSentenceService;
import com.example.izohlilugatmanalit.service.mapper.WordInSentenceMapper;
import com.example.izohlilugatmanalit.service.validation.WordInSentenceValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class WordInSentenceTest {
    private WordInSentenceMapper wordInSentenceMapper;
    private WordInSentenceValidation wordInSentenceValidation;
    private WordInSentenceRepository wordInSentenceRepository;
    private WordInSentenceService wordInSentenceService;

    @BeforeEach()
    void initObject() {
        this.wordInSentenceMapper = Mockito.mock(WordInSentenceMapper.class);
        this.wordInSentenceRepository = Mockito.mock(WordInSentenceRepository.class);
        this.wordInSentenceValidation = Mockito.mock(WordInSentenceValidation.class);
        this.wordInSentenceService = new WordInSentenceService(wordInSentenceRepository, wordInSentenceMapper, wordInSentenceValidation);
    }

    @Test
    void createValidation() {

        when(this.wordInSentenceValidation.validation(any()))
                .thenReturn(List.of(new ErrorDto("categoery", "validation error")));

        ResponseDto<WordInSentenceResponse> response = this.wordInSentenceService.create(any());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -3);

        verify(this.wordInSentenceValidation, times(1)).validation(any());
    }

    @Test
    void createPositive() {
        WordInSentenceResponse response = WordInSentenceResponse.builder()
                .wordId(1)
                .build();

        WordInSentence wordInSentence = WordInSentence.builder()
                .wordId(1)
                .build();

        when(this.wordInSentenceMapper.toDto(any()))
                .thenReturn(response);

        when(this.wordInSentenceMapper.toEntity(any()))
                .thenReturn(wordInSentence);

        ResponseDto<WordInSentenceResponse> responseDto = this.wordInSentenceService.create(any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.wordInSentenceMapper, times(1)).toDto(any());
        verify(this.wordInSentenceMapper, times(1)).toEntity(any());
        verify(this.wordInSentenceRepository, times(1)).save(any());

    }

    @Test
    void createException() {
        when(this.wordInSentenceMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<WordInSentenceResponse> response = this.wordInSentenceService.create(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.wordInSentenceMapper, times(1)).toEntity(any());
    }

    @Test
    void getPositive() {
        WordInSentenceResponse response = WordInSentenceResponse.builder()
                .wordId(1)
                .build();

        WordInSentence wordInSentence = WordInSentence.builder()
                .wordId(1)
                .build();

        when(this.wordInSentenceMapper.toDto(any()))
                .thenReturn(response);


        when(this.wordInSentenceRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(wordInSentence));

        ResponseDto<WordInSentenceResponse> responseDto = this.wordInSentenceService.get(any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.wordInSentenceMapper, times(1)).toDto(any());
        verify(this.wordInSentenceRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void getNegative() {
        when(this.wordInSentenceRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<WordInSentenceResponse> responseDto = this.wordInSentenceService.get(any());
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.wordInSentenceRepository, times(1)).findByIdAndDeletedAtIsNull(any());

    }

    @Test
    void getException() {
        when(this.wordInSentenceMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<WordInSentenceResponse> response = this.wordInSentenceService.get(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.wordInSentenceRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void updateValidation() {
        WordInSentenceRequest request = new WordInSentenceRequest();
        when(this.wordInSentenceValidation.validation(any()))
                .thenReturn(List.of(new ErrorDto("categoery", "validation error")));

        ResponseDto<WordInSentenceResponse> response = this.wordInSentenceService.update(request, any());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -3);

        verify(this.wordInSentenceValidation, times(1)).validation(any());
    }

    @Test
    void updatePositive() {
        WordInSentenceResponse response = WordInSentenceResponse.builder()
                .wordId(1)
                .build();

        WordInSentence wordInSentence = WordInSentence.builder()
                .wordId(1)
                .build();

        WordInSentenceRequest request = WordInSentenceRequest.builder()
                .wordId(1)
                .build();


        when(this.wordInSentenceMapper.toDto(any()))
                .thenReturn(response);


        when(this.wordInSentenceRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(wordInSentence));

        ResponseDto<WordInSentenceResponse> responseDto = this.wordInSentenceService.update(request, any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.wordInSentenceMapper, times(1)).toDto(any());
        verify(this.wordInSentenceRepository, times(1)).findByIdAndDeletedAtIsNull(any());
        verify(this.wordInSentenceRepository, times(1)).save(any());
    }

    @Test
    void updateNegative() {
        WordInSentenceRequest request = new WordInSentenceRequest();
        when(this.wordInSentenceRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<WordInSentenceResponse> responseDto = this.wordInSentenceService.update(request, any());
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.wordInSentenceRepository, times(1)).findByIdAndDeletedAtIsNull(any());

    }

    @Test
    void updateException() {
        WordInSentenceRequest request = new WordInSentenceRequest();
        when(this.wordInSentenceMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<WordInSentenceResponse> response = this.wordInSentenceService.update(request, any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.wordInSentenceRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void deletePositive() {
        WordInSentenceResponse response = WordInSentenceResponse.builder()
                .wordId(1)
                .build();

        WordInSentence wordInSentence = WordInSentence.builder()
                .wordId(1)
                .build();

        when(this.wordInSentenceMapper.toDto(any()))
                .thenReturn(response);


        when(this.wordInSentenceRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(wordInSentence));

        ResponseDto<WordInSentenceResponse> responseDto = this.wordInSentenceService.delete(any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.wordInSentenceMapper, times(1)).toDto(any());
        verify(this.wordInSentenceRepository, times(1)).findByIdAndDeletedAtIsNull(any());
        verify(this.wordInSentenceRepository, times(1)).save(any());
    }

    @Test
    void deleteNegative() {
        when(this.wordInSentenceRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<WordInSentenceResponse> responseDto = this.wordInSentenceService.delete(any());
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.wordInSentenceRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void deleteException() {
        when(this.wordInSentenceMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<WordInSentenceResponse> response = this.wordInSentenceService.delete(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.wordInSentenceRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void getAllPositive() {
        WordInSentenceResponse response = WordInSentenceResponse.builder()
                .wordId(1)
                .build();

        WordInSentence wordInSentence = WordInSentence.builder()
                .wordId(1)
                .build();

        when(this.wordInSentenceMapper.toDto(any()))
                .thenReturn(response);


        when(this.wordInSentenceRepository.getAllByQuery())
                .thenReturn(List.of(wordInSentence));

        ResponseDto<List<WordInSentenceResponse>> responseDto = this.wordInSentenceService.getAllType();
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.wordInSentenceMapper, times(1)).toDto(any());
        verify(this.wordInSentenceRepository, times(1)).getAllByQuery();
    }

    @Test
    void getAllNegative() {
        when(this.wordInSentenceRepository.getAllByQuery())
                .thenReturn(List.of());

        ResponseDto<List<WordInSentenceResponse>> responseDto = this.wordInSentenceService.getAllType();
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.wordInSentenceRepository, times(1)).getAllByQuery();
    }
}
