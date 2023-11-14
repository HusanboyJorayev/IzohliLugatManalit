package com.example.izohlilugatmanalit.service;

import com.example.izohlilugatmanalit.dto.ErrorDto;
import com.example.izohlilugatmanalit.dto.ResponseDto;
import com.example.izohlilugatmanalit.entity.Type;
import com.example.izohlilugatmanalit.repository.TypeRepository;
import com.example.izohlilugatmanalit.request.TypeRequest;
import com.example.izohlilugatmanalit.response.TypeResponse;
import com.example.izohlilugatmanalit.service.mapper.TypeMapper;
import com.example.izohlilugatmanalit.service.validation.TypeValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TypeService {

    private final TypeMapper typeMapper;
    private final TypeRepository typeRepository;
    private final TypeValidation typeValidation;

    public ResponseDto<TypeResponse> create(TypeRequest request) {
        List<ErrorDto> errors = this.typeValidation.validation(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<TypeResponse>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }
        try {
            Type type = this.typeMapper.toEntity(request);
            type.setCreatedAt(LocalDateTime.now());
            this.typeRepository.save(type);

            return ResponseDto.<TypeResponse>builder()
                    .success(true)
                    .message("Ok")
                    .data(this.typeMapper.toDto(type))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<TypeResponse>builder()
                    .code(-1)
                    .message("Type while saving error")
                    .build();
        }
    }

    public ResponseDto<TypeResponse> get(Integer id) {
        try {
            return this.typeRepository.findByIdAndDeletedAtIsNull(id)
                    .map(type -> ResponseDto.<TypeResponse>builder()
                            .success(true)
                            .message("Ok")
                            .data(this.typeMapper.toDto(type))
                            .build())
                    .orElse(ResponseDto.<TypeResponse>builder()
                            .code(-1)
                            .message("type is not found")
                            .build());

        } catch (Exception e) {
            return ResponseDto.<TypeResponse>builder()
                    .code(-1)
                    .message("Type while getting error")
                    .build();
        }
    }

    public ResponseDto<TypeResponse> update(TypeRequest request, Integer id) {
        List<ErrorDto> errors = this.typeValidation.validation(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<TypeResponse>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }
        try {
            return this.typeRepository.findByIdAndDeletedAtIsNull(id)
                    .map(type -> {
                        type.setUpdatedAt(LocalDateTime.now());
                        this.typeRepository.save(type);
                        this.typeMapper.update(type, request);

                        return ResponseDto.<TypeResponse>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.typeMapper.toDto(type))
                                .build();
                    })
                    .orElse(ResponseDto.<TypeResponse>builder()
                            .code(-1)
                            .message("Type is not found")
                            .build());

        } catch (Exception e) {
            return ResponseDto.<TypeResponse>builder()
                    .code(-1)
                    .message("type while updating error")
                    .build();
        }
    }

    public ResponseDto<TypeResponse> delete(Integer id) {
        try {
            return this.typeRepository.findByIdAndDeletedAtIsNull(id)
                    .map(type -> {
                        type.setDeletedAt(LocalDateTime.now());
                        this.typeRepository.save(type);

                        return ResponseDto.<TypeResponse>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.typeMapper.toDto(type))
                                .build();
                    })
                    .orElse(ResponseDto.<TypeResponse>builder()
                            .code(-1)
                            .message("Type is not found")
                            .build());

        } catch (Exception e) {
            return ResponseDto.<TypeResponse>builder()
                    .code(-1)
                    .message("Type while deleting error")
                    .build();
        }
    }

    public ResponseDto<List<TypeResponse>> getAllType() {

        try {
            List<Type> types = this.typeRepository.getAllByQuery();
            if (types.isEmpty()) {
                return ResponseDto.<List<TypeResponse>>builder()
                        .code(-1)
                        .message("Types are not found")
                        .build();
            }
            return ResponseDto.<List<TypeResponse>>builder()
                    .success(true)
                    .message("Ok")
                    .data(types.stream().map(this.typeMapper::toDto).toList())
                    .build();
        } catch (Exception e) {
            return ResponseDto.<List<TypeResponse>>builder()
                    .code(-1)
                    .message("Types while getting all")
                    .build();
        }
    }

    public ResponseDto<Page<TypeResponse>> getPage(Integer page, Integer size) {
        Page<Type> typesPage = this.typeRepository.findAllByDeletedAtIsNull(PageRequest.of(page, size));
        if (typesPage.isEmpty()) {
            return ResponseDto.<Page<TypeResponse>>builder()
                    .code(-1)
                    .message("types are not found")
                    .build();
        }
        return ResponseDto.<Page<TypeResponse>>builder()
                .success(true)
                .message("Ok")
                .data(typesPage.map(this.typeMapper::toDto))
                .build();
    }
}
