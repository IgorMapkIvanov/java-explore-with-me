package ru.practicum.ewmmainservice.services.compilations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmainservice.dto.compilations.CompilationDto;
import ru.practicum.ewmmainservice.dto.compilations.NewCompilationDto;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.mappers.compilations.CompilationMapper;
import ru.practicum.ewmmainservice.models.Compilation;
import ru.practicum.ewmmainservice.models.Event;
import ru.practicum.ewmmainservice.repositories.CompilationRepository;
import ru.practicum.ewmmainservice.repositories.EventRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CompilationAdminServiceImpl implements CompilationAdminService {
    private final EventRepository eventRepository;

    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;

    /**
     * Add new compilation.
     *
     * @param newCompilationDto {@link NewCompilationDto}
     * @return {@link CompilationDto}
     */

    @Override
    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {
        CompilationDto savedCompilation = compilationMapper
                .toCompilationDto(compilationRepository.save(compilationMapper.toCompilationFromNew(newCompilationDto)));
        log.info("COMPILATION_ADMIN_SERVICE: Add compilation: ID = {}", savedCompilation.getId());
        return savedCompilation;
    }

    /**
     * Delete compilation.
     *
     * @param compId {@link Long}
     */
    @Override
    public void deleteCompilation(Long compId) {
        checkInDbAndReturnCompilation(compId);
        compilationRepository.deleteById(compId);
        log.info("COMPILATION_ADMIN_SERVICE: Delete compilation with ID = {}", compId);
    }

    /**
     * Delete event from compilation.
     *
     * @param compId  {@link Long}
     * @param eventId {@link Long}
     */
    @Override
    public void deleteEventFromCompilation(Long compId, Long eventId) {
        Compilation compilation = checkInDbAndReturnCompilation(compId);
        Event event = checkInDbAndReturnEvent(eventId);
        compilation.getEvents().remove(event);
        log.info("COMPILATION_ADMIN_SERVICE: Event with ID = {} delete from compilation with ID = {}", eventId, compId);
    }

    /**
     * Add event to compilation
     *
     * @param compId  {@link Long}
     * @param eventId {@link Long}
     */
    @Override
    public void addEventToCompilation(Long compId, Long eventId) {
        Compilation compilation = checkInDbAndReturnCompilation(compId);
        Event newEventToCompilation = checkInDbAndReturnEvent(eventId);
        if (!compilation.getEvents().contains(newEventToCompilation)) {
            compilation.getEvents().add(newEventToCompilation);
        }
        log.info("COMPILATION_ADMIN_SERVICE: Event with ID = {} add to compilation with ID = {}", eventId, compId);
    }

    /**
     * Unpin compilation
     *
     * @param compId {@link Long}
     */
    @Override
    public void unpinCompilation(Long compId) {
        Compilation compilation = checkInDbAndReturnCompilation(compId);
        if (compilation.getPinned()) {
            compilation.setPinned(false);
        }
        log.info("COMPILATION_ADMIN_SERVICE: Unpin compilation with ID = {}", compId);
    }

    /**
     * Pin compilation to homepage
     *
     * @param compId {@link Long}
     */
    @Override
    public void pinCompilationToHomepage(Long compId) {
        Compilation compilation = checkInDbAndReturnCompilation(compId);
        if (!compilation.getPinned()) {
            compilation.setPinned(true);
        }
        log.info("COMPILATION_ADMIN_SERVICE: Pin compilation with ID = {}", compId);
    }

    private Event checkInDbAndReturnEvent(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> {
            String message = String.format("Event with ID = '%s', not found", id);
            String reason = "Event not found";
            throw new NotFoundException(message, reason);
        });
    }

    private Compilation checkInDbAndReturnCompilation(Long id) {
        return compilationRepository.findById(id).orElseThrow(() -> {
            String message = String.format("Compilation with ID = '%s', not found", id);
            String reason = "Compilation not found";
            throw new NotFoundException(message, reason);
        });
    }
}