package pl.dudios.planerbackend.calendar.evnet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.dudios.planerbackend.calendar.evnet.model.Event;
import pl.dudios.planerbackend.calendar.evnet.service.EventService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/events")
    public List<Event> getEvents(@AuthenticationPrincipal Long userId) {
        return eventService.getEvents(userId);
    }

    @PostMapping("/event")
    public Event addEvent(@AuthenticationPrincipal Long userId, @RequestBody Event event) {
        return eventService.addEvent(event, userId);
    }

    @DeleteMapping("/event/{eventId}")
    public void deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEventById(eventId);
    }

}
