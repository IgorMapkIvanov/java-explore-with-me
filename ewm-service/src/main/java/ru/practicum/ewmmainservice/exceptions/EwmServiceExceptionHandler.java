package ru.practicum.ewmmainservice.exceptions;

import lombok.NonNull;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class EwmServiceExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e) {
        ApiError apiError = new ApiError(new ArrayList<>(List.of(ExceptionUtils.getStackFrames(e))),
                "internal Server Error",
                "Runtime error");
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleEventNotFoundException(NotFoundException e) {
        ApiError apiError = new ApiError();
        apiError.setMessage(e.getMessage());
        apiError.setReason("Not found error");
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DeleteCategoryException.class)
    public ResponseEntity<Object> handleDeleteCategoryException(DeleteCategoryException e) {
        ApiError apiError = new ApiError();
        apiError.setMessage(e.getMessage());
        apiError.setReason("Can't delete category");
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException e) {
        ApiError apiError = new ApiError();
        apiError.setErrors(new ArrayList<>(List.of(ExceptionUtils.getStackFrames(e))));
        apiError.setMessage(e.getMessage());
        apiError.setReason("Validation error");
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected @NonNull ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException e,
                                                                           @NonNull HttpHeaders headers,
                                                                           @NonNull HttpStatus status,
                                                                           @NonNull WebRequest request) {
        ApiError apiError = new ApiError(new ArrayList<>(List.of(ExceptionUtils.getStackFrames(e))),
                "Method Argument Not Valid",
                "Only events with status \"PENDING\" and \"CANCELED\" can be changed by the user");
        return new ResponseEntity<>(apiError, status);
    }
}