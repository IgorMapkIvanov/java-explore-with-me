package ru.practicum.ewmmainservice.exceptions;

public class ConflictException extends EwmRuntimeException{
    public ConflictException(String message, String reason) {
        super(message, reason);
    }
}