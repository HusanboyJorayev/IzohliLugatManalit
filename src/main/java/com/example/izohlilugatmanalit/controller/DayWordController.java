package com.example.izohlilugatmanalit.controller;

import com.example.izohlilugatmanalit.dto.ResponseDto;
import com.example.izohlilugatmanalit.request.DayWordRequest;
import com.example.izohlilugatmanalit.response.DayWordResponse;
import com.example.izohlilugatmanalit.service.DayWordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("dayWord")
@Tag(name = "DayWord api")
public class DayWordController {
    private final DayWordService dayWordService;

    @PostMapping("/create")
    public ResponseDto<DayWordResponse> create(@RequestBody @Valid DayWordRequest request) {
        return this.dayWordService.create(request);
    }

    @GetMapping("/get")
    public ResponseDto<DayWordResponse> get(@RequestParam Integer id) {
        return this.dayWordService.get(id);
    }

    @PutMapping("/update")
    ResponseDto<DayWordResponse> update(@RequestBody @Valid DayWordRequest request, @RequestParam Integer id) {
        return this.dayWordService.update(request, id);
    }

    @DeleteMapping("/delet")
    public ResponseDto<DayWordResponse> delete(@RequestParam Integer id) {
        return this.dayWordService.delete(id);
    }

    @GetMapping("/getAll")
    public ResponseDto<List<DayWordResponse>> getAllType() {
        return this.dayWordService.getAllType();
    }

    @GetMapping("/pageable")
    public ResponseDto<Page<DayWordResponse>> getPage(@RequestParam Integer page, @RequestParam Integer size) {
        return this.dayWordService.getPage(page, size);
    }
}
