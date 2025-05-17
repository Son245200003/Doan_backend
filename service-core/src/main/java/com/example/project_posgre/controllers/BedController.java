package com.example.project_posgre.controllers;


import com.example.project_posgre.dtos.requests.BedRequest;
import com.example.project_posgre.models.Bed;
import com.example.project_posgre.services.BedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beds")
public class BedController {
    private final BedService bedService;

    public BedController(BedService bedService) {
        this.bedService = bedService;
    }

    @GetMapping
    public List<Bed> getAllBeds() {
        return bedService.getAllBeds();
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