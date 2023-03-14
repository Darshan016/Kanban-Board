package com.stackroute.emailsender;

//import com.stackroute.emailsender.rabbitMq.MailConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EmailsenderApplication {



	public static void main(String[] args) {
		SpringApplication.run(EmailsenderApplication.class, args);

	}

}
