package ru.practicum.ewmmainservice.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.enums.State;
import ru.practicum.ewmmainservice.mappers.categories.CategoryMapper;
import ru.practicum.ewmmainservice.models.Event;
import ru.practicum.ewmmainservice.services.categories.CategoryPublicService;
import ru.practicum.ewmmainservice.services.users.UserAdminService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class EventStorage {
    private final UserAdminService userAdminService;
    private final CategoryPublicService categoryPublicService;
    private final JdbcTemplate jdbcTemplate;


    public List<Event> getEvents(String sqlQuery) {
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeEvent(rs));
    }

    private Event makeEvent(ResultSet rs) throws SQLException {
        Converter<String, State> converter = source -> {
            try {
                return source.isEmpty() ? null : State.valueOf(source.trim().toUpperCase(Locale.ROOT));
            } catch (Exception e) {
                return State.UNSUPPORTED_STATE;
            }
        };

        return new Event(rs.getLong("id"),
                rs.getString("title"),
                rs.getString("annotation"),
                rs.getString("description"),
                rs.getTimestamp("event_date").toLocalDateTime(),
                rs.getFloat("lat"),
                rs.getFloat("lon"),
                CategoryMapper.toCategory(categoryPublicService.getCategoryById(rs.getLong("category_id"))),
                rs.getBoolean("paid"),
                rs.getLong("participant_limit"),
                userAdminService.getUserById(rs.getLong("initiator_id")),
                rs.getTimestamp("created_on").toLocalDateTime(),
                rs.getTimestamp("published_on") != null ?
                        rs.getTimestamp("published_on").toLocalDateTime() : null,
                rs.getBoolean("request_moderation"),
                converter.convert(rs.getString("state"))
        );
    }
}