package devlava.vimeomodule.util;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class VimeoClient {

    @Value("${vimeo.access-token}")
    private String accessToken;

    public String createUploadSession(long fileSize) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        String requestBody = """
            {
              "upload": {
                "approach": "tus",
                "size": %d
              }
            }
        """.formatted(fileSize);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.vimeo.com/me/videos"))
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new RuntimeException("Vimeo 세션 생성 실패: " + response.body());
        }

        // TUS upload 링크 추출
        return new JSONObject(response.body())
                .getJSONObject("upload")
                .getString("upload_link");
    }
}
