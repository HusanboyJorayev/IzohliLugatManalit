package com.example.izohlilugatmanalit.service.validation;

import com.example.izohlilugatmanalit.dto.ErrorDto;
import com.example.izohlilugatmanalit.request.NoteRequest;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NoteValidation {
    public List<ErrorDto> validation(NoteRequest request) {
        List<ErrorDto> errors = new ArrayList<>();

        if (StringUtils.isBlank(request.getDescription())) {
            errors.add(new ErrorDto("description", "description cannot be null or empty"));
        }
        if (StringUtils.isBlank(request.getSource())) {
            errors.add(new ErrorDto("source", "source cannot be null or empty"));
        }
        if (StringUtils.isBlank(request.getTitle())) {
            errors.add(new ErrorDto("title", "title cannot be null or empty"));
        }
        return errors;
    }
}
