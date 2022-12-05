package ru.practicum.ewmmainservice.config;

import lombok.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import ru.practicum.ewmmainservice.services.statistics.StatsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StatsHandle implements HandlerInterceptor {
    private final StatsService statsService;

    public StatsHandle(StatsService statsService) {
        this.statsService = statsService;
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request,
                           HttpServletResponse response, @NonNull Object handler,
                           ModelAndView modelAndView) throws Exception {
        if (response.getStatus() == 200) {
            statsService.statsHit(request);
        }
    }
}