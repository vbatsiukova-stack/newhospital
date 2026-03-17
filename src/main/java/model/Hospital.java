package model;

import exceptions.AppointmentConflictException;
import exceptions.EntityNotFoundException;
import main.java.enums.RoomType;
import people.Doctor;
import people.Patient;
import people.Staff;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Hospital {
    private String name;
    private String address;
    private String phone;
    private String email;
    private String website;
    private List<Department> departments;
    private List<Room> rooms;
    private List<Staff> staff;
    private List<Appointment> appointments;
    private List<LabTest> labTests;

    public Hospital() {
        this.departments = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.staff = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.labTests = new ArrayList<>();
    }

    public Hospital(String name, String address, String phone, String email, String website,
                    List<Department> departments, List<Room> rooms, List<Staff> staff,
                    List<Appointment> appointments, List<LabTest> labTests) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.website = website;
        this.departments = departments != null ? departments : new ArrayList<>();
        this.rooms = rooms != null ? rooms : new ArrayList<>();
        this.staff = staff != null ? staff : new ArrayList<>();
        this.appointments = appointments != null ? appointments : new ArrayList<>();
        this.labTests = labTests != null ? labTests : new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Staff> getStaff() {
        return staff;
    }

    public void setStaff(List<Staff> staff) {
        this.staff = staff;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<LabTest> getLabTests() {
        return labTests;
    }

    public void setLabTests(List<LabTest> labTests) {
        this.labTests = labTests;
    }

    public Staff findStaffByStaffId(String staffId) throws EntityNotFoundException {
        if (staff == null || staff.isEmpty()) {
            throw new EntityNotFoundException("Staff list is empty");
        }

        for (Staff s : staff) {
            if (s != null && Objects.equals(s.getStaffId(), staffId)) {
                return s;
            }
        }

        throw new EntityNotFoundException("Staff with staffId " + staffId + " not found");
    }

    public void scheduleAppointmentOrThrow(Appointment newAppointment) throws AppointmentConflictException {
        if (newAppointment == null) {
            throw new IllegalArgumentException("Appointment cannot be null");
        }

        if (appointments == null) {
            appointments = new ArrayList<>();
        }

        for (Appointment a : appointments) {
            if (a != null && newAppointment.conflictsWith(a)) {
                throw new AppointmentConflictException(
                        "Appointment conflict: doctor already has an appointment in this time slot"
                );
            }
        }

        appointments.add(newAppointment);
    }

    public void scheduleAppointmentOrThrow(Date appointmentTime, Patient patient, Doctor doctor)
            throws AppointmentConflictException {
        Appointment newAppointment = new Appointment(appointmentTime, patient, doctor);
        scheduleAppointmentOrThrow(newAppointment);
    }

    public List<Doctor> getAllDoctors() {
        return staff.stream()
                .filter(Objects::nonNull)
                .filter(s -> s instanceof Doctor)
                .map(s -> (Doctor) s)
                .collect(Collectors.toList());
    }

    public List<Staff> getStaffSortedByLastName() {
        return staff.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(Staff::getLastName))
                .collect(Collectors.toList());
    }

    public List<String> getUniqueStaffRoles() {
        return staff.stream()
                .filter(Objects::nonNull)
                .map(Staff::getRole)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    public long countAppointmentsByDoctor(Doctor doctor) {
        return appointments.stream()
                .filter(Objects::nonNull)
                .filter(a -> Objects.equals(a.getDoctor(), doctor))
                .count();
    }

    public Appointment findFirstAppointmentByDoctor(Doctor doctor) throws EntityNotFoundException {
        return appointments.stream()
                .filter(Objects::nonNull)
                .filter(a -> Objects.equals(a.getDoctor(), doctor))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("No appointment found for doctor"));
    }

    public boolean hasOperatingRoom() {
        return rooms.stream()
                .filter(Objects::nonNull)
                .anyMatch(room -> room.getType() == RoomType.OPERATING);
    }

    public List<LabTest> getLabTestsWithoutResult() {
        return labTests.stream()
                .filter(Objects::nonNull)
                .filter(test -> test.getResult() == null)
                .collect(Collectors.toList());
    }
}