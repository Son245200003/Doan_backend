package com.example.project_posgre.services.Impl;

import com.example.project_posgre.models.Room;
import com.example.project_posgre.repository.RoomRepository;
import com.example.project_posgre.services.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoomById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    @Override
    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room updateRoom(Long id, Room roomDetails) {
        Room room = getRoomById(id);
        if (room == null) return null;
        room.setRoomName(roomDetails.getRoomName());
        room.setRoomType(roomDetails.getRoomType());
        room.setDescription(roomDetails.getDescription());
        room.setBedCount(roomDetails.getBedCount());
        room.setPrice(roomDetails.getPrice());
        room.setStatus(roomDetails.getStatus());
        return roomRepository.save(room);
    }

    @Override
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}
