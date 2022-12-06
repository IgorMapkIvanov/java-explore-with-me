package ru.practicum.ewmmainservice.client.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewHits {

    private String app;
    private String uri;
    private Integer hits;
}
