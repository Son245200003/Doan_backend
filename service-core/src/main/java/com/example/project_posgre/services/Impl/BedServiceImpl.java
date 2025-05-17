package com.example.project_posgre.services.Impl;

import com.example.project_posgre.dtos.requests.BedRequest;
import com.example.project_posgre.models.Bed;
import com.example.project_posgre.models.Room;
import com.example.project_posgre.repository.BedRepository;
import com.example.project_posgre.repository.RoomRepository;
import com.example.project_posgre.services.BedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BedServiceImpl implements BedService {
    private final BedRepository bedRepository;
    private final RoomRepository roomRepository;

    @Override
    public List<Bed> getAllBeds() {
        return bedRepository.findAll();
    }

    @Override
    public List<Bed> getBedsByRoomId(Long roomId) {
        return bedRepository.findByRoomId(roomId);
    }

    @Override
    public Bed getBedById(Long id) {
        return bedRepository.findById(id).orElse(null);
    }

    @Override
    public Bed createBed(BedRequest request) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));
        Bed bed = new Bed();
        bed.setRoom(room);
        bed.setStatus(request.getStatus());
        return bedRepository.save(bed);
    }

    @Override
    public Bed updateBed(Long id, BedRequest request) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));
        Bed bed = getBedById(id);
        if (bed == null) return null;
        bed.setStatus(request.getStatus());
        bed.setRoom(room);
        return bedRepository.save(bed);
    }

    @Override
    public void deleteBed(Long id) {
        bedRepository.deleteById(id);
    }
}
