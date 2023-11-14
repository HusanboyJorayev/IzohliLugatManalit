package com.example.izohlilugatmanalit.service;

import com.example.izohlilugatmanalit.dto.ErrorDto;
import com.example.izohlilugatmanalit.dto.ResponseDto;
import com.example.izohlilugatmanalit.entity.Word;
import com.example.izohlilugatmanalit.repository.WordRepository;
import com.example.izohlilugatmanalit.request.WordRequest;
import com.example.izohlilugatmanalit.response.WordResponse;
import com.example.izohlilugatmanalit.service.mapper.WordMapper;
import com.example.izohlilugatmanalit.service.validation.WordValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WordService {
    private final WordMapper wordMapper;
    private final WordRepository wordRepository;
    private final WordValidation wordValidation;


    public ResponseDto<WordResponse> create(WordRequest request) {
        List<ErrorDto> errors = this.wordValidation.validation(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<WordResponse>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }
        try {
            Word word = this.wordMapper.toEntity(request);
            word.setCreatedAt(LocalDateTime.now());
            this.wordRepository.save(word);

            return ResponseDto.<WordResponse>builder()
                    .success(true)
                    .message("Ok")
                    .data(this.wordMapper.toDto(word))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<WordResponse>builder()
                    .code(-1)
                    .message("Type while saving error")
                    .build();
        }
    }

    public ResponseDto<WordResponse> get(Integer id) {
        try {
            return this.wordRepository.findByIdAndDeletedAtIsNull(id)
                    .map(word -> ResponseDto.<WordResponse>builder()
                            .success(true)
                            .message("Ok")
                            .data(this.wordMapper.toDto(word))
                            .build())
                    .orElse(ResponseDto.<WordResponse>builder()
                            .code(-1)
                            .message("type is not found")
                            .build());

        } catch (Exception e) {
            return ResponseDto.<WordResponse>builder()
                    .code(-1)
                    .message("Type while getting error")
                    .build();
        }
    }

    public ResponseDto<WordResponse> update(WordRequest request, Integer id) {
        List<ErrorDto> errors = this.wordValidation.validation(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<WordResponse>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }
        try {
            return this.wordRepository.findByIdAndDeletedAtIsNull(id)
                    .map(word -> {
                        word.setUpdatedAt(LocalDateTime.now());
                        this.wordRepository.save(word);
                        this.wordMapper.update(word, request);

                        return ResponseDto.<WordResponse>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.wordMapper.toDto(word))
                                .build();
                    })
                    .orElse(ResponseDto.<WordResponse>builder()
                            .code(-1)
                            .message("Type is not found")
                            .build());

        } catch (Exception e) {
            return ResponseDto.<WordResponse>builder()
                    .code(-1)
                    .message("type while updating error")
                    .build();
        }
    }

    public ResponseDto<WordResponse> delete(Integer id) {
        try {
            return this.wordRepository.findByIdAndDeletedAtIsNull(id)
                    .map(word -> {
                        word.setDeletedAt(LocalDateTime.now());
                        this.wordRepository.save(word);

                        return ResponseDto.<WordResponse>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.wordMapper.toDto(word))
                                .build();
                    })
                    .orElse(ResponseDto.<WordResponse>builder()
                            .code(-1)
                            .message("Type is not found")
                            .build());

        } catch (Exception e) {
            return ResponseDto.<WordResponse>builder()
                    .code(-1)
                    .message("Type while deleting error")
                    .build();
        }
    }

    public ResponseDto<List<WordResponse>> getAllType() {

        try {
            List<Word> types = this.wordRepository.getAllByQuery();
            if (types.isEmpty()) {
                return ResponseDto.<List<WordResponse>>builder()
                        .code(-1)
                        .message("Types are not found")
                        .build();
            }
            return ResponseDto.<List<WordResponse>>builder()
                    .success(true)
                    .message("Ok")
                    .data(types.stream().map(this.wordMapper::toDto).toList())
                    .build();
        } catch (Exception e) {
            return ResponseDto.<List<WordResponse>>builder()
                    .code(-1)
                    .message("Types while getting all")
                    .build();
        }
    }

    public ResponseDto<Page<WordResponse>> getPage(Integer page, Integer size) {
        Page<Word> wordPage = this.wordRepository.findAllByDeletedAtIsNull(PageRequest.of(page, size));
        if (wordPage.isEmpty()) {
            return ResponseDto.<Page<WordResponse>>builder()
                    .code(-1)
                    .message("types are not found")
                    .build();
        }
        return ResponseDto.<Page<WordResponse>>builder()
                .success(true)
                .message("Ok")
                .data(wordPage.map(this.wordMapper::toDto))
                .build();
    }
}
