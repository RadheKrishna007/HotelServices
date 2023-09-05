package com.lcwd.user.service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyConfig {

	@Bean
	@LoadBalanced // this is used for accessing the api using their name's and  not their port and host
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
}
