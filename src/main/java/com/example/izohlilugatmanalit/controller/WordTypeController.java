package com.example.izohlilugatmanalit.controller;

import com.example.izohlilugatmanalit.dto.ResponseDto;
import com.example.izohlilugatmanalit.request.WordTypeRequest;
import com.example.izohlilugatmanalit.response.WordTypeResponse;
import com.example.izohlilugatmanalit.service.WordTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("wordType")
@Tag(name = "WordType api")
public class WordTypeController {
    private final WordTypeService wordTypeService;

    @PostMapping("/create")
    public ResponseDto<WordTypeResponse> create(@RequestBody @Valid WordTypeRequest request) {
        return this.wordTypeService.create(request);
    }

    @GetMapping("/get")
    public ResponseDto<WordTypeResponse> get(@RequestParam Integer id) {
        return this.wordTypeService.get(id);
    }

    @PutMapping("/update")
    ResponseDto<WordTypeResponse> update(@RequestBody @Valid WordTypeRequest request, @RequestParam Integer id) {
        return this.wordTypeService.update(request, id);
    }

    @DeleteMapping("/delet")
    public ResponseDto<WordTypeResponse> delete(@RequestParam Integer id) {
        return this.wordTypeService.delete(id);
    }

    @GetMapping("/getAll")
    public ResponseDto<List<WordTypeResponse>> getAllType() {
        return this.wordTypeService.getAllType();
    }

    @GetMapping("/pageable")
    public ResponseDto<Page<WordTypeResponse>> getPage(@RequestParam Integer page, @RequestParam Integer size) {
        return this.wordTypeService.getPage(page, size);
    }
}
