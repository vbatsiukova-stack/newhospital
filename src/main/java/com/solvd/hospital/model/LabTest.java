package com.solvd.hospital.model;

import com.solvd.hospital.people.Doctor;
import com.solvd.hospital.people.Patient;
import com.solvd.hospital.exceptions.LabTestNotReadyException;

public class LabTest {
    private String testName;
    private Patient patient;
    private Doctor orderedBy;
    private String result;

    public LabTest() {
    }

    public LabTest(String testName, Patient patient, Doctor orderedBy, String result) {
        this.testName = testName;
        this.patient = patient;
        this.orderedBy = orderedBy;
        this.result = result;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(Doctor orderedBy) {
        this.orderedBy = orderedBy;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultOrThrow() throws LabTestNotReadyException {
        if (result == null || result.isEmpty()) {
            throw new LabTestNotReadyException(
                    "Lab test '" + testName + "' is not ready yet"
            );
        }
        return result;
    }
}
