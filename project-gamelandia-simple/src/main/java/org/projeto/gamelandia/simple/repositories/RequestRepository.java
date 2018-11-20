package org.projeto.gamelandia.simple.repositories;

import org.projeto.gamelandia.simple.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {

	Request findByCustomerName(String customerName);

}
