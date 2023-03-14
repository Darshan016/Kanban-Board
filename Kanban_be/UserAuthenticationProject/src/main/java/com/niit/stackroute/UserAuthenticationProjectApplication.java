package com.niit.stackroute;

import com.niit.stackroute.filter.JwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
@EnableEurekaClient
@CrossOrigin
public class UserAuthenticationProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserAuthenticationProjectApplication.class, args);
	}

//	@Bean
//	public FilterRegistrationBean<JwtFilter> jwtFilter(){
//		FilterRegistrationBean<JwtFilter> filterRegistrationBean = new FilterRegistrationBean<>();
//		filterRegistrationBean.setFilter(new JwtFilter());
//		filterRegistrationBean.addUrlPatterns("/api/v1/*");
//		return filterRegistrationBean;
//	}
	@Bean
	public FilterRegistrationBean<?> filterRegistrationBean(){
		final CorsConfiguration config=new CorsConfiguration();

		config.setAllowCredentials(true);
		config.addAllowedOrigin("http://localhost:4200");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");

		final UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**",config);

		FilterRegistrationBean<?> bean=new FilterRegistrationBean<>(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);

		return bean;
	}
}
