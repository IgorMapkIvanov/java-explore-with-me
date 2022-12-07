package ru.practicim.ewmstatsserver.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

import static Utils.Constants.DATE_TIME_FORMAT;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EndpointHitDto implements Serializable {
    private Long id;
    @NotBlank
    @Length(max = 100)
    private String app;
    @NotBlank
    @Length(max = 500)
    private String uri;
    @NotBlank
    @Length(max = 50)
    private String ip;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime timestamp;
}