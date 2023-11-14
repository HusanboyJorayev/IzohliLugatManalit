package com.example.izohlilugatmanalit.controller;

import com.example.izohlilugatmanalit.dto.ResponseDto;
import com.example.izohlilugatmanalit.request.SentenceRequest;
import com.example.izohlilugatmanalit.response.SentenceResponce;
import com.example.izohlilugatmanalit.service.SentenceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("sentence")
@Tag(name = "Sentence api")
public class SentenceController {
    private final SentenceService sentenceService;

    @PostMapping("/create")
    public ResponseDto<SentenceResponce> create(@RequestBody @Valid SentenceRequest request) {
        return this.sentenceService.create(request);
    }

    @GetMapping("/get")
    public ResponseDto<SentenceResponce> get(@RequestParam Integer id) {
        return this.sentenceService.get(id);
    }

    @PutMapping("/update")
    ResponseDto<SentenceResponce> update(@RequestBody @Valid SentenceRequest request, @RequestParam Integer id) {
        return this.sentenceService.update(request, id);
    }

    @DeleteMapping("/delet")
    public ResponseDto<SentenceResponce> delete(@RequestParam Integer id) {
        return this.sentenceService.delete(id);
    }

    @GetMapping("/getAll")
    public ResponseDto<List<SentenceResponce>> getAllType() {
        return this.sentenceService.getAllType();
    }

    @GetMapping("/pageable")
    public ResponseDto<Page<SentenceResponce>> getPage(@RequestParam Integer page, @RequestParam Integer size) {
        return this.sentenceService.getPage(page, size);
    }
}
