package devlava.vimeomodule.service;

import devlava.vimeomodule.dto.UploadUrlResponse;
import devlava.vimeomodule.util.VimeoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class VideoUploadService {

    private final FfmpegService ffmpegService;
    private final VimeoClient vimeoClient;

    public UploadUrlResponse handleUpload(MultipartFile file) throws IOException, InterruptedException {
        // 1. 임시 파일 저장
        Path tempInput = Files.createTempFile("upload_", ".mp4");
        file.transferTo(tempInput.toFile());

        // 2. 720p로 인코딩
        Path encodedFile = ffmpegService.encodeTo720p(tempInput);

        // 3. Vimeo 업로드 세션 생성
        String uploadUrl = vimeoClient.createUploadSession(Files.size(encodedFile));

        // 4. (선택) encodedFile 삭제: 클라이언트 업로드 완료 후 삭제 추천

        return new UploadUrlResponse(uploadUrl);
    }
}
