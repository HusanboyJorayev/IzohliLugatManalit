package com.example.izohlilugatmanalit.service.validation;

import com.example.izohlilugatmanalit.dto.ErrorDto;
import com.example.izohlilugatmanalit.request.WordRequest;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WordValidation {
    public List<ErrorDto> validation(WordRequest request) {
        List<ErrorDto> errors = new ArrayList<>();

        if (StringUtils.isBlank(request.getName())) {
            errors.add(new ErrorDto("name", "name cannot be null or empty"));
        }
        if (StringUtils.isBlank(request.getTranscript())) {
            errors.add(new ErrorDto("transcript", "transcript cannot be null or empty"));
        }
        return errors;
    }
}
