package ru.practicum.ewmmainservice.exceptions;

public class ValidationException extends EwmRuntimeException {
    public ValidationException(String message, String reason) {
        super(message, reason);
    }
}