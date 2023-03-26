package pl.dudios.planerbackend.calendar.evnet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dudios.planerbackend.calendar.evnet.model.Event;

import java.util.List;

@Repository
public interface EventRepo extends JpaRepository<Event, Long> {
    List<Event> findAllByUserId(Long userId);
}
