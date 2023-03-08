package pl.dudios.planerbackend.security.user.model.dto;

public record ChangePassword(String password, String repeatPassword, String hash) {
}
