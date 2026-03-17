package people;

import interfaces.Identifiable;
import model.Insurance;

public class Patient extends Person implements Identifiable {

    private String patientId;
    private int age;
    private Insurance insurance;

    public Patient() {
        super();
    }

    @Override
    public String getRole() {
        return "people.Patient";
    }

    public Patient(String firstName, String lastName, String phone, String address,
                   String patientId, int age) {
        super(firstName, lastName, phone, address);
        this.patientId = patientId;
        this.age = age;
    }

    public Patient(String firstName, String lastName, String phone, String address,
                   String patientId, int age, Insurance insurance) {
        super(firstName, lastName, phone, address);
        this.patientId = patientId;
        this.age = age;
        this.insurance = insurance;
    }

    @Override
    public String getId() {
        return patientId;
    }


    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }


    @Override
    public String toString() {
        return "people.Patient{" +
                "id='" + patientId + '\'' +
                ", name='" + getFirstName() + " " + getLastName() + '\'' +
                ", age=" + age +
                '}';
    }
}
