package pl.dudios.planerbackend.task.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dudios.planerbackend.task.model.Task;
import pl.dudios.planerbackend.task.repository.TaskRepo;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepo taskRepo;

    public Page<Task> getTasks(Pageable pageable, Long userId) {
        return taskRepo.findAllByUserId(pageable, userId);
    }

    public Task addTask(Task task, Long userId) {

        return taskRepo.save(Task.builder()
                .name(task.getName())
                .deadline(task.getDeadline())
                .completed(false)
                .notify(task.isNotify())
                .userId(userId)
                .build());
    }

    @Transactional
    public Task updateTask(Task task, Long taskId) {
        Task oldTask = taskRepo.findById(taskId).orElseThrow();
        oldTask.setName(task.getName());
        oldTask.setDeadline(task.getDeadline());
        oldTask.setNotify(task.isNotify());

        return taskRepo.save(oldTask);
    }

    public Task getTaskById(Long taskId) {
        return taskRepo.findById(taskId).orElseThrow();
    }

    public void deleteTaskById(Long taskId) {
        taskRepo.deleteById(taskId);
    }

    @Transactional
    public Task changeStatus(Long taskId, boolean completed) {
        System.out.println(completed);
        Task task = taskRepo.findById(taskId).orElseThrow();
        task.setCompleted(completed);
        return taskRepo.save(task);
    }
}
