package org.mitre.springboot.config.openid.connect;

import javax.servlet.Filter;

import org.mitre.oauth2.web.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

@Order(150)
@Configuration
public class JwkWebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	@Qualifier("corsFilter")
	private Filter corsFilter;
	
	@Autowired
	private Http403ForbiddenEntryPoint http403ForbiddenEntryPoint;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.requestMatchers()
				.antMatchers("/jwk**")
				.and()
			.exceptionHandling()
				.authenticationEntryPoint(http403ForbiddenEntryPoint)
				.and()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
		.authorizeRequests()
			.antMatchers("/jwk**")
			.permitAll()
		;
		// @formatter:on
	}
}
