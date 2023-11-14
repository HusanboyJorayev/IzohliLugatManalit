package com.example.izohlilugatmanalit.service;

import com.example.izohlilugatmanalit.dto.ErrorDto;
import com.example.izohlilugatmanalit.dto.ResponseDto;
import com.example.izohlilugatmanalit.entity.Note;
import com.example.izohlilugatmanalit.entity.Type;
import com.example.izohlilugatmanalit.repository.NoteRepository;
import com.example.izohlilugatmanalit.request.NoteRequest;
import com.example.izohlilugatmanalit.request.TypeRequest;
import com.example.izohlilugatmanalit.response.NoteResponse;
import com.example.izohlilugatmanalit.response.TypeResponse;
import com.example.izohlilugatmanalit.service.mapper.NoteMapper;
import com.example.izohlilugatmanalit.service.validation.NoteValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteValidation noteValidation;
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    public ResponseDto<NoteResponse> create(NoteRequest request) {
        List<ErrorDto> errors = this.noteValidation.validation(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<NoteResponse>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }
        try {
            Note note = this.noteMapper.toEntity(request);
            note.setCreatedAt(LocalDateTime.now());
            this.noteRepository.save(note);

            return ResponseDto.<NoteResponse>builder()
                    .success(true)
                    .message("Ok")
                    .data(this.noteMapper.toDto(note))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<NoteResponse>builder()
                    .code(-1)
                    .message("Type while saving error")
                    .build();
        }
    }

    public ResponseDto<NoteResponse> get(Integer id) {
        try {
            return this.noteRepository.findByIdAndDeletedAtIsNull(id)
                    .map(type -> ResponseDto.<NoteResponse>builder()
                            .success(true)
                            .message("Ok")
                            .data(this.noteMapper.toDto(type))
                            .build())
                    .orElse(ResponseDto.<NoteResponse>builder()
                            .code(-1)
                            .message("type is not found")
                            .build());

        } catch (Exception e) {
            return ResponseDto.<NoteResponse>builder()
                    .code(-1)
                    .message("Type while getting error")
                    .build();
        }
    }

    public ResponseDto<NoteResponse> update(NoteRequest request, Integer id) {
        List<ErrorDto> errors = this.noteValidation.validation(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<NoteResponse>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }
        try {
            return this.noteRepository.findByIdAndDeletedAtIsNull(id)
                    .map(type -> {
                        type.setUpdatedAt(LocalDateTime.now());
                        this.noteRepository.save(type);
                        this.noteMapper.update(type, request);

                        return ResponseDto.<NoteResponse>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.noteMapper.toDto(type))
                                .build();
                    })
                    .orElse(ResponseDto.<NoteResponse>builder()
                            .code(-1)
                            .message("Type is not found")
                            .build());

        } catch (Exception e) {
            return ResponseDto.<NoteResponse>builder()
                    .code(-1)
                    .message("type while updating error")
                    .build();
        }
    }

    public ResponseDto<NoteResponse> delete(Integer id) {
        try {
            return this.noteRepository.findByIdAndDeletedAtIsNull(id)
                    .map(type -> {
                        type.setDeletedAt(LocalDateTime.now());
                        this.noteRepository.save(type);

                        return ResponseDto.<NoteResponse>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.noteMapper.toDto(type))
                                .build();
                    })
                    .orElse(ResponseDto.<NoteResponse>builder()
                            .code(-1)
                            .message("Type is not found")
                            .build());

        } catch (Exception e) {
            return ResponseDto.<NoteResponse>builder()
                    .code(-1)
                    .message("Type while deleting error")
                    .build();
        }
    }

    public ResponseDto<List<NoteResponse>> getAllType() {

        try {
            List<Note> types = this.noteRepository.getAllByQuery();
            if (types.isEmpty()) {
                return ResponseDto.<List<NoteResponse>>builder()
                        .code(-1)
                        .message("Types are not found")
                        .build();
            }
            return ResponseDto.<List<NoteResponse>>builder()
                    .success(true)
                    .message("Ok")
                    .data(types.stream().map(this.noteMapper::toDto).toList())
                    .build();
        } catch (Exception e) {
            return ResponseDto.<List<NoteResponse>>builder()
                    .code(-1)
                    .message("Types while getting all")
                    .build();
        }
    }

    public ResponseDto<Page<NoteResponse>> getPage(Integer page, Integer size) {
        Page<Note> typesPage = this.noteRepository.findAllByDeletedAtIsNull(PageRequest.of(page, size));
        if (typesPage.isEmpty()) {
            return ResponseDto.<Page<NoteResponse>>builder()
                    .code(-1)
                    .message("types are not found")
                    .build();
        }
        return ResponseDto.<Page<NoteResponse>>builder()
                .success(true)
                .message("Ok")
                .data(typesPage.map(this.noteMapper::toDto))
                .build();
    }
}
