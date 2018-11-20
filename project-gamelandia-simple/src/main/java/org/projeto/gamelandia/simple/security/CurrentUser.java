package org.projeto.gamelandia.simple.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


/*
 * 
 * CLASSE PARA COLETAR O NOME DO USUÁRIO LOGADO NO SISTEMA 
 */
@Component
public class CurrentUser {

	public LoginDetailBean getActiveUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal == null) {
			return null;
		}

		return (LoginDetailBean)principal;
	}

}
