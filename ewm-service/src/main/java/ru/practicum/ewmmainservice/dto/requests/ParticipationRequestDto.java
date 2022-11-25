package ru.practicum.ewmmainservice.dto.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDto {
    private Long id;
    private Long event;
    private Long requester;
    private String created;
    private String status;

    @Override
    public String toString() {
        return "class ParticipationRequestDto {\n" +
                "    id:        " + id + "\n" +
                "    event:     " + event + "\n" +
                "    requester: " + requester + "\n" +
                "    created:   " + created + "\n" +
                "    status:    " + status + "\n" +
                "}";
    }
}