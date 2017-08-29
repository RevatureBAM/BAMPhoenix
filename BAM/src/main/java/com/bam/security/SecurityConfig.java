package com.bam.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.bam.service.BamUserServiceDetails;

/**
 * 
 * @author Duncan Hayward
 * Getting user from the database with
 * @Autowired
 * UserDetailsService
 *
  * LoadUsername() will load the User record from the DB
  * Pass back a Spring Security User Object NOT BAMUser object
   * .passwordEncoder(new BCryptPasswordEncoder());
   * 
   * Don't disable csrf in production
   * 
   *  //.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	BamUserServiceDetails userDetailsService;

	@Autowired
	private AuthenticationSuccessHandler restAuthenticationSuccessHandler;

	@Autowired
	private AuthenticationFailureHandler restAuthenticationFailureHandler;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	
	}
	
	/***
	 * @author Nam Mai
	 * Configure the passwordEncoder to use the BCrypt hashing algorithm
	 */
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	/***
	 * @author Nam Mai
	 * This method encodes the password upon authentication
	 */
	@Bean
	public DaoAuthenticationProvider authProvider(){
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	 @Override
	 public void configure(WebSecurity web) throws Exception {
		// Ignore certain URLs.
		web.ignoring().antMatchers("/index.html", "/static/**", "/");
	 }


	/***
	 * @author Nam Mai
	 * References the authProvider to enable hashing
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth){
		auth.authenticationProvider(authProvider());
	}
	
	@Override
	 protected void configure(HttpSecurity http) throws Exception {
	  http
//	   .headers().disable()
	   .csrf().disable()
	   .authorizeRequests()
	    .antMatchers("/rest/api/v1/Users/Register").permitAll()
	    .antMatchers("**rest*/**").authenticated()
	    .antMatchers("*rest*/**").authenticated()
	    .antMatchers("**/*rest*/**").authenticated()
	    .antMatchers("**rest*/**").authenticated()
	    .anyRequest().authenticated()
//	    .antMatchers("/rest/api/v1/Curriculum/**").hasAuthority("Trainer")
	    .and()
	    .formLogin()
	   	.loginPage("/")
	    .loginProcessingUrl("/authenticate")
	    .successHandler(restAuthenticationSuccessHandler)
	    .failureHandler(restAuthenticationFailureHandler)
	    .usernameParameter("username")
	    .passwordParameter("password")
	    .permitAll()
	    .and()
	    .logout()
	    .logoutUrl("/logout")
	    .logoutSuccessHandler(new LogoutSuccessHandler() {
	    	 @Override
	    	public void onLogoutSuccess(
	    	HttpServletRequest request,
	    	HttpServletResponse response,
	    	Authentication a) throws IOException, ServletException {
	    	response.setStatus(HttpServletResponse.SC_NO_CONTENT);
	    	  }

	    	})
	    	.deleteCookies("JSESSIONID")
	    	.invalidateHttpSession(true)
	    .permitAll();
	 }
}
