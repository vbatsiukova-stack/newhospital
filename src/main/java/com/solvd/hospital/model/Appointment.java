package com.solvd.hospital.model;

import com.solvd.hospital.enums.AppointmentStatus;
import com.solvd.hospital.interfaces.Schedulable;
import com.solvd.hospital.people.Doctor;
import com.solvd.hospital.people.Patient;

import java.util.Date;
import java.util.Objects;

public final class Appointment implements Schedulable {

    private Date appointmentTime;
    private Patient patient;
    private Doctor doctor;
    private Room room;
    private String reason;
    private TimeSlot slot;
    private AppointmentStatus status = AppointmentStatus.SCHEDULED;

    private static final long MINUTE = 60_000L;

    public Appointment() {
    }

    public Appointment(Date appointmentTime, Patient patient, Doctor doctor) {
        this.appointmentTime = appointmentTime;
        this.patient = patient;
        this.doctor = doctor;

        this.status = AppointmentStatus.SCHEDULED;
        this.slot = defaultSlot(appointmentTime);
    }

    public Appointment(Date appointmentTime, Patient patient, Doctor doctor, Room room) {
        this.appointmentTime = appointmentTime;
        this.patient = patient;
        this.doctor = doctor;
        this.room = room;

        this.status = AppointmentStatus.SCHEDULED;
        this.slot = defaultSlot(appointmentTime);
    }

    public Appointment(Date appointmentTime, Patient patient, Doctor doctor, String reason) {
        this.appointmentTime = appointmentTime;
        this.patient = patient;
        this.doctor = doctor;
        this.reason = reason;

        this.status = AppointmentStatus.SCHEDULED;
        this.slot = defaultSlot(appointmentTime);
    }

    public Appointment(Date appointmentTime, Patient patient, Doctor doctor, Room room, String reason) {
        this.appointmentTime = appointmentTime;
        this.patient = patient;
        this.doctor = doctor;
        this.room = room;
        this.reason = reason;

        this.status = AppointmentStatus.SCHEDULED;
        this.slot = defaultSlot(appointmentTime);
    }

    @Override
    public Date getDateTime() {
        return appointmentTime;
    }

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void cancel() {
        if (status == AppointmentStatus.COMPLETED) {
            throw new IllegalStateException("Can't cancel completed appointment");
        }
        status = AppointmentStatus.CANCELLED;
    }

    public void complete() {
        if (status != AppointmentStatus.SCHEDULED) {
            throw new IllegalStateException("Only scheduled appointment can be completed");
        }
        status = AppointmentStatus.COMPLETED;
    }

    public final boolean isActive() {
        return status == AppointmentStatus.SCHEDULED;
    }

    public TimeSlot getSlot() {
        if (slot == null && appointmentTime != null) {
            slot = defaultSlot(appointmentTime);
        }
        return slot;
    }

    public boolean conflictsWith(Appointment other) {
        if (other == null) return false;
        if (!this.isActive() || !other.isActive()) return false;
        if (this.doctor == null || other.doctor == null) return false;
        if (!this.doctor.equals(other.doctor)) return false;

        TimeSlot a = this.getSlot();
        TimeSlot b = other.getSlot();
        if (a == null || b == null) return false;

        return a.overlaps(b);
    }

    @Override
    public String toString() {
        return "model.Appointment{" +
                "appointmentTime=" + appointmentTime +
                ", patient=" + patient +
                ", doctor=" + doctor +
                ", room=" + room +
                ", reason='" + reason + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(appointmentTime, that.appointmentTime) &&
                Objects.equals(patient, that.patient) &&
                Objects.equals(doctor, that.doctor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appointmentTime, patient, doctor);
    }

    private TimeSlot defaultSlot(Date start) {
        if (start == null) return null;
        return new TimeSlot(start, new Date(start.getTime() + 30 * 60_000L));
    }
}