package pl.edu.pk.lab3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationStarter {
    public static void main(String[] args) {
        System.setProperty("server.servlet.context-path", "/");
        SpringApplication.run(ApplicationStarter.class, args);
        System.out.println("ApplicationStarter has started");
    }
}
