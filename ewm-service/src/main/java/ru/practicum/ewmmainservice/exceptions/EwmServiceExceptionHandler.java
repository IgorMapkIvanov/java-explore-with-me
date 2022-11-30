package ru.practicum.ewmmainservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class EwmServiceExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleRuntimeException(RuntimeException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .reason("INTERNAL_SERVER_ERROR")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .errors(e.getStackTrace())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleEventNotFoundException(NotFoundException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .reason(e.getReason())
                .status(HttpStatus.NOT_FOUND)
                .errors(e.getStackTrace())
                .timestamp(LocalDateTime.now()).build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleDeleteCategoryException(DeleteCategoryException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .reason("Can't delete category")
                .errors(e.getStackTrace())
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(ValidationException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .reason(e.getReason())
                .status(HttpStatus.BAD_REQUEST)
                .errors(e.getStackTrace())
                .timestamp(LocalDateTime.now()).build();
    }
}