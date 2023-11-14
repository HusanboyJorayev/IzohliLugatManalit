package com.example.izohlilugatmanalit.controller;

import com.example.izohlilugatmanalit.dto.ResponseDto;
import com.example.izohlilugatmanalit.request.WordRequest;
import com.example.izohlilugatmanalit.response.WordResponse;
import com.example.izohlilugatmanalit.service.WordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("word")
@Tag(name = "Word api")
public class WordController {
    private final WordService wordService;

    @PostMapping("/create")
    public ResponseDto<WordResponse> create(@RequestBody @Valid WordRequest request) {
        return this.wordService.create(request);
    }

    @GetMapping("/get")
    public ResponseDto<WordResponse> get(@RequestParam Integer id) {
        return this.wordService.get(id);
    }

    @PutMapping("/update")
    ResponseDto<WordResponse> update(@RequestBody @Valid WordRequest request, @RequestParam Integer id) {
        return this.wordService.update(request, id);
    }

    @DeleteMapping("/delete")
    public ResponseDto<WordResponse> delete(@RequestParam Integer id) {
        return this.wordService.delete(id);
    }

    @GetMapping("/getAll")
    public ResponseDto<List<WordResponse>> getAllType() {
        return this.wordService.getAllType();
    }

    @GetMapping("/pageable")
    public ResponseDto<Page<WordResponse>> getPage(@RequestParam Integer page, @RequestParam Integer size) {
        return this.wordService.getPage(page, size);
    }
}
