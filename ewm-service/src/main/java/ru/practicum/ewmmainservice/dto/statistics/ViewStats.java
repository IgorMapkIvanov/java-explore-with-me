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
public class ViewStats {
    private String uri;
    private String app;
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