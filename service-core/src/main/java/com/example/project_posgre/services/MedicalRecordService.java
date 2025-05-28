package com.example.project_posgre.services;

import com.example.project_posgre.models.MedicalRecord;

import java.util.List;

public interface MedicalRecordService {
    List<MedicalRecord> getAllRecords();
    MedicalRecord getRecordById(Long id);
    MedicalRecord createRecord(MedicalRecord record);
    MedicalRecord updateRecord(Long id, MedicalRecord record);
    void deleteRecord(Long id);
    public List<MedicalRecord> getMedicalRecordByPatient(long id);

    }