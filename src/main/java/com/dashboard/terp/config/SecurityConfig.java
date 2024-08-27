package com.dashboard.terp.config;

import java.io.ObjectInputFilter.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.dashboard.terp.filter.JwtAuthFilter;
import com.dashboard.terp.services.CustomSecurityUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {
	
	
	@Autowired
	private JwtAuthFilter jwtAuthFilter;
	
	@Autowired
	private CustomAuthenticationEntryPoint entryPoint;
	
	@Autowired
	private AccessDeniedHandlerJwt accessDeniedHandler;
	
	//implmentation with in memory
//	@Bean
//	public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//		UserDetails admin  = User.withUsername("abc@gmail.com").password(encoder.encode("tanmay")).roles("ADMIN").build();
//		UserDetails user  = User.withUsername("tanmay@gmail.com").password(encoder.encode("tanmay")).roles("USER").build();
//
//		return new InMemoryUserDetailsManager(admin,user);
//	}
	
	
	@Bean
	 UserDetailsService userDetailsService() {
		return new CustomSecurityUserDetailsService();
	}
	
	
//	//below for without jwt basic auth
//	@Bean
//	 SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http.csrf(csrf -> csrf.disable()) .authorizeHttpRequests(authz -> authz
//        		.requestMatchers(HttpMethod.POST,"/api/employee" ).permitAll()
//        		.requestMatchers("/api/employee/without").permitAll()  
//        		.anyRequest().authenticated()
//        		)
//                .formLogin(Customizer.withDefaults());
////                .httpBasic(Customizer.withDefaults());
//			 
//		 return http.build();
//	}
	
	
	@Bean
	 SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

       http.csrf(csrf -> csrf.disable()) 
       .cors(cors->cors.disable())
       .authorizeHttpRequests(authz -> authz
       		.requestMatchers(HttpMethod.POST,"/api/employee","/api/employee/login" ).permitAll()
       		.requestMatchers("/api/employee/without").permitAll()  
       		.anyRequest().authenticated()
       		)
//       .exceptionHandling(ex->ex.accessDeniedHandler(accessDeniedHandler))
       .exceptionHandling(ex-> ex.authenticationEntryPoint(entryPoint))
       
       .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
       .authenticationProvider(authenticationProvider())
       .addFilterBefore (jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);
             
		 return http.build();
	}
	
	@Bean
	 PasswordEncoder passwordEncoder () {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	 AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Bean
	AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
//	@Bean
//	 WebMvcConfigurer corsConfgiurer() {
//		return new WebMvcConfigurer() {
//
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				// TODO Auto-generated method stub
//				registry.addMapping("/**")
//						.allowedOrigins("http://127.0.0.1:8080")
//						.allowedMethods("GET","PUT","POST","DELETE","OPTIONS")
//						.allowCredentials(true);
//							
//			}
//		
//			
//		
//		};
//	}
	
	

}
