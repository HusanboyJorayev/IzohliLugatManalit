package com.example.izohlilugatmanalit.service.validation;

import com.example.izohlilugatmanalit.dto.ErrorDto;
import com.example.izohlilugatmanalit.request.SentenceRequest;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SentenceValidation {
    public List<ErrorDto> validation(SentenceRequest request) {
        List<ErrorDto> errors = new ArrayList<>();

        if (StringUtils.isBlank(request.getContent())) {
            errors.add(new ErrorDto("content", "content cannot be null or empty"));
        }
        return errors;
    }
}
