package com.example.izohlilugatmanalit.service;

import com.example.izohlilugatmanalit.dto.ErrorDto;
import com.example.izohlilugatmanalit.dto.ResponseDto;
import com.example.izohlilugatmanalit.entity.Category;
import com.example.izohlilugatmanalit.repository.CategoryRepository;
import com.example.izohlilugatmanalit.request.CategoryRequest;
import com.example.izohlilugatmanalit.response.CategoryResponse;
import com.example.izohlilugatmanalit.service.mapper.CategoryMapper;
import com.example.izohlilugatmanalit.service.validation.CategoryValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryValidation categoryValidation;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    public ResponseDto<CategoryResponse> create(CategoryRequest request) {
        List<ErrorDto> errors = this.categoryValidation.validation(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<CategoryResponse>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }
        try {
            Category category = this.categoryMapper.toEntity(request);
            category.setCreatedAt(LocalDateTime.now());
            this.categoryRepository.save(category);

            return ResponseDto.<CategoryResponse>builder()
                    .success(true)
                    .message("Ok")
                    .data(this.categoryMapper.toDto(category))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<CategoryResponse>builder()
                    .code(-1)
                    .message("Type while saving error")
                    .build();
        }
    }

    public ResponseDto<CategoryResponse> get(Integer id) {
        try {
            return this.categoryRepository.findByIdAndDeletedAtIsNull(id)
                    .map(category -> ResponseDto.<CategoryResponse>builder()
                            .success(true)
                            .message("Ok")
                            .data(this.categoryMapper.toDto(category))
                            .build())
                    .orElse(ResponseDto.<CategoryResponse>builder()
                            .code(-1)
                            .message("type is not found")
                            .build());

        } catch (Exception e) {
            return ResponseDto.<CategoryResponse>builder()
                    .code(-1)
                    .message("Type while getting error")
                    .build();
        }
    }

    public ResponseDto<CategoryResponse> update(CategoryRequest request, Integer id) {
        List<ErrorDto> errors = this.categoryValidation.validation(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<CategoryResponse>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }
        try {
            return this.categoryRepository.findByIdAndDeletedAtIsNull(id)
                    .map(category -> {
                        category.setUpdatedAt(LocalDateTime.now());
                        this.categoryRepository.save(category);
                        this.categoryMapper.update(category, request);

                        return ResponseDto.<CategoryResponse>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.categoryMapper.toDto(category))
                                .build();
                    })
                    .orElse(ResponseDto.<CategoryResponse>builder()
                            .code(-1)
                            .message("Type is not found")
                            .build());

        } catch (Exception e) {
            return ResponseDto.<CategoryResponse>builder()
                    .code(-1)
                    .message("type while updating error")
                    .build();
        }
    }

    public ResponseDto<CategoryResponse> delete(Integer id) {
        try {
            return this.categoryRepository.findByIdAndDeletedAtIsNull(id)
                    .map(category -> {
                        category.setDeletedAt(LocalDateTime.now());
                        this.categoryRepository.save(category);

                        return ResponseDto.<CategoryResponse>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.categoryMapper.toDto(category))
                                .build();
                    })
                    .orElse(ResponseDto.<CategoryResponse>builder()
                            .code(-1)
                            .message("Type is not found")
                            .build());

        } catch (Exception e) {
            return ResponseDto.<CategoryResponse>builder()
                    .code(-1)
                    .message("Type while deleting error")
                    .build();
        }
    }

    public ResponseDto<List<CategoryResponse>> getAllType() {

        try {
            List<Category> categories = this.categoryRepository.getAllByQuery();
            if (categories.isEmpty()) {
                return ResponseDto.<List<CategoryResponse>>builder()
                        .code(-1)
                        .message("Types are not found")
                        .build();
            }
            return ResponseDto.<List<CategoryResponse>>builder()
                    .success(true)
                    .message("Ok")
                    .data(categories.stream().map(this.categoryMapper::toDto).toList())
                    .build();
        } catch (Exception e) {
            return ResponseDto.<List<CategoryResponse>>builder()
                    .code(-1)
                    .message("Types while getting all")
                    .build();
        }
    }

    public ResponseDto<Page<CategoryResponse>> getPage(Integer page, Integer size) {
        Page<Category> categoryPage = this.categoryRepository.findAllByDeletedAtIsNull(PageRequest.of(page, size));
        if (categoryPage.isEmpty()) {
            return ResponseDto.<Page<CategoryResponse>>builder()
                    .code(-1)
                    .message("types are not found")
                    .build();
        }
        return ResponseDto.<Page<CategoryResponse>>builder()
                .success(true)
                .message("Ok")
                .data(categoryPage.map(this.categoryMapper::toDto))
                .build();
    }
}
