package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
	
	@Autowired
	private JwtAuthenticationEntrypoint jwtAuthenticationEntrypoint;
	
	@Autowired
	private UserDetailsService jwtuserDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(jwtuserDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManager() throws Exception{
		return super.authenticationManager();
	}
	
	protected void configure(HttpSecurity httpSecurity) throws Exception{
		httpSecurity.csrf().disable()
		.authorizeRequests()
		.antMatchers("/favicon.ico").permitAll()
		.antMatchers("/users/login").permitAll()
	    .antMatchers("/users/sign-up").permitAll()
	    .antMatchers("/h2-console/**").permitAll()
//	    .antMatchers("/browser/index.html/**").permitAll()
//	    .antMatchers("/rest/**").permitAll()
//	    .antMatchers("/actuator/**").permitAll()
		.anyRequest().authenticated().and().
				
		exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntrypoint).and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
	httpSecurity.headers().frameOptions().disable();
//		.and()
//		.csrf().ignoringAntMatchers("/h2-console/**")
//        .and()
//        .cors().disable();;
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    // Allow swagger to be accessed without authentication
	    web.ignoring().antMatchers("/v2/api-docs")//
	        .antMatchers("/swagger-resources/**")//
	        .antMatchers("/swagger-ui.html")//
	        .antMatchers("/configuration/**")//
	        .antMatchers("/webjars/**")//
	        .antMatchers("/public");
	  }
	  
	  
	
	
	
	
	
}
