package com.example.project_posgre.services.Impl;

import com.example.project_posgre.models.MedicalRecord;
import com.example.project_posgre.repository.MedicalRecordRepository;
import com.example.project_posgre.services.MedicalRecordService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordServiceImpl(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    @Override
    public List<MedicalRecord> getAllRecords() {
        return medicalRecordRepository.findAllByOrderByIdAsc();
    }
    @Override
    public List<MedicalRecord> getMedicalRecordByPatient(long id) {
        return medicalRecordRepository.findByPatientId(id);
    }

    @Override
    public MedicalRecord getRecordById(Long id) {
        return medicalRecordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medical record not found with id: " + id));
    }

    @Override
    public MedicalRecord createRecord(MedicalRecord record) {
        return medicalRecordRepository.save(record);
    }

    @Override
    public MedicalRecord updateRecord(Long id, MedicalRecord recordDetails) {
        MedicalRecord record = getRecordById(id);

        record.setPatient(recordDetails.getPatient());
        record.setDoctor(recordDetails.getDoctor());
        record.setAppointment(recordDetails.getAppointment());
        record.setExaminationDate(recordDetails.getExaminationDate());
        record.setDiagnosis(recordDetails.getDiagnosis());
        record.setSymptoms(recordDetails.getSymptoms());
        record.setNotes(recordDetails.getNotes());
        record.setPrescription(recordDetails.getPrescription());
        record.setResult(recordDetails.getResult());

        return medicalRecordRepository.save(record);
    }

    @Override
    public void deleteRecord(Long id) {
        if (!medicalRecordRepository.existsById(id)) {
            throw new EntityNotFoundException("Medical record not found with id: " + id);
        }
        medicalRecordRepository.deleteById(id);
    }
}