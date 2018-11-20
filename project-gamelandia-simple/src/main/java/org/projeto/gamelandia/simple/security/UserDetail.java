package org.projeto.gamelandia.simple.security;

import org.projeto.gamelandia.simple.entity.Client;
import org.projeto.gamelandia.simple.entity.Vendedor;
import org.projeto.gamelandia.simple.permission.PermissionEntity;
import org.projeto.gamelandia.simple.repositories.ClientRepository;
import org.projeto.gamelandia.simple.repositories.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetail implements UserDetailsService {

	@Autowired
	private VendedorRepository vendedorRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		String userName = null;
		String userEmail = null;
		String userPwd = null;

		Vendedor vendedorData = this.vendedorRepository.findByEmail(email);
		Client clientData = this.clientRepository.findByEmailCliente(email);

		if (vendedorData == null) {
			userName = clientData.getName();
			userEmail = clientData.getEmailCliente();
			userPwd = clientData.getPassword();
		} else {
			userName = vendedorData.getName();
			userEmail = vendedorData.getEmail();
			userPwd = vendedorData.getPassword();
		}

		LoginDetailBean login = new LoginDetailBean(userName, userEmail, userPwd);

		if (vendedorData == null) {
			for (PermissionEntity permission : clientData.getPermissions()) {
				login.addRole(permission.getRole());
			}
		}else {
			for (PermissionEntity permission : vendedorData.getPermissions()) {
				login.addRole(permission.getRole());
			}
		}

		return login;
	}

}
