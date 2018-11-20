package org.projeto.gamelandia.simple.repositories;

import org.projeto.gamelandia.simple.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
	
	
	public Client findByEmailCliente(String emailCliente);
	
}


