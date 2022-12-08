package ru.practicum.ewmmainservice.exceptions;

public class NotFoundException extends EwmRuntimeException {
    public NotFoundException(String message, String reason) {
        super(message, reason);
    }
}