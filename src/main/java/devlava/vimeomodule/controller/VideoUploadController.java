package devlava.vimeomodule.controller;

import devlava.vimeomodule.dto.UploadUrlResponse;
import devlava.vimeomodule.service.VideoUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoUploadController {

    private final VideoUploadService videoUploadService;

    @PostMapping("/upload")
    public ResponseEntity<UploadUrlResponse> uploadAndPrepareVideo(@RequestParam("file") MultipartFile file) throws IOException, IOException, InterruptedException {
        UploadUrlResponse response = videoUploadService.handleUpload(file);
        return ResponseEntity.ok(response);
    }
}
