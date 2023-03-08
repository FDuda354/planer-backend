package pl.dudios.planerbackend.task.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dudios.planerbackend.mail.EmailClientService;
import pl.dudios.planerbackend.security.user.repository.UserRepo;
import pl.dudios.planerbackend.task.model.Task;
import pl.dudios.planerbackend.task.repository.TaskRepo;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TaskMailNotify {

    private final TaskRepo taskRepo;
    private final UserRepo userRepo;
    private final EmailClientService emailClientService;

    @Scheduled(cron = "0 0 23 * * *")
    @Transactional
    public void sendMail() {
      taskRepo.findAllByDeadline(LocalDateTime.now().plusDays(1)).forEach(task -> {
            String email = userRepo.findById(task.getUserId()).get().getUsername();
          emailClientService.getSender().sendEmail(email, "Remind Task", createEmailMessage(task));
        });
    }

    private String createEmailMessage(Task task) {
        return "Task: " + task.getName() + " is due tomorrow!";
    }
}
