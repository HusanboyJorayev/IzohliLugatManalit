package com.example.izohlilugatmanalit.service;

import com.example.izohlilugatmanalit.dto.ErrorDto;
import com.example.izohlilugatmanalit.dto.ResponseDto;
import com.example.izohlilugatmanalit.entity.WordType;
import com.example.izohlilugatmanalit.repository.WordTypeRepository;
import com.example.izohlilugatmanalit.request.WordTypeRequest;
import com.example.izohlilugatmanalit.response.WordTypeResponse;
import com.example.izohlilugatmanalit.service.mapper.WordTypeMapper;
import com.example.izohlilugatmanalit.service.validation.WordTypeValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WordTypeService {
    private final WordTypeValidation wordTypeValidation;
    private final WordTypeMapper wordTypeMapper;
    private final WordTypeRepository wordTypeRepository;

    public ResponseDto<WordTypeResponse> create(WordTypeRequest request) {
        List<ErrorDto> errors = this.wordTypeValidation.validation(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<WordTypeResponse>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }
        try {
            WordType type = this.wordTypeMapper.toEntity(request);
            type.setCreatedAt(LocalDateTime.now());
            this.wordTypeRepository.save(type);

            return ResponseDto.<WordTypeResponse>builder()
                    .success(true)
                    .message("Ok")
                    .data(this.wordTypeMapper.toDto(type))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<WordTypeResponse>builder()
                    .code(-1)
                    .message("Type while saving error")
                    .build();
        }
    }

    public ResponseDto<WordTypeResponse> get(Integer id) {
        try {
            return this.wordTypeRepository.findByIdAndDeletedAtIsNull(id)
                    .map(type -> ResponseDto.<WordTypeResponse>builder()
                            .success(true)
                            .message("Ok")
                            .data(this.wordTypeMapper.toDto(type))
                            .build())
                    .orElse(ResponseDto.<WordTypeResponse>builder()
                            .code(-1)
                            .message("type is not found")
                            .build());

        } catch (Exception e) {
            return ResponseDto.<WordTypeResponse>builder()
                    .code(-1)
                    .message("Type while getting error")
                    .build();
        }
    }

    public ResponseDto<WordTypeResponse> update(WordTypeRequest request, Integer id) {
        List<ErrorDto> errors = this.wordTypeValidation.validation(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<WordTypeResponse>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }
        try {
            return this.wordTypeRepository.findByIdAndDeletedAtIsNull(id)
                    .map(type -> {
                        type.setUpdatedAt(LocalDateTime.now());
                        this.wordTypeRepository.save(type);
                        this.wordTypeMapper.update(type, request);

                        return ResponseDto.<WordTypeResponse>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.wordTypeMapper.toDto(type))
                                .build();
                    })
                    .orElse(ResponseDto.<WordTypeResponse>builder()
                            .code(-1)
                            .message("Type is not found")
                            .build());

        } catch (Exception e) {
            return ResponseDto.<WordTypeResponse>builder()
                    .code(-1)
                    .message("type while updating error")
                    .build();
        }
    }

    public ResponseDto<WordTypeResponse> delete(Integer id) {
        try {
            return this.wordTypeRepository.findByIdAndDeletedAtIsNull(id)
                    .map(type -> {
                        type.setDeletedAt(LocalDateTime.now());
                        this.wordTypeRepository.save(type);

                        return ResponseDto.<WordTypeResponse>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.wordTypeMapper.toDto(type))
                                .build();
                    })
                    .orElse(ResponseDto.<WordTypeResponse>builder()
                            .code(-1)
                            .message("Type is not found")
                            .build());

        } catch (Exception e) {
            return ResponseDto.<WordTypeResponse>builder()
                    .code(-1)
                    .message("Type while deleting error")
                    .build();
        }
    }

    public ResponseDto<List<WordTypeResponse>> getAllType() {

        try {
            List<WordType> types = this.wordTypeRepository.getAllByQuery();
            if (types.isEmpty()) {
                return ResponseDto.<List<WordTypeResponse>>builder()
                        .code(-1)
                        .message("Types are not found")
                        .build();
            }
            return ResponseDto.<List<WordTypeResponse>>builder()
                    .success(true)
                    .message("Ok")
                    .data(types.stream().map(this.wordTypeMapper::toDto).toList())
                    .build();
        } catch (Exception e) {
            return ResponseDto.<List<WordTypeResponse>>builder()
                    .code(-1)
                    .message("Types while getting all")
                    .build();
        }
    }

    public ResponseDto<Page<WordTypeResponse>> getPage(Integer page, Integer size) {
        Page<WordType> typesPage = this.wordTypeRepository.findAllByDeletedAtIsNull(PageRequest.of(page, size));
        if (typesPage.isEmpty()) {
            return ResponseDto.<Page<WordTypeResponse>>builder()
                    .code(-1)
                    .message("types are not found")
                    .build();
        }
        return ResponseDto.<Page<WordTypeResponse>>builder()
                .success(true)
                .message("Ok")
                .data(typesPage.map(this.wordTypeMapper::toDto))
                .build();
    }
}
