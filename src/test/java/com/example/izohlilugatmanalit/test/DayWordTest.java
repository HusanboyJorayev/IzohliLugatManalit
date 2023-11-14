package com.example.izohlilugatmanalit.test;

import com.example.izohlilugatmanalit.dto.ErrorDto;
import com.example.izohlilugatmanalit.dto.ResponseDto;
import com.example.izohlilugatmanalit.entity.DayWord;
import com.example.izohlilugatmanalit.repository.DayWordRepository;
import com.example.izohlilugatmanalit.request.DayWordRequest;
import com.example.izohlilugatmanalit.response.DayWordResponse;
import com.example.izohlilugatmanalit.service.DayWordService;
import com.example.izohlilugatmanalit.service.mapper.DayWordMapper;
import com.example.izohlilugatmanalit.service.validation.DayWordValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DayWordTest {
    private DayWordMapper dayWordMapper;
    private DayWordValidation dayWordValidation;
    private DayWordRepository dayWordRepository;
    private DayWordService dayWordService;

    @BeforeEach()
    void initObject() {
        this.dayWordMapper = Mockito.mock(DayWordMapper.class);
        this.dayWordRepository = Mockito.mock(DayWordRepository.class);
        this.dayWordValidation = Mockito.mock(DayWordValidation.class);
        this.dayWordService = new DayWordService(dayWordRepository, dayWordMapper, dayWordValidation);
    }

    @Test
    void createValidation() {

        when(this.dayWordValidation.validation(any()))
                .thenReturn(List.of(new ErrorDto("categoery", "validation error")));

        ResponseDto<DayWordResponse> response = this.dayWordService.create(any());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -3);

        verify(this.dayWordValidation, times(1)).validation(any());
    }

    @Test
    void createPositive() {
        DayWordResponse response = DayWordResponse.builder()
                .wordId(1)
                .build();

        DayWord dayWord = DayWord.builder()
                .wordId(1)
                .build();

        when(this.dayWordMapper.toDto(any()))
                .thenReturn(response);

        when(this.dayWordMapper.toEntity(any()))
                .thenReturn(dayWord);

        ResponseDto<DayWordResponse> responseDto = this.dayWordService.create(any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.dayWordMapper, times(1)).toDto(any());
        verify(this.dayWordMapper, times(1)).toEntity(any());
        verify(this.dayWordRepository, times(1)).save(any());

    }

    @Test
    void createException() {
        when(this.dayWordMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<DayWordResponse> response = this.dayWordService.create(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.dayWordMapper, times(1)).toEntity(any());
    }

    @Test
    void getPositive() {
        DayWordResponse response = DayWordResponse.builder()
                .wordId(1)
                .build();

        DayWord dayWord = DayWord.builder()
                .wordId(1)
                .build();

        when(this.dayWordMapper.toDto(any()))
                .thenReturn(response);


        when(this.dayWordRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(dayWord));

        ResponseDto<DayWordResponse> responseDto = this.dayWordService.get(any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.dayWordMapper, times(1)).toDto(any());
        verify(this.dayWordRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void getNegative() {
        when(this.dayWordRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<DayWordResponse> responseDto = this.dayWordService.get(any());
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.dayWordRepository, times(1)).findByIdAndDeletedAtIsNull(any());

    }

    @Test
    void getException() {
        when(this.dayWordMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<DayWordResponse> response = this.dayWordService.get(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.dayWordRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void updateValidation() {
        DayWordRequest request = new DayWordRequest();
        when(this.dayWordValidation.validation(any()))
                .thenReturn(List.of(new ErrorDto("categoery", "validation error")));

        ResponseDto<DayWordResponse> response = this.dayWordService.update(request, any());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -3);

        verify(this.dayWordValidation, times(1)).validation(any());
    }

    @Test
    void updatePositive() {
        DayWordResponse response = DayWordResponse.builder()
                .wordId(1)
                .build();

        DayWord dayWord = DayWord.builder()
                .wordId(1)
                .build();

        DayWordRequest request = DayWordRequest.builder()
                .wordId(1)
                .build();


        when(this.dayWordMapper.toDto(any()))
                .thenReturn(response);


        when(this.dayWordRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(dayWord));

        ResponseDto<DayWordResponse> responseDto = this.dayWordService.update(request, any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.dayWordMapper, times(1)).toDto(any());
        verify(this.dayWordRepository, times(1)).findByIdAndDeletedAtIsNull(any());
        verify(this.dayWordRepository, times(1)).save(any());
    }

    @Test
    void updateNegative() {
        DayWordRequest request = new DayWordRequest();
        when(this.dayWordRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<DayWordResponse> responseDto = this.dayWordService.update(request, any());
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.dayWordRepository, times(1)).findByIdAndDeletedAtIsNull(any());

    }

    @Test
    void updateException() {
        DayWordRequest request = new DayWordRequest();
        when(this.dayWordMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<DayWordResponse> response = this.dayWordService.update(request, any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.dayWordRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void deletePositive() {
        DayWordResponse response = DayWordResponse.builder()
                .wordId(1)
                .build();

        DayWord dayWord = DayWord.builder()
                .wordId(1)
                .build();

        when(this.dayWordMapper.toDto(any()))
                .thenReturn(response);


        when(this.dayWordRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(dayWord));

        ResponseDto<DayWordResponse> responseDto = this.dayWordService.delete(any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.dayWordMapper, times(1)).toDto(any());
        verify(this.dayWordRepository, times(1)).findByIdAndDeletedAtIsNull(any());
        verify(this.dayWordRepository, times(1)).save(any());
    }

    @Test
    void deleteNegative() {
        when(this.dayWordRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<DayWordResponse> responseDto = this.dayWordService.delete(any());
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.dayWordRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void deleteException() {
        when(this.dayWordMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<DayWordResponse> response = this.dayWordService.delete(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.dayWordRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void getAllPositive() {
        DayWordResponse response = DayWordResponse.builder()
                .wordId(1)
                .build();

        DayWord dayWord = DayWord.builder()
                .wordId(1)
                .build();

        when(this.dayWordMapper.toDto(any()))
                .thenReturn(response);


        when(this.dayWordRepository.getAllByQuery())
                .thenReturn(List.of(dayWord));

        ResponseDto<List<DayWordResponse>> responseDto = this.dayWordService.getAllType();
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.dayWordMapper, times(1)).toDto(any());
        verify(this.dayWordRepository, times(1)).getAllByQuery();
    }

    @Test
    void getAllNegative() {
        when(this.dayWordRepository.getAllByQuery())
                .thenReturn(List.of());

        ResponseDto<List<DayWordResponse>> responseDto = this.dayWordService.getAllType();
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.dayWordRepository, times(1)).getAllByQuery();
    }
}
