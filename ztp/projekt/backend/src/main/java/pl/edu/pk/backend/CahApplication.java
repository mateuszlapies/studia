package pl.edu.pk.backend;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableMongoRepositories
public class CahApplication {

	private static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		context = SpringApplication.run(CahApplication.class, args);
	}

	public static void restart() {
		ApplicationArguments args = context.getBean(ApplicationArguments.class);

		Thread thread = new Thread(() -> {
			context.close();
			context = SpringApplication.run(CahApplication.class, args.getSourceArgs());
		});

		thread.setDaemon(false);
		thread.start();
	}
}
