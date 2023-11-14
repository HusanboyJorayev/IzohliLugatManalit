package com.example.izohlilugatmanalit.service;

import com.example.izohlilugatmanalit.dto.ErrorDto;
import com.example.izohlilugatmanalit.dto.ResponseDto;
import com.example.izohlilugatmanalit.entity.DayWord;
import com.example.izohlilugatmanalit.entity.Type;
import com.example.izohlilugatmanalit.repository.DayWordRepository;
import com.example.izohlilugatmanalit.request.DayWordRequest;
import com.example.izohlilugatmanalit.request.TypeRequest;
import com.example.izohlilugatmanalit.response.DayWordResponse;
import com.example.izohlilugatmanalit.response.TypeResponse;
import com.example.izohlilugatmanalit.service.mapper.DayWordMapper;
import com.example.izohlilugatmanalit.service.validation.DayWordValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DayWordService {
    private final DayWordRepository dayWordRepository;
    private final DayWordMapper dayWordMapper;
    private final DayWordValidation dayWordValidation;

    public ResponseDto<DayWordResponse> create(DayWordRequest request) {
        List<ErrorDto> errors = this.dayWordValidation.validation(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<DayWordResponse>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }
        try {
            DayWord type = this.dayWordMapper.toEntity(request);
            type.setCreatedAt(LocalDateTime.now());
            this.dayWordRepository.save(type);

            return ResponseDto.<DayWordResponse>builder()
                    .success(true)
                    .message("Ok")
                    .data(this.dayWordMapper.toDto(type))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<DayWordResponse>builder()
                    .code(-1)
                    .message("Type while saving error")
                    .build();
        }
    }

    public ResponseDto<DayWordResponse> get(Integer id) {
        try {
            return this.dayWordRepository.findByIdAndDeletedAtIsNull(id)
                    .map(type -> ResponseDto.<DayWordResponse>builder()
                            .success(true)
                            .message("Ok")
                            .data(this.dayWordMapper.toDto(type))
                            .build())
                    .orElse(ResponseDto.<DayWordResponse>builder()
                            .code(-1)
                            .message("type is not found")
                            .build());

        } catch (Exception e) {
            return ResponseDto.<DayWordResponse>builder()
                    .code(-1)
                    .message("Type while getting error")
                    .build();
        }
    }

    public ResponseDto<DayWordResponse> update(DayWordRequest request, Integer id) {
        List<ErrorDto> errors = this.dayWordValidation.validation(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<DayWordResponse>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }
        try {
            return this.dayWordRepository.findByIdAndDeletedAtIsNull(id)
                    .map(type -> {
                        type.setUpdatedAt(LocalDateTime.now());
                        this.dayWordRepository.save(type);
                        this.dayWordMapper.update(type, request);

                        return ResponseDto.<DayWordResponse>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.dayWordMapper.toDto(type))
                                .build();
                    })
                    .orElse(ResponseDto.<DayWordResponse>builder()
                            .code(-1)
                            .message("Type is not found")
                            .build());

        } catch (Exception e) {
            return ResponseDto.<DayWordResponse>builder()
                    .code(-1)
                    .message("type while updating error")
                    .build();
        }
    }

    public ResponseDto<DayWordResponse> delete(Integer id) {
        try {
            return this.dayWordRepository.findByIdAndDeletedAtIsNull(id)
                    .map(type -> {
                        type.setDeletedAt(LocalDateTime.now());
                        this.dayWordRepository.save(type);

                        return ResponseDto.<DayWordResponse>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.dayWordMapper.toDto(type))
                                .build();
                    })
                    .orElse(ResponseDto.<DayWordResponse>builder()
                            .code(-1)
                            .message("Type is not found")
                            .build());

        } catch (Exception e) {
            return ResponseDto.<DayWordResponse>builder()
                    .code(-1)
                    .message("Type while deleting error")
                    .build();
        }
    }

    public ResponseDto<List<DayWordResponse>> getAllType() {

        try {
            List<DayWord> types = this.dayWordRepository.getAllByQuery();
            if (types.isEmpty()) {
                return ResponseDto.<List<DayWordResponse>>builder()
                        .code(-1)
                        .message("Types are not found")
                        .build();
            }
            return ResponseDto.<List<DayWordResponse>>builder()
                    .success(true)
                    .message("Ok")
                    .data(types.stream().map(this.dayWordMapper::toDto).toList())
                    .build();
        } catch (Exception e) {
            return ResponseDto.<List<DayWordResponse>>builder()
                    .code(-1)
                    .message("Types while getting all")
                    .build();
        }
    }

    public ResponseDto<Page<DayWordResponse>> getPage(Integer page, Integer size) {
        Page<DayWord> typesPage = this.dayWordRepository.findAllByDeletedAtIsNull(PageRequest.of(page, size));
        if (typesPage.isEmpty()) {
            return ResponseDto.<Page<DayWordResponse>>builder()
                    .code(-1)
                    .message("types are not found")
                    .build();
        }
        return ResponseDto.<Page<DayWordResponse>>builder()
                .success(true)
                .message("Ok")
                .data(typesPage.map(this.dayWordMapper::toDto))
                .build();
    }
}
