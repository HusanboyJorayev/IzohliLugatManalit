package com.example.izohlilugatmanalit.service.validation;

import com.example.izohlilugatmanalit.dto.ErrorDto;
import com.example.izohlilugatmanalit.request.DayWordRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DayWordValidation {
    public List<ErrorDto> validation(DayWordRequest request) {
        List<ErrorDto> errors = new ArrayList<>();

        if (request.getWordId() == null) {
            errors.add(new ErrorDto("wordId", "wordId cannot be null or empty"));
        }
        return errors;
    }
}
