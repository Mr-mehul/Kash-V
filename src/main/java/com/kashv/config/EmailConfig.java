package com.kashv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;


@Configuration
public class EmailConfig {

	@Bean
	public SimpleMailMessage emailTemplate() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("mehul.lunagariya8866@gmail.com");
		message.setFrom("abc@sss.com");
		message.setSubject("Important email");
		message.setText("FATAL - Application crash. Save your job !!");
		return message;
	}

}
