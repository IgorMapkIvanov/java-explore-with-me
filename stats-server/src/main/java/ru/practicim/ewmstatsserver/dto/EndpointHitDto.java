package ru.practicim.ewmstatsserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reactor.util.annotation.NonNull;

import java.io.Serializable;

/**
 * A DTO for the {@link ru.practicim.ewmstatsserver.model.EndpointHit} entity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EndpointHitDto implements Serializable {
    private Long id;
    @NonNull
    private String app;
    @NonNull
    private String uri;
    @NonNull
    private String ip;
    @NonNull
    private String timestamp;


    @Override
    public String toString() {
        return "class EndpointHitDto {\n" +
                "    id:       " + toIndentedString(id) + "\n" +
                "    app:       " + toIndentedString(app) + "\n" +
                "    uri:       " + toIndentedString(uri) + "\n" +
                "    ip:        " + toIndentedString(ip) + "\n" +
                "    timestamp: " + toIndentedString(timestamp) + "\n" +
                "}";
    }

    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}