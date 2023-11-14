package com.example.izohlilugatmanalit.controller;

import com.example.izohlilugatmanalit.dto.ResponseDto;
import com.example.izohlilugatmanalit.request.WordInSentenceRequest;
import com.example.izohlilugatmanalit.response.WordInSentenceResponse;
import com.example.izohlilugatmanalit.service.WordInSentenceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("wordInSentence")
@Tag(name = "WordInSentence api")
public class WordInSentenceController {
    private final WordInSentenceService wordInSentenceService;

    @PostMapping("/create")
    public ResponseDto<WordInSentenceResponse> create(@RequestBody @Valid WordInSentenceRequest request) {
        return this.wordInSentenceService.create(request);
    }

    @GetMapping("/get")
    public ResponseDto<WordInSentenceResponse> get(@RequestParam Integer id) {
        return this.wordInSentenceService.get(id);
    }

    @PutMapping("/update")
    ResponseDto<WordInSentenceResponse> update(@RequestBody @Valid WordInSentenceRequest request, @RequestParam Integer id) {
        return this.wordInSentenceService.update(request, id);
    }

    @DeleteMapping("/delet")
    public ResponseDto<WordInSentenceResponse> delete(@RequestParam Integer id) {
        return this.wordInSentenceService.delete(id);
    }

    @GetMapping("/getAll")
    public ResponseDto<List<WordInSentenceResponse>> getAllType() {
        return this.wordInSentenceService.getAllType();
    }

    @GetMapping("/pageable")
    public ResponseDto<Page<WordInSentenceResponse>> getPage(@RequestParam Integer page, @RequestParam Integer size) {
        return this.wordInSentenceService.getPage(page, size);
    }
}
