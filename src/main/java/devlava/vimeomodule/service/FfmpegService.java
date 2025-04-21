package devlava.vimeomodule.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FfmpegService {

    public Path encodeTo720p(Path inputFile) throws IOException {
        Path outputFile = Files.createTempFile("encoded_", ".mp4");

        ProcessBuilder builder = new ProcessBuilder(
                "ffmpeg", "-i", inputFile.toString(),
                "-vf", "scale=-2:720",  // preserve aspect ratio
                "-c:a", "aac",
                "-c:v", "libx264",
                "-preset", "fast",
                "-y",
                outputFile.toString()
        );
        builder.redirectErrorStream(true);
        Process process = builder.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            while (reader.readLine() != null) {}  // log 생략
        }

        try {
            if (process.waitFor() != 0) throw new RuntimeException("FFMPEG 인코딩 실패");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return outputFile;
    }
}
