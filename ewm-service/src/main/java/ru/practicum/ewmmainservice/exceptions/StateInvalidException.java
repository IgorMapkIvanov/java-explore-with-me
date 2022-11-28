package ru.practicum.ewmmainservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StateInvalidException extends RuntimeException {
    public StateInvalidException(String message) {
        super("The passed value for the state field is incorrect");
    }
}