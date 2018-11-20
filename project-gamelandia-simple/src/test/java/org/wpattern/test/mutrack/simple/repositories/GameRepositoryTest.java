package org.wpattern.test.mutrack.simple.repositories;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.projeto.gamelandia.simple.entity.Game;
import org.projeto.gamelandia.simple.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.wpattern.test.mutrack.simple.utils.AbstractTest;

public class GameRepositoryTest extends AbstractTest {

	private static final Logger LOGGER = Logger.getLogger(GameRepositoryTest.class);

	@Autowired
	private GameRepository gameRepository;
	
	

	@Test
	public void findAllTest() {
		List<Game> game = this.gameRepository.findAll();

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Test FindAll(): " + game);
		}
	}

}


