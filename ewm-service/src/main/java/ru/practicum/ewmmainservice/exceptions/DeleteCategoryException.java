package ru.practicum.ewmmainservice.exceptions;

public class DeleteCategoryException extends EwmRuntimeException {
    public DeleteCategoryException(String message, String reason) {
        super(message, reason);
    }
}