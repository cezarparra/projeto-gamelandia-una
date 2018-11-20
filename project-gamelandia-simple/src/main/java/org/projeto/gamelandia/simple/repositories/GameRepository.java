package org.projeto.gamelandia.simple.repositories;

import org.projeto.gamelandia.simple.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {

	public Game findByNomeJogo(String nomeJogo);


}
