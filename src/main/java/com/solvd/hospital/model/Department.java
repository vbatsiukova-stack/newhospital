package com.solvd.hospital.model;

import com.solvd.hospital.enums.DepartmentType;
import com.solvd.hospital.people.Doctor;
import com.solvd.hospital.people.Nurse;

import java.util.List;
import java.util.ArrayList;

public class Department {

    private String name;
    private List<Doctor> doctors = new ArrayList<>();
    private List<Nurse> nurses = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private DepartmentType type;

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    public Department(String name, DepartmentType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public List<Nurse> getNurses() {
        return nurses;
    }

    public void setNurses(List<Nurse> nurses) {
        this.nurses = nurses;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public DepartmentType getType() {
        return type;
    }

    public void setType(DepartmentType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "model.Department{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}