package com.idontchop.usagelimitservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.idontchop.usagelimitservice.services.HitProcessor;

@Configuration
public class UsageLimitConfiguration {
	
	@Bean("HitProcessor")
	@Scope("request")
	public HitProcessor getHitProcessor() {
		return new HitProcessor();
	}

}
