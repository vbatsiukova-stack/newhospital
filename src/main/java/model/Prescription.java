package model;

import people.Doctor;
import people.Patient;

import java.util.Date;

public class Prescription {
    private Doctor doctor;
    private Patient patient;
    private Medication[] medications;
    private Date prescriptionDate;

    public Prescription() {
    }

    public Prescription(Doctor doctor, Patient patient, Medication[] medications, Date prescriptionDate) {
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

    public Medication[] getMedications() {
        return medications;
    }

    public void setMedications(Medication[] medications) {
        this.medications = medications;
    }

    public Date getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(Date prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }
}
