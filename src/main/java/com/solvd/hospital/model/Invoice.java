package com.solvd.hospital.model;

import com.solvd.hospital.interfaces.Identifiable;
import com.solvd.hospital.interfaces.Payable;
import com.solvd.hospital.people.Patient;
import com.solvd.hospital.exceptions.PaymentFailedException;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Invoice implements Payable, Identifiable {

    private String invoiceNumber;
    private Patient patient;
    private Date invoiceDate;
    private double totalAmount;
    private List<LabTest> labTests;
    private List<Appointment> appointments;

    public Invoice() {
    }

    public Invoice(String invoiceNumber, Patient patient, Date invoiceDate) {
        this.invoiceNumber = invoiceNumber;
        this.patient = patient;
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<LabTest> getLabTests() {
        return labTests;
    }

    public void setLabTests(List<LabTest> labTests) {
        this.labTests = labTests;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceNumber='" + invoiceNumber + '\'' +
                ", patient=" + patient +
                ", invoiceDate=" + invoiceDate +
                ", totalAmount=" + totalAmount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(invoiceNumber, invoice.invoiceNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceNumber);
    }

    @Override
    public double calculateTotal() {
        return totalAmount;
    }

    public double calculateTotal(double insuranceCoveragePercent) {
        double discount = totalAmount * (insuranceCoveragePercent / 100.0);
        return totalAmount - discount;
    }

    @Override
    public String getId() {
        return invoiceNumber;
    }

    @Override
    public void pay(double amount) throws PaymentFailedException {
        if (amount <= 0) {
            throw new PaymentFailedException("Payment amount must be positive");
        }

        if (amount < totalAmount) {
            throw new PaymentFailedException(
                    "Not enough money. Required: " + totalAmount + ", provided: " + amount
            );
        }
    }
}