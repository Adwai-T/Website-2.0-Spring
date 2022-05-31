package in.adwait.website;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;


/*
 * As we use a custom configuration for hibernate jpa repository
 * we have to exclude the autoconfiguration done by spring boot.
 */

@SpringBootApplication
@EnableAutoConfiguration(exclude= HibernateJpaAutoConfiguration.class)
public class WebsiteApplication implements CommandLineRunner{
	private static final Logger log = LoggerFactory.getLogger(WebsiteApplication.class);

	@Value("${message.welcome}")
	private String welcome;
	@Value("${message.end}")
	private String end;

	public static void main(String[] args) {
		SpringApplication.run(WebsiteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        log.info(welcome);
        log.info(end);
    }
}
