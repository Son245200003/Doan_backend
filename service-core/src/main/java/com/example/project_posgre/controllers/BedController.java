package com.example.project_posgre.controllers;


import com.example.project_posgre.dtos.reponses.BedsDto;
import com.example.project_posgre.dtos.requests.BedRequest;
import com.example.project_posgre.models.Bed;
import com.example.project_posgre.repository.BedRepository;
import com.example.project_posgre.services.BedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/beds")
@RequiredArgsConstructor
public class BedController {
    private final BedService bedService;
    private final BedRepository bedRepository;

    @GetMapping
    public List<Bed> getAllBeds() {
        return bedService.getAllBeds();
    }
    @GetMapping("/available")
    public List<BedsDto> getAvailableBeds() {
        List<Bed> beds = bedRepository.findByStatus(Bed.BedStatus.AVAILABLE);
        return beds.stream()
                .map(BedsDto::mapFromBed)
                .collect(Collectors.toList());
    }
    @GetMapping("/room/{roomId}")
    public List<Bed> getBedsByRoomId(@PathVariable Long roomId) {
        return bedService.getBedsByRoomId(roomId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bed> getBedById(@PathVariable Long id) {
        Bed bed = bedService.getBedById(id);
        return bed != null ? ResponseEntity.ok(bed) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Bed createBed(@RequestBody BedRequest bed) {
        return bedService.createBed(bed);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bed> updateBed(@PathVariable Long id, @RequestBody BedRequest bed) {
        Bed updated = bedService.updateBed(id, bed);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBed(@PathVariable Long id) {
        bedService.deleteBed(id);
        return ResponseEntity.noContent().build();
    }
}