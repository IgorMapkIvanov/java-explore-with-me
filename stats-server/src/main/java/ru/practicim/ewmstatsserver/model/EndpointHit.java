package ru.practicim.ewmstatsserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс <b>BookingDto</b> со свойствами:
 * <p><b>id</b> — Идентификатор записи;</p>
 * <p><b>app</b> — Идентификатор сервиса, для которого записывается информация;</p>
 * <p><b>uri</b> — URI, для которого был осуществлен запрос;</p>
 * <p><b>ip</b> — IP-адрес пользователя, осуществившего запрос;</p>
 * <p><b>timestamp</b> — Дата и время, когда был совершен запрос к эндпоинту (в формате "yyyy-MM-dd HH:mm:ss").</p>
 */
@Table(name = "endpoints", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EndpointHit {
    @Id
    @Column("id")
    private Long id;
    @Column("app")
    private String app;
    @Column("uri")
    private String uri;
    @Column("uri_id")
    private String uriId;
    @Column("ip")
    private String ip;
    @Column("timestamp")
    private LocalDateTime timestamp;

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