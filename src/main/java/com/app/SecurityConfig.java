package com.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.business.UsersBusinessService;

/*
 * Alex Vergara
 * 5/3/2022
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	// to manipulate the users
	@Autowired
	UsersBusinessService service;
	
	// to hash passwords
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// security configurations
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.authorizeRequests()
			
			// allow non logged in users to see the login page, register page, and image folders
			.antMatchers("/login", "/register", "/img/**").permitAll()
			.and()
			.httpBasic()
			.and()
			.formLogin()
			// added to specify login form
			// used the URL that is served by the login controller
			.loginPage("/login")
				
			// match the text input fields on the login form
			.usernameParameter("username")
			.passwordParameter("password")
			.permitAll()
			
			// display all orders after login
			.defaultSuccessUrl("/all");
			
	}
	
	// @SuppressWarnings("deprecation")
	// encrypt the passwords
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		String password = new BCryptPasswordEncoder().encode("123");
		// for entering the encrypted value into the database
		// extend to registration?
		System.out.println("== Encrypted value of 123 ===== " + password + " ==");
		auth
		.userDetailsService(service);

	}
}
