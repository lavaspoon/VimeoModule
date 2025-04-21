# Vimeo Upload Module (Spring Boot + FFMPEG)

This project is a Spring Boot-based backend module designed for uploading videos to Vimeo **with pre-processing and resolution control**.

## 🔧 Features

- Accepts user-uploaded videos via HTTP
- Uses **FFMPEG** to re-encode videos to **720p or lower** before uploading to Vimeo
- Generates a **Vimeo TUS upload session URL** for client-side chunked upload
- Supports extensible and clean architecture using service interfaces
- Easily integratable with a React frontend or other clients

## 🚀 How It Works

1. The client uploads a video to `/api/videos/upload`
2. The server stores it temporarily and encodes it to 720p using FFMPEG
3. A Vimeo TUS upload session is created and returned to the client
4. The client uploads the encoded file directly to Vimeo using `tus-js-client`
5. (Optional) You can notify the server or user upon upload completion

## 🧪 Requirements

- Java 17+
- FFMPEG must be installed and accessible via system path
- A Vimeo developer account with an access token

## 🔐 Configuration

Set your Vimeo access token in `application.yml`:

```yaml
vimeo:
  access-token: YOUR_VIMEO_ACCESS_TOKEN

