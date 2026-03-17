package exceptions;

public class InvalidInsuranceException extends Exception {
    public InvalidInsuranceException() { super(); }
    public InvalidInsuranceException(String message) { super(message); }
}
