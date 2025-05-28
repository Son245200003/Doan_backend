package com.example.project_posgre.services.Impl;

import com.example.project_posgre.dtos.requests.BedRequest;
import com.example.project_posgre.exception.InvalidParamException;
import com.example.project_posgre.exception.NotFoundException;
import com.example.project_posgre.models.Bed;
import com.example.project_posgre.models.Room;
import com.example.project_posgre.repository.BedRepository;
import com.example.project_posgre.repository.RoomRepository;
import com.example.project_posgre.services.BedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BedServiceImpl implements BedService {
    private final BedRepository bedRepository;
    private final RoomRepository roomRepository;

    @Override
    public List<Bed> getAllBeds() {
        return bedRepository.findAllByOrderByIdAsc();
    }

    @Override
    public List<Bed> getBedsByRoomId(Long roomId) {
        return bedRepository.findByRoomId(roomId, Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Bed getBedById(Long id) {
        return bedRepository.findById(id).orElse(null);
    }

    @Override
    public Bed createBed(BedRequest request) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));
        long currentBeds = bedRepository.countByRoomId(room.getId());
        if (currentBeds >= room.getBedCount()) {
            throw new InvalidParamException("Đã thêm đủ giường cho phòng này");
        }
        Bed bed = new Bed();
        bed.setRoom(room);
        bed.setStatus(request.getStatus());
        return bedRepository.save(bed);
    }

    @Override
    public Bed updateBed(Long id, BedRequest request) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));
        Bed bed = bedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bed not found"));
        bed.setStatus(request.getStatus());
        bed.setRoom(room);
        return bedRepository.saveAndFlush(bed);
    }

    @Override
    public void deleteBed(Long id) {
        bedRepository.deleteById(id);
    }
}
