package pl.dudios.planerbackend.mail;

public interface EmailSender {
    void sendEmail(String to, String subject, String content);
}
