package xyz.alexandrit.hamsterservice.exception;

public class HamsterNotFoundException extends RuntimeException {
    public HamsterNotFoundException(String message) {
        super(message);
    }
}
