package com.example.project_posgre.services.Impl;

import com.example.project_posgre.dtos.requests.InvoiceRequestDTO;
import com.example.project_posgre.models.Invoice;
import com.example.project_posgre.models.InvoiceDetail;
import com.example.project_posgre.models.Patient;
import com.example.project_posgre.repository.*;
import com.example.project_posgre.services.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final UserRepository userRepository;
    private final InvoiceDetailRepository invoiceDetailRepository;
    private final ServiceRepository serviceRepository;
    private final MedicineRepository medicineRepository;
    private final AdmissionRepository admissionRepository;
    private final PatientServiceRepository patientServiceRepository;

    @Override
    public Invoice createInvoice(InvoiceRequestDTO dto) {
        Invoice invoice = new Invoice();
        invoice.setPatient(patientRepository.findById(dto.getPatientId()).orElseThrow());
        invoice.setInvoiceDate(dto.getInvoiceDate());
        invoice.setTotalAmount(dto.getTotalAmount());
        invoice.setDiscount(dto.getDiscount());
        invoice.setFinalAmount(dto.getFinalAmount());
        invoice.setPaid(dto.getPaid());
        invoice.setPaymentMethod(Invoice.PaymentMethod.valueOf(dto.getPaymentMethod()));
        invoice.setStatus(Invoice.InvoiceStatus.valueOf(dto.getStatus()));
        invoice.setNotes(dto.getNotes());

// convert InvoiceDetailRequestDTO
        List<InvoiceDetail> details = dto.getInvoiceDetails().stream().map(detailDTO -> {
            InvoiceDetail detail = new InvoiceDetail();
            detail.setInvoice(invoice); // gắn lại parent
            if (detailDTO.getServiceId() != null) {
                detail.setService(serviceRepository.findById(detailDTO.getServiceId()).orElse(null));
            }
            if (detailDTO.getMedicineId() != null) {
                detail.setMedicine(medicineRepository.findById(detailDTO.getMedicineId()).orElse(null));
            }
            detail.setQuantity(detailDTO.getQuantity());
            detail.setUnitPrice(detailDTO.getUnitPrice());
            detail.setAmount(detailDTO.getAmount());
            detail.setNotes(detailDTO.getNotes());
            return detail;
        }).toList();

        invoice.setInvoiceDetails(details);

        admissionRepository.markAllAdmissionsPaidByPatient(dto.getPatientId());
        prescriptionRepository.markAllPrescriptionsPaidByPatient(dto.getPatientId());
        patientServiceRepository.markAllPatientServicesPaidByPatient(dto.getPatientId());
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice updateInvoice(Long id, InvoiceRequestDTO dto) {
        Invoice existing = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));

        // Cập nhật các field
        existing.setPatient(patientRepository.findById(dto.getPatientId()).orElseThrow());
        existing.setInvoiceDate(dto.getInvoiceDate());
        existing.setTotalAmount(dto.getTotalAmount());
        existing.setDiscount(dto.getDiscount());
        existing.setFinalAmount(dto.getFinalAmount());
        existing.setPaid(dto.getPaid());
        existing.setPaymentMethod(Invoice.PaymentMethod.valueOf(dto.getPaymentMethod()));
        existing.setStatus(Invoice.InvoiceStatus.valueOf(dto.getStatus()));
        existing.setNotes(dto.getNotes());

        // Cập nhật createdBy nếu cần (hoặc có thể không cho sửa)
        // Xóa danh sách cũ (nếu cascade = ALL sẽ tự xóa trong DB)
        existing.getInvoiceDetails().clear();

        // Tạo danh sách mới
        List<InvoiceDetail> updatedDetails = dto.getInvoiceDetails().stream().map(detailDTO -> {
            InvoiceDetail detail = new InvoiceDetail();
            detail.setInvoice(existing);
            if (detailDTO.getServiceId() != null) {
                detail.setService(serviceRepository.findById(detailDTO.getServiceId()).orElse(null));
            }
            if (detailDTO.getMedicineId() != null) {
                detail.setMedicine(medicineRepository.findById(detailDTO.getMedicineId()).orElse(null));
            }
            detail.setQuantity(detailDTO.getQuantity());
            detail.setUnitPrice(detailDTO.getUnitPrice());
            detail.setAmount(detailDTO.getAmount());
            detail.setNotes(detailDTO.getNotes());
            return detail;
        }).toList();

        existing.setInvoiceDetails(updatedDetails);
        existing.setUpdatedAt(LocalDateTime.now());

        return invoiceRepository.save(existing);
    }


    @Override
    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }

    @Override
    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAllByOrderByIdAsc();
    }

    @Override
    public List<Invoice> getInvoicesByPatient(Long idPatient) {
        Patient patient = patientRepository.findById(idPatient).orElse(null);
        return invoiceRepository.findAllByPatientOrderByIdAsc(patient);
    }
}
