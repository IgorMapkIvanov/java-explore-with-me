package ru.practicim.ewmstatsserver.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Objects;

/**
 * Класс <b>ViewStats</b> со свойствами:
 * <p><b>app</b> — Идентификатор сервиса;</p>
 * <p><b>uri</b> — URI, для которого был осуществлен запрос;</p>
 * <p><b>hits</b> — Количество просмотров.</p>
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewStats {
    private String app;
    private String uri;
    private Long hits;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ViewStats viewStats = (ViewStats) o;
        return Objects.equals(this.app, viewStats.app) &&
                Objects.equals(this.uri, viewStats.uri) &&
                Objects.equals(this.hits, viewStats.hits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(app, uri, hits);
    }

    @Override
    public String toString() {
        return "class ViewStats {\n" +
                "    app:  " + app + "\n" +
                "    uri:  " + uri + "\n" +
                "    hits: " + hits + "\n" +
                "}";
    }
}