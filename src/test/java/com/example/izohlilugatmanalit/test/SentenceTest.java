package com.example.izohlilugatmanalit.test;

import com.example.izohlilugatmanalit.dto.ErrorDto;
import com.example.izohlilugatmanalit.dto.ResponseDto;
import com.example.izohlilugatmanalit.entity.Sentence;
import com.example.izohlilugatmanalit.repository.SentenceRepository;
import com.example.izohlilugatmanalit.request.SentenceRequest;
import com.example.izohlilugatmanalit.response.SentenceResponce;
import com.example.izohlilugatmanalit.service.SentenceService;
import com.example.izohlilugatmanalit.service.mapper.SentenceMapper;
import com.example.izohlilugatmanalit.service.validation.SentenceValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SentenceTest {
    private SentenceMapper sentenceMapper;
    private SentenceValidation sentenceValidation;
    private SentenceRepository sentenceRepository;
    private SentenceService sentenceService;

    @BeforeEach()
    void initObject() {
        this.sentenceMapper = Mockito.mock(SentenceMapper.class);
        this.sentenceRepository = Mockito.mock(SentenceRepository.class);
        this.sentenceValidation = Mockito.mock(SentenceValidation.class);
        this.sentenceService = new SentenceService(sentenceRepository, sentenceMapper, sentenceValidation);
    }

    @Test
    void createValidation() {

        when(this.sentenceValidation.validation(any()))
                .thenReturn(List.of(new ErrorDto("categoery", "validation error")));

        ResponseDto<SentenceResponce> response = this.sentenceService.create(any());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -3);

        verify(this.sentenceValidation, times(1)).validation(any());
    }

    @Test
    void createPositive() {
        SentenceResponce response = SentenceResponce.builder()
                .content("contact")
                .build();

        Sentence sentence = Sentence.builder()
                .content("contact")
                .build();

        when(this.sentenceMapper.toDto(any()))
                .thenReturn(response);

        when(this.sentenceMapper.toEntity(any()))
                .thenReturn(sentence);

        ResponseDto<SentenceResponce> responseDto = this.sentenceService.create(any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.sentenceMapper, times(1)).toDto(any());
        verify(this.sentenceMapper, times(1)).toEntity(any());
        verify(this.sentenceRepository, times(1)).save(any());

    }

    @Test
    void createException() {
        when(this.sentenceMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<SentenceResponce> response = this.sentenceService.create(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.sentenceMapper, times(1)).toEntity(any());
    }

    @Test
    void getPositive() {
        SentenceResponce response = SentenceResponce.builder()
                .content("contact")
                .build();

        Sentence sentence = Sentence.builder()
                .content("contact")
                .build();

        when(this.sentenceMapper.toDto(any()))
                .thenReturn(response);


        when(this.sentenceRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(sentence));

        ResponseDto<SentenceResponce> responseDto = this.sentenceService.get(any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.sentenceMapper, times(1)).toDto(any());
        verify(this.sentenceRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void getNegative() {
        when(this.sentenceRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<SentenceResponce> responseDto = this.sentenceService.get(any());
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.sentenceRepository, times(1)).findByIdAndDeletedAtIsNull(any());

    }

    @Test
    void getException() {
        when(this.sentenceMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<SentenceResponce> response = this.sentenceService.get(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.sentenceRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void updateValidation() {
        SentenceRequest request = new SentenceRequest();
        when(this.sentenceValidation.validation(any()))
                .thenReturn(List.of(new ErrorDto("categoery", "validation error")));

        ResponseDto<SentenceResponce> response = this.sentenceService.update(request, any());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -3);

        verify(this.sentenceValidation, times(1)).validation(any());
    }

    @Test
    void updatePositive() {
        SentenceResponce response = SentenceResponce.builder()
                .content("contact")
                .build();

        Sentence sentence = Sentence.builder()
                .content("contact")
                .build();

        SentenceRequest request = SentenceRequest.builder()
                .content("contact")
                .build();

        when(this.sentenceMapper.toDto(any()))
                .thenReturn(response);


        when(this.sentenceRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(sentence));

        ResponseDto<SentenceResponce> responseDto = this.sentenceService.update(request, any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.sentenceMapper, times(1)).toDto(any());
        verify(this.sentenceRepository, times(1)).findByIdAndDeletedAtIsNull(any());
        verify(this.sentenceRepository, times(1)).save(any());
    }

    @Test
    void updateNegative() {
        SentenceRequest request = new SentenceRequest();
        when(this.sentenceRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<SentenceResponce> responseDto = this.sentenceService.update(request, any());
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.sentenceRepository, times(1)).findByIdAndDeletedAtIsNull(any());

    }

    @Test
    void updateException() {
        SentenceRequest request = new SentenceRequest();
        when(this.sentenceMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<SentenceResponce> response = this.sentenceService.update(request, any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.sentenceRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void deletePositive() {
        SentenceResponce response = SentenceResponce.builder()
                .content("contact")
                .build();

        Sentence sentence = Sentence.builder()
                .content("contact")
                .build();

        when(this.sentenceMapper.toDto(any()))
                .thenReturn(response);


        when(this.sentenceRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(sentence));

        ResponseDto<SentenceResponce> responseDto = this.sentenceService.delete(any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.sentenceMapper, times(1)).toDto(any());
        verify(this.sentenceRepository, times(1)).findByIdAndDeletedAtIsNull(any());
        verify(this.sentenceRepository, times(1)).save(any());
    }

    @Test
    void deleteNegative() {
        when(this.sentenceRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<SentenceResponce> responseDto = this.sentenceService.delete(any());
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.sentenceRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void deleteException() {
        when(this.sentenceMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<SentenceResponce> response = this.sentenceService.delete(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.sentenceRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void getAllPositive() {
        SentenceResponce response = SentenceResponce.builder()
                .content("contact")
                .build();

        Sentence sentence = Sentence.builder()
                .content("contact")
                .build();

        when(this.sentenceMapper.toDto(any()))
                .thenReturn(response);


        when(this.sentenceRepository.getAllByQuery())
                .thenReturn(List.of(sentence));

        ResponseDto<List<SentenceResponce>> responseDto = this.sentenceService.getAllType();
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.sentenceMapper, times(1)).toDto(any());
        verify(this.sentenceRepository, times(1)).getAllByQuery();
    }

    @Test
    void getAllNegative() {
        when(this.sentenceRepository.getAllByQuery())
                .thenReturn(List.of());

        ResponseDto<List<SentenceResponce>> responseDto = this.sentenceService.getAllType();
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.sentenceRepository, times(1)).getAllByQuery();
    }
}
