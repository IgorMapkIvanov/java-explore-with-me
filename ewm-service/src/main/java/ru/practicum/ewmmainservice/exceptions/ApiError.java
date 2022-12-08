package ru.practicum.ewmmainservice.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static ru.practicum.ewmmainservice.Utils.Constants.DATE_TIME_FORMAT;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class ApiError {
    private final String message;
    private final String reason;
    private final HttpStatus status;
    private final StackTraceElement[] errors;
    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private final LocalDateTime timestamp;
}