package com.project.jw;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class JwApplication {
	@Value("${cloudinary.cloudname}")
	private String cloudName;

	@Value("${cloudinary.apikey}")
	private String apiKey;

	@Value("${cloudinary.apisecret}")
	private String apiSecret;
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public Cloudinary cloudinary() {
		Cloudinary cloudinary = null;
		Map config = new HashMap();
		config.put("cloudname", cloudName);
		config.put("apikey", apiKey);
		config.put("apisecret", apiSecret);
		cloudinary = new Cloudinary(config);
		return cloudinary;
	}
	public static void main(String[] args) {
		SpringApplication.run(JwApplication.class, args);
	}

}
