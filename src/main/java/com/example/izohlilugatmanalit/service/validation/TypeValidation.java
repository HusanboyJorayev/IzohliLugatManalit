package com.example.izohlilugatmanalit.service.validation;

import com.example.izohlilugatmanalit.dto.ErrorDto;
import com.example.izohlilugatmanalit.request.TypeRequest;

import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TypeValidation {

    public List<ErrorDto> validation(TypeRequest request) {
        List<ErrorDto> errors = new ArrayList<>();

        if (StringUtils.isBlank(request.getName())) {
            errors.add(new ErrorDto("name", "name cannot be null or empty"));
        }
        return errors;
    }
}
