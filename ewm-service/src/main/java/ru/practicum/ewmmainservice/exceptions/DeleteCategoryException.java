package ru.practicum.ewmmainservice.exceptions;

public class DeleteCategoryException extends Throwable {
    public DeleteCategoryException(String message) {
        super(message);
    }

    public DeleteCategoryException(String message, Throwable cause) {
        super(message, cause);
    }
}