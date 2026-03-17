package people;

import interfaces.Diagnosable;

import java.util.HashSet;
import java.util.Set;

public class Doctor extends Staff implements Diagnosable {

    private String specialization;
    private Set<Patient> patients = new HashSet<>();


    public Doctor() {
        super();
    }

    public Doctor(String specialization) {
        super();
        this.specialization = specialization;
    }

    @Override
    public String getRole() {
        return "people.Doctor";
    }

    public Doctor(String staffId, String specialization) {
        super(staffId);
        this.specialization = specialization;
    }

    public Doctor(String firstName, String lastName, String phone, String address,
                  String staffId, String specialization) {
        super(firstName, lastName, phone, address, staffId);
        this.specialization = specialization;
    }

    @Override
    public String diagnose(Patient patient) {
        return "people.Doctor (" + specialization + ") diagnosed patient " +
                patient.getPatientId();
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}