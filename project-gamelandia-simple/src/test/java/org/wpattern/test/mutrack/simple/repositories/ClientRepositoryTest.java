package org.wpattern.test.mutrack.simple.repositories;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.projeto.gamelandia.simple.entity.Client;
import org.projeto.gamelandia.simple.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.wpattern.test.mutrack.simple.utils.AbstractTest;

public class ClientRepositoryTest extends AbstractTest {

	private static final Logger LOGGER = Logger.getLogger(ClientRepositoryTest.class);

	@Autowired
	private ClientRepository clientRepository;

	@Test
	public void findAllTest() {
		List<Client> clientes = this.clientRepository.findAll();

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Test FindAll(): " + clientes);
		}
	}

}


