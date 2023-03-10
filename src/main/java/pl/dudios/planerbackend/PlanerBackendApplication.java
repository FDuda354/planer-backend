package pl.dudios.planerbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PlanerBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlanerBackendApplication.class, args);
    }

}
