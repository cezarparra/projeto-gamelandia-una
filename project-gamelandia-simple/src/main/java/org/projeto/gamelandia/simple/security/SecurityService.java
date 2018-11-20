package org.projeto.gamelandia.simple.security;

import java.security.Principal;

import org.projeto.gamelandia.simple.utils.ServiceMap;
import org.projeto.gamelandia.simple.utils.ServicePath;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/*
 * 
 * CLASSE PARA COLETAR O NOME DO USUÁRIO LOGADO NO SISTEMA 
 */
@RestController
@RequestMapping(ServicePath.LOGIN_PATH)
public class SecurityService implements ServiceMap {
	@RequestMapping(method = { RequestMethod.GET })
	public Principal user(Principal user) {
		return user;
	}

}
