package pl.dudios.planerbackend.calendar.evnet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dudios.planerbackend.calendar.evnet.model.Event;
import pl.dudios.planerbackend.calendar.evnet.repository.EventRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepo eventRepo;

    public List<Event> getEvents(Long userId) {
        return eventRepo.findAllByUserId(userId);
    }

    public Event addEvent(Event event, Long userId) {

        return eventRepo.save(Event.builder()
                .name(event.getName())
                .date(event.getDate())
                .userId(userId)
                .build());
    }

    public void deleteEventById(Long eventId) {
        eventRepo.deleteById(eventId);
    }
}
