package com.example.izohlilugatmanalit.service.validation;

import com.example.izohlilugatmanalit.dto.ErrorDto;
import com.example.izohlilugatmanalit.request.WordInSentenceRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WordInSentenceValidation {
    public List<ErrorDto> validation(WordInSentenceRequest request) {
        List<ErrorDto> errors = new ArrayList<>();

        if (request.getWordId() == null) {
            errors.add(new ErrorDto("wordId", "wordId cannot be null or empty"));
        }
        if (request.getSentenceId() == null) {
            errors.add(new ErrorDto("sentenceId", "sentenceId cannot be null or empty"));
        }
        if (request.getOrders() == null) {
            errors.add(new ErrorDto("orders", "orders cannot be null or empty"));
        }
        return errors;
    }
}
