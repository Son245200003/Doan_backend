package com.example.project_posgre.dtos.reponses;

import com.example.project_posgre.models.Room;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data

public class UnpaidInvoiceDTO {
    @Data
    @AllArgsConstructor
    public static class UnpaidServiceDTO {
        private Long id;
        private String name;
        private BigDecimal price;
        // constructor, getters, setters
    }

    @Data
    @AllArgsConstructor
    public static class UnpaidMedicineDTO {
        private Long prescriptionId;
        private String prescriptionDate;
        private Long medicineId;
        private String medicineName;
        private int quantity;
        private BigDecimal unitPrice;
        private BigDecimal totalPrice;
        // constructor, getters, setters
    }
    @Data
    @AllArgsConstructor
    public static class UnpaidRoomDTO {
        private Long admissionId;
        private String bedName;
        private String roomName;
        private int days;
        private BigDecimal unitPrice;
        private BigDecimal totalPrice;
        private String fromDate;
        private String toDate;
        private Room.RoomType roomType;
        // constructor, getters, setters
    }
    @Data
    @AllArgsConstructor
    public static class UnpaidAllTypeDTO {
        private List<UnpaidServiceDTO> services;
        private BigDecimal totalService;

        private List<UnpaidMedicineDTO> medicines;
        private BigDecimal totalMedicine;

        private List<UnpaidRoomDTO> rooms;
        private BigDecimal totalRoom;

        private BigDecimal totalAmount;
        // constructor, getters, setters
    }

}
