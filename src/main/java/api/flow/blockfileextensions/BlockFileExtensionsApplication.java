package api.flow.blockfileextensions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BlockFileExtensionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlockFileExtensionsApplication.class, args);
	}

}
