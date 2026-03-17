package people;

public abstract class Staff extends Person {

    private String staffId;

    public Staff() {
        super();
    }

    public Staff(String staffId) {
        this.staffId = staffId;
    }

    public Staff(String firstName, String lastName, String phone, String address, String staffId) {
        super(firstName, lastName, phone, address);
        this.staffId = staffId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
}