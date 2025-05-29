package com.example.project_posgre.services.Impl;

import com.example.project_posgre.models.Admission;
import com.example.project_posgre.repository.AdmissionRepository;
import com.example.project_posgre.services.AdmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdmissionServiceImpl implements AdmissionService {

    @Autowired
    private AdmissionRepository admissionRepository;

    @Override
    public List<Admission> getAllAdmissions() {
        return admissionRepository.findAll();
    }

    @Override
    public Optional<Admission> getAdmissionById(Long id) {
        return admissionRepository.findById(id);
    }

    @Override
    public Admission createAdmission(Admission admission) {
        admission.setStatus(Admission.AdmissionStatus.TREATING);
        admission.setStatusPay(Admission.Status.UNPAID);
        return admissionRepository.save(admission);
    }

    @Override
    public Admission updateAdmission(Long id, Admission admissionDetails) {
        return admissionRepository.findById(id).map(admission -> {
            admission.setPatient(admissionDetails.getPatient());
            admission.setBed(admissionDetails.getBed());
            admission.setDoctor(admissionDetails.getDoctor());
            admission.setAdmissionDate(admissionDetails.getAdmissionDate());
            admission.setExpectedDischargeDate(admissionDetails.getExpectedDischargeDate());
            admission.setDischargeDate(admissionDetails.getDischargeDate());
            admission.setDiagnosis(admissionDetails.getDiagnosis());
            admission.setCondition(admissionDetails.getCondition());
            admission.setNotes(admissionDetails.getNotes());
            admission.setStatus(admissionDetails.getStatus());
            return admissionRepository.save(admission);
        }).orElseThrow(() -> new RuntimeException("Admission not found with id " + id));
    }

    @Override
    public void deleteAdmission(Long id) {
        admissionRepository.deleteById(id);
    }
    public List<Admission> findByPatientId(Long patientId){
        return admissionRepository.findByPatientId(patientId);
    }
}