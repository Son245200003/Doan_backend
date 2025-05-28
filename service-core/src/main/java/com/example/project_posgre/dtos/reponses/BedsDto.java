package com.example.project_posgre.dtos.reponses;

import com.example.project_posgre.models.Bed;
import com.example.project_posgre.models.Room;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BedsDto {
    private Long id;
    private Room room;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Static mapping function
    public static BedsDto mapFromBed(Bed bed) {
        BedsDto dto = new BedsDto();
        dto.setId(bed.getId());
        dto.setStatus(bed.getStatus().name());
        dto.setCreatedAt(bed.getCreatedAt());
        dto.setUpdatedAt(bed.getUpdatedAt());
        dto.setRoom(bed.getRoom());
        return dto;
    }
}
