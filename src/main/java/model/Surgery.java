package model;

import people.Doctor;
import people.Patient;

import java.util.Date;

public class Surgery {
    private String surgeryType;
    private Patient patient;
    private Doctor surgeon;
    private Date surgeryDate;

    public Surgery() {
    }

    public Surgery(String surgeryType, Patient patient, Doctor surgeon, Date surgeryDate) {
        this.surgeryType = surgeryType;
        this.patient = patient;
        this.surgeon = surgeon;
        this.surgeryDate = surgeryDate;
    }

    public String getSurgeryType() {
        return surgeryType;
    }

    public void setSurgeryType(String surgeryType) {
        this.surgeryType = surgeryType;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getSurgeon() {
        return surgeon;
    }

    public void setSurgeon(Doctor surgeon) {
        this.surgeon = surgeon;
    }

    public Date getSurgeryDate() {
        return surgeryDate;
    }

    public void setSurgeryDate(Date surgeryDate) {
        this.surgeryDate = surgeryDate;
    }
}