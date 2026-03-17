package exceptions;

public class AppointmentConflictException extends Exception {
    public AppointmentConflictException() { super(); }
    public AppointmentConflictException(String message) { super(message); }
}
