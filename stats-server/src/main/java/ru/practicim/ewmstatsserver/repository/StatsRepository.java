package ru.practicim.ewmstatsserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicim.ewmstatsserver.dto.ViewStatsDto;
import ru.practicim.ewmstatsserver.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<EndpointHit, Long> {
    @Query("select new ru.practicim.ewmstatsserver.dto.ViewStatsDto(e.app, e.uri, count(e.ip)) "
            + " from EndpointHit e "
            + " where e.timestamp between ?1 and ?2 "
            + " and e.uri in (?3) "
            + " group by e.app, e.uri")
    List<ViewStatsDto> countTotalIp(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("uris") List<String> uris);

    @Query("select new ru.practicim.ewmstatsserver.dto.ViewStatsDto(e.app, e.uri, count(distinct e.ip)) "
            + " from EndpointHit e "
            + " where e.timestamp between :start and :end "
            + " and e.uri in (:uris) "
            + " group by e.app, e.uri")
    List<ViewStatsDto> countTotalIpDistinct(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("uris") List<String> uris);
}