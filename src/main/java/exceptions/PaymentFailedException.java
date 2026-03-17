package exceptions;

public class PaymentFailedException extends Exception {
    public PaymentFailedException() { super(); }
    public PaymentFailedException(String message) { super(message); }
}
