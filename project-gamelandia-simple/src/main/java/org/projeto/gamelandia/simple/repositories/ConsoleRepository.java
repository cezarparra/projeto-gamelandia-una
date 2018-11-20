package org.projeto.gamelandia.simple.repositories;

import org.projeto.gamelandia.simple.entity.Console;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsoleRepository extends JpaRepository<Console, Long> {

	public Console findByNomeConsole(String nomeConsole);
}
