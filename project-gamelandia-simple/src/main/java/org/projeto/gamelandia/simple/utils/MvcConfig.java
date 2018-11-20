package org.projeto.gamelandia.simple.utils;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan
@EnableWebSecurity
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/private/**").allowedOrigins("http://localhost:8080", "http://localhost:8081", "https://sandbox.pagseguro.uol.com.br",
				"https://apis-hm.ans.gov.br/e-protocolo/operadoras/005711/documentos/201800571100000")

				.allowedMethods("GET", "POST", "PUT", "DELETE")
				.allowedHeaders("Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method",
						"Access-Control-Request-Headers", "Access-Control-Allow-Origin")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Origin")
				.allowCredentials(true).maxAge(7200);

	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
          .addResourceHandler("/resources/**")
          .addResourceLocations("/resources/"); 
    }
}
