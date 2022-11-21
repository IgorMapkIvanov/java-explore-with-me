package ru.practicim.ewmstatsserver.exceptions;

public class DateTimeValidationException extends RuntimeException {
    public DateTimeValidationException(String message) {
        super(message);
    }
}