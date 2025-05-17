package com.example.project_posgre.dtos.requests;

import com.example.project_posgre.models.Bed;
import lombok.Data;

@Data
public class BedRequest {
    private Bed.BedStatus status;
    private Long roomId;
}
