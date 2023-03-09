package pl.dudios.planerbackend.task.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dudios.planerbackend.task.model.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long>{
    Page<Task> findAllByUserId(Pageable pageable, Long userId);

    List<Task> findAllByDeadlineAndNotifyIsTrue(LocalDateTime plusDays);
}
