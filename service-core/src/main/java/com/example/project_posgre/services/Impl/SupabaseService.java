package com.example.project_posgre.services.Impl;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class SupabaseService {
    private static final String SUPABASE_URL = "https://usjjfuintvzzljfauojj.supabase.co";
    private static final String BUCKET = "luufile";
    private static final String SERVICE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InVzampmdWludHZ6emxqZmF1b2pqIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTc0ODMxODUzOCwiZXhwIjoyMDYzODk0NTM4fQ.ub-xAyCrjsdJCRlQjgR0OCa1-zczdkK565ikBbmbqAQ"; // Lấy ở Supabase Settings > API > service_role

    public String uploadToSupabase(MultipartFile file) throws IOException {
        String objectPath = "uploads/" + file.getOriginalFilename(); // thư mục uploads/, hoặc tự đặt path khác

        String uploadUrl = String.format("%s/storage/v1/object/%s/%s", SUPABASE_URL, BUCKET, objectPath);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(SERVICE_KEY);
        headers.setContentType(MediaType.parseMediaType(file.getContentType()));

        HttpEntity<byte[]> entity = new HttpEntity<>(file.getBytes(), headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(uploadUrl, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            // Nếu bucket public thì link truy cập sẽ là: 
            return String.format("%s/storage/v1/object/public/%s/%s", SUPABASE_URL, BUCKET, objectPath);
        } else {
            throw new RuntimeException("Upload to Supabase failed: " + response.getStatusCode() + " - " + response.getBody());
        }
    }
}
