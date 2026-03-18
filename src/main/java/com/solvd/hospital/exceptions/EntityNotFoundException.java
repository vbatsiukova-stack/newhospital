package com.solvd.hospital.exceptions;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
