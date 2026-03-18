package com.solvd.hospital.model;

import com.solvd.hospital.people.Doctor;
import com.solvd.hospital.people.Patient;

import java.util.Date;
import java.util.List;

public class Prescription {
    private Doctor doctor;
    private Patient patient;
    private List<Medication> medications;
    private Date prescriptionDate;

    public Prescription() {
    }

    public Prescription(Doctor doctor, Patient patient, List<Medication> medications, Date prescriptionDate) {
        this.doctor = doctor;
        this.patient = patient;
        this.medications = medications;
        this.prescriptionDate = prescriptionDate;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public Date getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(Date prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }
}