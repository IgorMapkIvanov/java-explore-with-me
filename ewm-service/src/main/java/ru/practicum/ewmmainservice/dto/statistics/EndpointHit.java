package ru.practicum.ewmmainservice.dto.statistics;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EndpointHit {
    private Long id;
    private String uri;
    private String app;
    private String ip;
    private String timestamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        EndpointHit that = (EndpointHit) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "class EndpointHit {\n" +
                "    id:        " + id + "\n" +
                "    app:       " + app + "\n" +
                "    uri:       " + uri + "\n" +
                "    ip:        " + ip + "\n" +
                "    timestamp: " + timestamp + "\n" +
                "}";
    }
}