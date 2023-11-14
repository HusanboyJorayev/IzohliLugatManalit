package com.example.izohlilugatmanalit.service;

import com.example.izohlilugatmanalit.dto.ErrorDto;
import com.example.izohlilugatmanalit.dto.ResponseDto;
import com.example.izohlilugatmanalit.entity.Audio;
import com.example.izohlilugatmanalit.repository.AudioRepository;
import com.example.izohlilugatmanalit.request.AudioRequest;
import com.example.izohlilugatmanalit.response.AudioResponse;
import com.example.izohlilugatmanalit.service.mapper.AudioMapper;
import com.example.izohlilugatmanalit.service.validation.AudioValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AudioService {
    private final AudioMapper audioMapper;
    private final AudioRepository audioRepository;
    private final AudioValidation audioValidation;

    public ResponseDto<AudioResponse> upload(MultipartFile file) {
        try {
            return ResponseDto.<AudioResponse>builder()
                    .success(true)
                    .message("Ok")
                    .data(this.audioMapper.toDto(
                                    this.audioRepository.save(
                                            Audio.builder()
                                                    .path(saveAudio(file))
                                                    .createdAt(LocalDateTime.now())
                                                    .build()
                                    )
                            )
                    )
                    .build();

        } catch (Exception e) {
            return ResponseDto.<AudioResponse>builder()
                    .code(-1)
                    .message("Audio while uploading error")
                    .build();
        }
    }

    public ResponseDto<AudioResponse> download(Integer id) {
        try {
            Optional<Audio> optional = this.audioRepository.findByIdAndDeletedAtIsNull(id);
            if (optional.isEmpty()) {
                return ResponseDto.<AudioResponse>builder()
                        .code(-1)
                        .message("Audio is not found")
                        .build();
            }
            AudioResponse response = this.audioMapper.toDto(optional.get());
            response.setData(Files.readAllBytes(Path.of(optional.get().getPath())));

            return ResponseDto.<AudioResponse>builder()
                    .success(true)
                    .message("Ok")
                    .data(response)
                    .build();


        } catch (Exception e) {
            return ResponseDto.<AudioResponse>builder()
                    .code(-1)
                    .message("Audio while downloading error")
                    .build();
        }
    }

    public ResponseDto<AudioResponse> update(MultipartFile file, Integer id) {
        try {

            Optional<Audio> optional = this.audioRepository.findByIdAndDeletedAtIsNull(id);
            if (optional.isEmpty()) {
                return ResponseDto.<AudioResponse>builder()
                        .code(-1)
                        .message("Audio is not found")
                        .build();
            }
            Audio audio = optional.get();
            AudioResponse audioResponse = AudioResponse.builder()
                    .path(saveAudio(file))
                    .updatedAt(LocalDateTime.now())
                    .build();
            this.audioMapper.update(audio, audioResponse);
            Files.delete(Path.of(optional.get().getPath()));
            return ResponseDto.<AudioResponse>builder()
                    .success(true)
                    .message("Ok")
                    .data(this.audioMapper.toDto(
                            this.audioRepository.save(
                                    audio)))
                    .build();

        } catch (Exception e) {
            return ResponseDto.<AudioResponse>builder()
                    .code(-1)
                    .message("Audio while updating error")
                    .build();
        }
    }

    public ResponseDto<AudioResponse> delete(Integer id) {

        try {

            Optional<Audio> optional = this.audioRepository.findByIdAndDeletedAtIsNull(id);
            if (optional.isEmpty()) {
                return ResponseDto.<AudioResponse>builder()
                        .code(-1)
                        .message("Audio is not found")
                        .build();
            }
            File file = new File(optional.get().getPath());
            if (file.exists()) {
                file.delete();
            }
            return ResponseDto.<AudioResponse>builder()
                    .success(true)
                    .message("Ok")
                    .data(this.audioMapper.toDto(
                            this.audioRepository.save(
                                    Audio.builder()
                                            .deletedAt(LocalDateTime.now())
                                            .build())))
                    .build();

        } catch (Exception e) {
            return ResponseDto.<AudioResponse>builder()
                    .code(-1)
                    .message("Audio while updating error")
                    .build();
        }
    }

    private String saveAudio(MultipartFile file) throws IOException {
        String folder = String.format("/upload/%s", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        File filePath = new File(folder);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        String fileName = String.format("%s/%s", folder, UUID.randomUUID());
        Files.copy(file.getInputStream(), Path.of(fileName));
        return fileName;
    }
}
