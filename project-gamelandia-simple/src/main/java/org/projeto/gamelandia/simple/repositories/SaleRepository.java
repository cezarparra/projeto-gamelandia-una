package org.projeto.gamelandia.simple.repositories;

import org.projeto.gamelandia.simple.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
	
}
