package org.example.bankup;


import org.example.bankup.service.ZipCodeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableFeignClients( clients = ZipCodeService.class)
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class BankUpApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankUpApplication.class, args);
	}

}
