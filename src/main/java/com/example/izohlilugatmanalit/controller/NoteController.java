package com.example.izohlilugatmanalit.controller;

import com.example.izohlilugatmanalit.dto.ResponseDto;
import com.example.izohlilugatmanalit.request.NoteRequest;
import com.example.izohlilugatmanalit.response.NoteResponse;
import com.example.izohlilugatmanalit.service.NoteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("note")
@Tag(name = "ANote api")
public class NoteController {

    private final NoteService noteService;

    @PostMapping("/create")
    public ResponseDto<NoteResponse> create(@RequestBody @Valid NoteRequest request) {
        return this.noteService.create(request);
    }

    @GetMapping("/get")
    public ResponseDto<NoteResponse> get(@RequestParam Integer id) {
        return this.noteService.get(id);
    }

    @PutMapping("/update")
    ResponseDto<NoteResponse> update(@RequestBody @Valid NoteRequest request, @RequestParam Integer id) {
        return this.noteService.update(request, id);
    }

    @DeleteMapping("/delet")
    public ResponseDto<NoteResponse> delete(@RequestParam Integer id) {
        return this.noteService.delete(id);
    }

    @GetMapping("/getAll")
    public ResponseDto<List<NoteResponse>> getAllType() {
        return this.noteService.getAllType();
    }

    @GetMapping("/pageable")
    public ResponseDto<Page<NoteResponse>> getPage(@RequestParam Integer page, @RequestParam Integer size) {
        return this.noteService.getPage(page, size);
    }
}
