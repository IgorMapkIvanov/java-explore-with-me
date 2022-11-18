package ru.practicim.ewmstatsserver.model;

import lombok.*;

import javax.persistence.*;


/**
 * Класс <b>BookingDto</b> со свойствами:
 * <p><b>id</b> — Идентификатор записи;</p>
 * <p><b>app</b> — Идентификатор сервиса для которого записывается информация;</p>
 * <p><b>uri</b> — URI для которого был осуществлен запрос;</p>
 * <p><b>ip</b> — IP-адрес пользователя, осуществившего запрос;</p>
 * <p><b>timestamp</b> — Дата и время, когда был совершен запрос к эндпоинту (в формате "yyyy-MM-dd HH:mm:ss");</p>
 */
@Entity
@Table(name = "endpoints", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EndpointHit {
    @Id
    @Column(name = "id", columnDefinition = "bigint")
    @GeneratedValue(strategy = GenerationType.IDENTITY,
            generator = "endpoints_generator")
    private Long id;
    @Column(name = "app", nullable = false, length = 100)
    private String app;
    @Column(name = "uri", nullable = false, length = 100)
    private String uri;
    @Column(name = "ip", nullable = false, length = 50)
    private String ip;
    @Column(name = "timestamp", nullable = false, length = 50)
    private String timestamp;

    @Override
    public String toString() {
        return "class EndpointHit {\n" +
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