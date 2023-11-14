package com.example.izohlilugatmanalit.service;

import com.example.izohlilugatmanalit.dto.ErrorDto;
import com.example.izohlilugatmanalit.dto.ResponseDto;
import com.example.izohlilugatmanalit.entity.Sentence;
import com.example.izohlilugatmanalit.repository.SentenceRepository;
import com.example.izohlilugatmanalit.request.SentenceRequest;
import com.example.izohlilugatmanalit.response.SentenceResponce;
import com.example.izohlilugatmanalit.service.mapper.SentenceMapper;
import com.example.izohlilugatmanalit.service.validation.SentenceValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SentenceService {
    private final SentenceRepository sentenceRepository;
    private final SentenceMapper sentenceMapper;
    private final SentenceValidation sentenceValidation;

    public ResponseDto<SentenceResponce> create(SentenceRequest request) {
        List<ErrorDto> errors = this.sentenceValidation.validation(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<SentenceResponce>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }
        try {
            Sentence sentence = this.sentenceMapper.toEntity(request);
            sentence.setCreatedAt(LocalDateTime.now());
            this.sentenceRepository.save(sentence);

            return ResponseDto.<SentenceResponce>builder()
                    .success(true)
                    .message("Ok")
                    .data(this.sentenceMapper.toDto(sentence))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<SentenceResponce>builder()
                    .code(-1)
                    .message("Type while saving error")
                    .build();
        }
    }

    public ResponseDto<SentenceResponce> get(Integer id) {
        try {
            return this.sentenceRepository.findByIdAndDeletedAtIsNull(id)
                    .map(sentence -> ResponseDto.<SentenceResponce>builder()
                            .success(true)
                            .message("Ok")
                            .data(this.sentenceMapper.toDto(sentence))
                            .build())
                    .orElse(ResponseDto.<SentenceResponce>builder()
                            .code(-1)
                            .message("type is not found")
                            .build());

        } catch (Exception e) {
            return ResponseDto.<SentenceResponce>builder()
                    .code(-1)
                    .message("Type while getting error")
                    .build();
        }
    }

    public ResponseDto<SentenceResponce> update(SentenceRequest request, Integer id) {
        List<ErrorDto> errors = this.sentenceValidation.validation(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<SentenceResponce>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }
        try {
            return this.sentenceRepository.findByIdAndDeletedAtIsNull(id)
                    .map(s -> {
                        s.setUpdatedAt(LocalDateTime.now());
                        this.sentenceRepository.save(s);
                        this.sentenceMapper.update(s, request);

                        return ResponseDto.<SentenceResponce>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.sentenceMapper.toDto(s))
                                .build();
                    })
                    .orElse(ResponseDto.<SentenceResponce>builder()
                            .code(-1)
                            .message("Type is not found")
                            .build());

        } catch (Exception e) {
            return ResponseDto.<SentenceResponce>builder()
                    .code(-1)
                    .message("type while updating error")
                    .build();
        }
    }

    public ResponseDto<SentenceResponce> delete(Integer id) {
        try {
            return this.sentenceRepository.findByIdAndDeletedAtIsNull(id)
                    .map(s -> {
                        s.setDeletedAt(LocalDateTime.now());
                        this.sentenceRepository.save(s);

                        return ResponseDto.<SentenceResponce>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.sentenceMapper.toDto(s))
                                .build();
                    })
                    .orElse(ResponseDto.<SentenceResponce>builder()
                            .code(-1)
                            .message("Type is not found")
                            .build());

        } catch (Exception e) {
            return ResponseDto.<SentenceResponce>builder()
                    .code(-1)
                    .message("Type while deleting error")
                    .build();
        }
    }

    public ResponseDto<List<SentenceResponce>> getAllType() {

        try {
            List<Sentence> types = this.sentenceRepository.getAllByQuery();
            if (types.isEmpty()) {
                return ResponseDto.<List<SentenceResponce>>builder()
                        .code(-1)
                        .message("Types are not found")
                        .build();
            }
            return ResponseDto.<List<SentenceResponce>>builder()
                    .success(true)
                    .message("Ok")
                    .data(types.stream().map(this.sentenceMapper::toDto).toList())
                    .build();
        } catch (Exception e) {
            return ResponseDto.<List<SentenceResponce>>builder()
                    .code(-1)
                    .message("Types while getting all")
                    .build();
        }
    }

    public ResponseDto<Page<SentenceResponce>> getPage(Integer page, Integer size) {
        Page<Sentence> typesPage = this.sentenceRepository.findAllByDeletedAtIsNull(PageRequest.of(page, size));
        if (typesPage.isEmpty()) {
            return ResponseDto.<Page<SentenceResponce>>builder()
                    .code(-1)
                    .message("types are not found")
                    .build();
        }
        return ResponseDto.<Page<SentenceResponce>>builder()
                .success(true)
                .message("Ok")
                .data(typesPage.map(this.sentenceMapper::toDto))
                .build();
    }
}
