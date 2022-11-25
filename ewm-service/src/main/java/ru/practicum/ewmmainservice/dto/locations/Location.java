package ru.practicum.ewmmainservice.dto.locations;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @NotNull
    private Float lat;
    @NotNull
    private Float lon;

    @Override
    public String toString() {
        return "class Location {\n" +
                "    lat: " + lat + "\n" +
                "    lon: " + lon + "\n" +
                "}";
    }
}