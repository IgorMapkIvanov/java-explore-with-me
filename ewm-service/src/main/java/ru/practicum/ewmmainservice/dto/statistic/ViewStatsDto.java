package ru.practicum.ewmmainservice.dto.statistic;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewStatsDto {
    private String uri;
    private String app;
    private Long hits = 0L;
}