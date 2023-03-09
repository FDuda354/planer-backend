package pl.dudios.planerbackend.task.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.dudios.planerbackend.task.model.Task;
import pl.dudios.planerbackend.task.service.TaskService;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/tasks")
    public Page<Task> getTasks(Pageable pageable,@AuthenticationPrincipal Long userId) {
        return taskService.getTasks(pageable, userId);
    }

    @GetMapping("/task/{taskId}")
    public Task getTask(@PathVariable Long taskId) {
        return taskService.getTaskById(taskId);
    }

    @PostMapping("/task")
    public Task addTask(@RequestBody Task task, @AuthenticationPrincipal Long userId) {
      return taskService.addTask(task ,userId);
    }
    @PutMapping("/task/{taskId}")
    public Task updateTask(@RequestBody Task task, @PathVariable Long taskId) {
        return taskService.updateTask(task ,taskId);
    }
    @PatchMapping("/task/{taskId}")
    public Task changeStatus(@PathVariable Long taskId,@RequestParam boolean completed) {
        return taskService.changeStatus(taskId, completed);
    }

    @DeleteMapping("/task/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
         taskService.deleteTaskById(taskId);
    }
}
