package com.zee.comfig;

import com.zee.controller.AuthController;
import com.zee.controller.HomeController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AppConfig {

  
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.sessionManagement(management-> management.sessionCreationPolicy(
				SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/products/*/reviews").permitAll()
						.requestMatchers("/api/**").authenticated()
						.anyRequest().permitAll())
						.addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
						.csrf(csrf -> csrf.disable())
						// we need cors configuration here to allow the frontend to access the backend
						// we will create a bean for cors configuration
						.cors(cors -> cors.configurationSource(corsConfigurationSource()))
						.build();
				
	}
	
	// cors configuration source bean
	// corsconfigurationsource will help to define the cors configuration which is used in the security filter chain
	// to allow the frontend to access the backend by defining the allowed origins, methods, headers etc.
	private CorsConfigurationSource corsConfigurationSource() {
		return new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

				CorsConfiguration cfg = new CorsConfiguration();
				// it allowes all the urls to access the backend by frontend
				// like this given below
//				cfg.setAllowedMethods(Collections.singletonList("localhost:3000 localhost:4200"));
				// * = universal allows all
				cfg.setAllowedOrigins(Collections.singletonList("*"));
				// it allows to acces all methodds like post get put etc..
				cfg.setAllowedMethods(Collections.singletonList("*"));
				// it allows all the headers like authorization content type etc..
				cfg.setAllowedHeaders(Collections.singletonList("*"));
				// it allows the credentials like cookies session id etc..
				cfg.setAllowCredentials(true);
				// it allows the exposed headers like authorization 
				cfg.setExposedHeaders(Collections.singletonList("Authorization"));
				// it allows the max age of the preflight request
				// 3600 seconds = 1 hour
				// it means that the preflight request will be cached for 1 hour
				cfg.setMaxAge(3600l);
				
				
				return cfg;
			}
		};
		
	}
	
	@Bean
	// password encoder bean to encode the password
	// we are using bcrypt password encoder
	// bcrypt is a one way hashing algorithm
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	// rest template bean to make rest api calls to other services
	// like auth service from product service to validate the token
	// resttemplate is used to make rest api calls to other services
	// it is a synchronous client to perform http requests
	// it is used to consume restful web services
	// we need this when we want to use external api in our application
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
