package ru.practicim.ewmstatsserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
        String sb = "class ViewStats {\n" +
                "    app: " + toIndentedString(app) + "\n" +
                "    uri: " + toIndentedString(uri) + "\n" +
                "    hits: " + toIndentedString(hits) + "\n" +
                "}";
        return sb;
    }

    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}