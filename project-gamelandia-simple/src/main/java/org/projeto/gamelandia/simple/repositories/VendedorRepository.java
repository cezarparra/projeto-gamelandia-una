package org.projeto.gamelandia.simple.repositories;

import org.projeto.gamelandia.simple.entity.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {

	public Vendedor findByEmail(String emailVendedor);

}
