package org.mitre.springboot.config;

import javax.servlet.Filter;

import org.mitre.oauth2.web.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

@Configuration
@Order(210)
public class ProtectedResourceRegistrationResourceServerConfig extends ResourceServerConfigurerAdapter {
	String PATTERN = "/" + org.mitre.openid.connect.web.ProtectedResourceRegistrationEndpoint.URL + "/**";

	@Autowired
	@Qualifier("corsFilter")
	private Filter corsFilter;
	
	@Autowired
	private OAuth2AuthenticationEntryPoint authenticationEntryPoint; 
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.requestMatchers()
				.antMatchers(PATTERN)
				.and()
			.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
				.and()
			.addFilterBefore(corsFilter, SecurityContextPersistenceFilter.class)
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				.and()
			.authorizeRequests()
				.antMatchers(PATTERN)
				.permitAll()
		;
		// @formatter:on
	}

}