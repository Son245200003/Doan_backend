package com.example.project_posgre.controllers;

import com.example.project_posgre.dtos.requests.StaffRequestDTO;
import com.example.project_posgre.models.Attachment;
import com.example.project_posgre.models.Staff;
import com.example.project_posgre.repository.AttachmentRepository;
import com.example.project_posgre.services.Impl.SupabaseService;
import com.example.project_posgre.services.StaffService;
import com.example.project_posgre.utils.FileStorageUtil;
import org.json.HTTP;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    private final StaffService staffService;
    private final SupabaseService supabaseService;
    private final AttachmentRepository attachmentRepository;

    public StaffController(StaffService staffService, SupabaseService supabaseService, AttachmentRepository attachmentRepository) {
        this.staffService = staffService;
        this.supabaseService = supabaseService;
        this.attachmentRepository = attachmentRepository;
    }

    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<Staff> create(@RequestBody StaffRequestDTO dto) {
        return ResponseEntity.ok(staffService.createStaff(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Staff> update(@PathVariable Long id, @RequestBody StaffRequestDTO dto) {
        return ResponseEntity.ok(staffService.updateStaff(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Staff> getById(@PathVariable Long id) {
        return ResponseEntity.ok(staffService.getStaffById(id));
    }

    @GetMapping
    public ResponseEntity<List<Staff>> getAll() {
        return ResponseEntity.ok(staffService.getAllStaff());
    }
    @GetMapping("/doctor")
    public ResponseEntity<List<Staff>> getAllDoctor() {
        return ResponseEntity.ok(staffService.getAllDoctor());
    }
    @PostMapping("/{staffId}")
    public ResponseEntity<?> uploadAttachment(
            @PathVariable Long staffId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        return ResponseEntity.ok(staffService.uploadAttachment(staffId, file));
    }
    @PostMapping("/upload/{staffId}")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @PathVariable Long staffId) throws IOException {
        return ResponseEntity.ok(staffService.uploadAttachment(staffId, file));
    }
    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<?> viewFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get("uploads").resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream"; // fallback
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + resource.getFilename() + "\"")
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/attachments/{attachmentId}")
    public ResponseEntity<?> deleteAtachment( @PathVariable Long attachmentId) throws IOException {
        attachmentRepository.deleteById(attachmentId);
        return ResponseEntity.ok().build();
    }
}
