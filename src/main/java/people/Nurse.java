package people;

import interfaces.Assistable;

public class Nurse extends Staff implements Assistable {

    private String level;
    private String shift;

    public Nurse() {
        super();
    }

    @Override
    public String getRole() {
        return "people.Nurse";
    }

    public Nurse(String staffId, String level, String shift) {
        super(staffId);
        this.level = level;
        this.shift = shift;
    }

    public Nurse(String firstName, String lastName, String phone, String address,
                 String staffId, String level, String shift) {
        super(firstName, lastName, phone, address, staffId);
        this.level = level;
        this.shift = shift;
    }

    @Override
    public void assist(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient cannot be null");
        }
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}
