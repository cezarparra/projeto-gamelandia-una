package org.projeto.gamelandia.simple.utils;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * CLASSE RESPONSÁVEL POR FAZER A LEITURA DO ARQUIVO .properties
 * E TAMBÉM REALIZA O TIPO DE CRIPTOGRAFIA É UTILIZADA NO SISTEMA O 
 * BCrypt
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = { "org.projeto.gamelandia.simple" })
public class AppContext {

	@Bean(name = "applicationProperty")
	public ApplicationProperty getApplicationProperty() {
		return new ApplicationProperty();
	}

	@Bean(name = "passwordEncoder")
	public PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();
	}
}
