package co.com.poli.backlogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BacklogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BacklogServiceApplication.class, args);
	}

}
