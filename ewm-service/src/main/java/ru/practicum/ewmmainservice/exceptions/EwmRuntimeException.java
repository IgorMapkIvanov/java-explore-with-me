package ru.practicum.ewmmainservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EwmRuntimeException extends RuntimeException {
    private String message;
    private String reason;
}