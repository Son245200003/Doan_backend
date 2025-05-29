package com.example.project_posgre.controllers;
import com.example.project_posgre.dtos.reponses.UnpaidInvoiceDTO;
import com.example.project_posgre.dtos.requests.InvoiceRequestDTO;
import com.example.project_posgre.models.*;
import com.example.project_posgre.repository.AdmissionRepository;
import com.example.project_posgre.repository.PatientServiceRepository;
import com.example.project_posgre.repository.PrescriptionRepository;
import com.example.project_posgre.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private PatientServiceRepository patientServiceRepository;
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private AdmissionRepository admissionRepository;

    @PostMapping
    public Invoice createInvoice(@RequestBody InvoiceRequestDTO invoice) {
        return invoiceService.createInvoice(invoice);
    }

    @PutMapping("/{id}")
    public Invoice updateInvoice(@PathVariable Long id, @RequestBody InvoiceRequestDTO invoice) {
        return invoiceService.updateInvoice(id, invoice);
    }

    @DeleteMapping("/{id}")
    public void deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
    }
    @GetMapping("/{id}")
    public Invoice getInvoice(@PathVariable Long id) {
        return invoiceService.getInvoiceById(id);
    }
    @GetMapping("/patient")
    public List<Invoice> getInvoiceByPatient(@RequestParam Long patientId) {
        return invoiceService.getInvoicesByPatient(patientId);
    }

    @GetMapping
    public List<Invoice> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }
    @GetMapping("/pending/group-detail")
    public UnpaidInvoiceDTO.UnpaidAllTypeDTO getUnpaidGroupDetail(@RequestParam Long patientId) {
        // 1. Dịch vụ chưa thanh toán
        List<PatientService> unpaidServices = patientServiceRepository.findByPatientIdAndStatus(patientId, PatientService.Status.UNPAID);
        List<UnpaidInvoiceDTO.UnpaidServiceDTO> services = unpaidServices.stream().map(ps -> {
            Service s = ps.getService();
            return new UnpaidInvoiceDTO.UnpaidServiceDTO(
                    s.getId(),
                    s.getServiceName(),
                    s.getPrice() != null ? s.getPrice() : BigDecimal.ZERO
            );
        }).toList();

        BigDecimal totalService = services.stream()
                .map(UnpaidInvoiceDTO.UnpaidServiceDTO::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 2. Thuốc trong các đơn thuốc chưa thanh toán
        List<Prescription> unpaidPres = prescriptionRepository.findByPatientIdAndStatus(patientId, Prescription.Status.UNPAID);
        List<UnpaidInvoiceDTO.UnpaidMedicineDTO> medicines = new ArrayList<>();
        for (Prescription p : unpaidPres) {
            String presDate = new java.text.SimpleDateFormat("dd/MM/yyyy").format(p.getPrescriptionDate());
            if (p.getDetails() != null) {
                for (PrescriptionDetail d : p.getDetails()) {
                    Medicine m = d.getMedicine();
                    BigDecimal price = m.getPrice() != null ? m.getPrice() : BigDecimal.ZERO;
                    int qty = d.getQuantity() != null ? d.getQuantity() : 0;
                    BigDecimal total = price.multiply(BigDecimal.valueOf(qty));
                    medicines.add(new UnpaidInvoiceDTO.UnpaidMedicineDTO(
                            p.getId(),
                            presDate,
                            m.getId(),
                            m.getMedicineName(),
                            qty,
                            price,
                            total
                    ));
                }
            }
        }
        BigDecimal totalMedicine = medicines.stream()
                .map(UnpaidInvoiceDTO.UnpaidMedicineDTO::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 3. Phòng/giường chưa thanh toán
        List<Admission> unpaidAdmissions = admissionRepository.findByPatientIdAndStatusPay(patientId, Admission.Status.UNPAID);
        List<UnpaidInvoiceDTO.UnpaidRoomDTO> rooms = new ArrayList<>();
        for (Admission a : unpaidAdmissions) {
            Bed bed = a.getBed();
            BigDecimal unitPrice = bed.getRoom().getPrice();
            Date from = a.getAdmissionDate();
            Date to = a.getDischargeDate() != null
                    ? java.sql.Date.valueOf(a.getDischargeDate())
                    : new java.util.Date();
            long days = (to.getTime() - from.getTime()) / (1000 * 60 * 60 * 24) + 1;
            days = Math.max(days, 1);
            BigDecimal totalRoomPrice = unitPrice.multiply(BigDecimal.valueOf(days));
            rooms.add(new UnpaidInvoiceDTO.UnpaidRoomDTO(
                    a.getId(),
                    "Giường " + bed.getId(),
                    "Phòng " + bed.getRoom().getRoomName(),
                    (int) days,
                    unitPrice,
                    totalRoomPrice,
                    new java.text.SimpleDateFormat("dd/MM/yyyy").format(from),
                    new java.text.SimpleDateFormat("dd/MM/yyyy").format(to),
                    a.getBed().getRoom().getRoomType()
            ));
        }
        BigDecimal totalRoom = rooms.stream()
                .map(UnpaidInvoiceDTO.UnpaidRoomDTO::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalAmount = totalService.add(totalMedicine).add(totalRoom);

        return new UnpaidInvoiceDTO.UnpaidAllTypeDTO(services, totalService, medicines, totalMedicine, rooms, totalRoom, totalAmount);
    }


}

