package in.adwait.website;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
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
