package com.example.project_posgre.services;

import java.util.List;

import com.example.project_posgre.dtos.requests.BedRequest;
import com.example.project_posgre.models.Bed;

public interface BedService {
    List<Bed> getAllBeds();
    List<Bed> getBedsByRoomId(Long roomId);
    Bed getBedById(Long id);
    Bed createBed(BedRequest bed);
    Bed updateBed(Long id, BedRequest bedRequest);
    void deleteBed(Long id);
}