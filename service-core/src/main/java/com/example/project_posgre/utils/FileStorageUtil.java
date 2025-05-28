package com.example.project_posgre.utils;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

public class FileStorageUtil {

    // Trả về tên file (hoặc trả về URL tuỳ ý bạn)
    public static String storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        Path destination = uploadDir.resolve(uniqueFilename);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        // Trả về tên file (có thể trả cả đường dẫn nếu cần)
        return uniqueFilename;
    }

    // Nếu muốn trả về URL
    public static String getFileUrl(String filename) {
        // Nếu server static resource (uploads), trả: "/uploads/unique_file.jpg"
        return "/uploads/" + filename;
    }
}
