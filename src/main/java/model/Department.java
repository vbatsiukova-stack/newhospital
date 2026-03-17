package model;

import main.java.enums.DepartmentType;
import people.Doctor;
import people.Nurse;


public class Department {
    private String name;
    private Doctor[] doctors;
    private Nurse[] nurses;
    private Room[] rooms;
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

    public Doctor[] getDoctors() {
        return doctors;
    }

    public void setDoctors(Doctor[] doctors) {
        this.doctors = doctors;
    }

    public Nurse[] getNurses() {
        return nurses;
    }

    public void setNurses(Nurse[] nurses) {
        this.nurses = nurses;
    }

    public Room[] getRooms() {
        return rooms;
    }

    public void setRooms(Room[] rooms) {
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
