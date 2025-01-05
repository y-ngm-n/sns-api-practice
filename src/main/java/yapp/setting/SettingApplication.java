package yapp.setting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SettingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SettingApplication.class, args);
	}

}
