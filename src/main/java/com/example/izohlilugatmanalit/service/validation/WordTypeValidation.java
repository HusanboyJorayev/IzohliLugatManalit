package com.example.izohlilugatmanalit.service.validation;

import com.example.izohlilugatmanalit.dto.ErrorDto;
import com.example.izohlilugatmanalit.request.WordTypeRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WordTypeValidation {
    public List<ErrorDto> validation(WordTypeRequest request) {
        List<ErrorDto> errors = new ArrayList<>();

        if (request.getWordId() == null) {
            errors.add(new ErrorDto("wordId", "wordId cannot be null or empty"));
        }
        return errors;
    }
}
