package rs.ac.uns.ftn.asd.Projekatsiit2023;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Projekatsiit2023Application {
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder){
		return builder.build();
	}
	public static void main(String[] args) {
		SpringApplication.run(Projekatsiit2023Application.class, args);

	}

}
