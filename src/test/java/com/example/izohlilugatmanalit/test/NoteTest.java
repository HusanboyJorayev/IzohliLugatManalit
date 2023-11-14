package com.example.izohlilugatmanalit.test;

import com.example.izohlilugatmanalit.dto.ErrorDto;
import com.example.izohlilugatmanalit.dto.ResponseDto;
import com.example.izohlilugatmanalit.entity.Note;
import com.example.izohlilugatmanalit.repository.NoteRepository;
import com.example.izohlilugatmanalit.request.NoteRequest;
import com.example.izohlilugatmanalit.response.NoteResponse;
import com.example.izohlilugatmanalit.service.NoteService;
import com.example.izohlilugatmanalit.service.mapper.NoteMapper;
import com.example.izohlilugatmanalit.service.validation.NoteValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class NoteTest {
    private NoteMapper noteMapper;
    private NoteValidation noteValidation;
    private NoteRepository noteRepository;
    private NoteService noteService;

    @BeforeEach()
    void initObject() {
        this.noteMapper = Mockito.mock(NoteMapper.class);
        this.noteRepository = Mockito.mock(NoteRepository.class);
        this.noteValidation = Mockito.mock(NoteValidation.class);
        this.noteService = new NoteService(noteValidation,noteRepository,noteMapper);
    }

    @Test
    void createValidation() {

        when(this.noteValidation.validation(any()))
                .thenReturn(List.of(new ErrorDto("categoery", "validation error")));

        ResponseDto<NoteResponse> response = this.noteService.create(any());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -3);

        verify(this.noteValidation, times(1)).validation(any());
    }

    @Test
    void createPositive() {
       NoteResponse response= NoteResponse.builder()
               .title("title")
               .build();

        Note note= Note.builder()
                .title("title")
                .build();

        when(this.noteMapper.toDto(any()))
                .thenReturn(response);

        when(this.noteMapper.toEntity(any()))
                .thenReturn(note);

        ResponseDto<NoteResponse> responseDto = this.noteService.create(any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.noteMapper, times(1)).toDto(any());
        verify(this.noteMapper, times(1)).toEntity(any());
        verify(this.noteRepository, times(1)).save(any());

    }

    @Test
    void createException() {
        when(this.noteMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<NoteResponse> response = this.noteService.create(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.noteMapper, times(1)).toEntity(any());
    }

    @Test
    void getPositive() {
        NoteResponse response= NoteResponse.builder()
                .title("title")
                .build();

        Note note= Note.builder()
                .title("title")
                .build();

        when(this.noteMapper.toDto(any()))
                .thenReturn(response);


        when(this.noteRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(note));

        ResponseDto<NoteResponse> responseDto = this.noteService.get(any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.noteMapper, times(1)).toDto(any());
        verify(this.noteRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void getNegative() {
        when(this.noteRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<NoteResponse> responseDto = this.noteService.get(any());
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.noteRepository, times(1)).findByIdAndDeletedAtIsNull(any());

    }

    @Test
    void getException() {
        when(this.noteMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<NoteResponse> response = this.noteService.get(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.noteRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void updateValidation() {
        NoteRequest request = new NoteRequest();
        when(this.noteValidation.validation(any()))
                .thenReturn(List.of(new ErrorDto("categoery", "validation error")));

        ResponseDto<NoteResponse> response = this.noteService.update(request, any());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -3);

        verify(this.noteValidation, times(1)).validation(any());
    }

    @Test
    void updatePositive() {
        NoteResponse response= NoteResponse.builder()
                .title("title")
                .build();

        Note note= Note.builder()
                .title("title")
                .build();

        NoteRequest request= NoteRequest.builder()
                .title("title")
                .build();

        when(this.noteMapper.toDto(any()))
                .thenReturn(response);


        when(this.noteRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(note));

        ResponseDto<NoteResponse> responseDto = this.noteService.update(request, any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.noteMapper, times(1)).toDto(any());
        verify(this.noteRepository, times(1)).findByIdAndDeletedAtIsNull(any());
        verify(this.noteRepository, times(1)).save(any());
    }

    @Test
    void updateNegative() {
        NoteRequest request = new NoteRequest();
        when(this.noteRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<NoteResponse> responseDto = this.noteService.update(request, any());
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.noteRepository, times(1)).findByIdAndDeletedAtIsNull(any());

    }

    @Test
    void updateException() {
        NoteRequest request = new NoteRequest();
        when(this.noteMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<NoteResponse> response = this.noteService.update(request, any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.noteRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void deletePositive() {
        NoteResponse response= NoteResponse.builder()
                .title("title")
                .build();

        Note note= Note.builder()
                .title("title")
                .build();

        when(this.noteMapper.toDto(any()))
                .thenReturn(response);


        when(this.noteRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(note));

        ResponseDto<NoteResponse> responseDto = this.noteService.delete(any());
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.noteMapper, times(1)).toDto(any());
        verify(this.noteRepository, times(1)).findByIdAndDeletedAtIsNull(any());
        verify(this.noteRepository, times(1)).save(any());
    }

    @Test
    void deleteNegative() {
        when(this.noteRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<NoteResponse> responseDto = this.noteService.delete(any());
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.noteRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void deleteException() {
        when(this.noteMapper.toEntity(any()))
                .thenThrow(new RuntimeException());

        ResponseDto<NoteResponse> response = this.noteService.delete(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.noteRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void getAllPositive() {
        NoteResponse response= NoteResponse.builder()
                .title("title")
                .build();

        Note note= Note.builder()
                .title("title")
                .build();

        when(this.noteMapper.toDto(any()))
                .thenReturn(response);


        when(this.noteRepository.getAllByQuery())
                .thenReturn(List.of(note));

        ResponseDto<List<NoteResponse>> responseDto = this.noteService.getAllType();
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertTrue(responseDto.isSuccess());

        verify(this.noteMapper, times(1)).toDto(any());
        verify(this.noteRepository, times(1)).getAllByQuery();
    }

    @Test
    void getAllNegative() {
        when(this.noteRepository.getAllByQuery())
                .thenReturn(List.of());

        ResponseDto<List<NoteResponse>> responseDto = this.noteService.getAllType();
        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());

        verify(this.noteRepository, times(1)).getAllByQuery();
    }
}
