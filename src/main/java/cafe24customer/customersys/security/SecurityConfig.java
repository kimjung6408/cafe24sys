package cafe24customer.customersys.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    return http
	        .csrf(CsrfConfigurer::disable)
	        .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/","/css/**","/images/**","/js/**","/h2-console/**").permitAll()
	            .requestMatchers("/api/v1/**").hasRole("USER")
	            .anyRequest().authenticated()
	        )
	        .build();
	}

}
