package ru.practicum.ewmmainservice.services.compilations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.dto.compilations.CompilationDto;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.mappers.compilations.CompilationMapper;
import ru.practicum.ewmmainservice.models.Compilation;
import ru.practicum.ewmmainservice.pageable.EwmPageable;
import ru.practicum.ewmmainservice.repositories.CompilationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationPublicServiceImpl implements CompilationPublicService {
    private final CompilationRepository compilationRepository;

    /**
     * Get compilations.
     *
     * @param pinned {@link Boolean}
     * @param from   {@link Integer}
     * @param size   {@link Integer}
     * @return List<CompilationDto>
     */
    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = EwmPageable.of(from, size, Sort.by(Sort.Direction.ASC, "id"));
        if (pinned == null) {
            log.info("COMPILATION_PUBLIC_SERVICE: Get compilations without pinned.");
            return compilationRepository.findAll(pageable).stream()
                    .map(CompilationMapper::toCompilationDto)
                    .collect(Collectors.toUnmodifiableList());
        } else {
            log.info("COMPILATION_PUBLIC_SERVICE: Get compilations with pinned = {}.", pinned);
            return compilationRepository.findByPinned(pinned, pageable).stream()
                    .map(CompilationMapper::toCompilationDto)
                    .collect(Collectors.toUnmodifiableList());
        }
    }

    /**
     * Get compilation by ID.
     *
     * @param compId {@link Long}
     * @return {@link CompilationDto}
     */
    @Override
    public CompilationDto getCompilationById(Long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(() -> {
            String message = String.format("Compilation with ID = '%s', not found", compId);
            String reason = "Compilation not found";
            throw new NotFoundException(message, reason);
        });
        log.info("COMPILATION_PUBLIC_SERVICE: Get compilations with ID = {}.", compId);
        return CompilationMapper.toCompilationDto(compilation);
    }
}