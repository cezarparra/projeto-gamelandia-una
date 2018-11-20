package org.wpattern.test.mutrack.simple.repositories;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.projeto.gamelandia.simple.entity.Vendedor;
import org.projeto.gamelandia.simple.repositories.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.wpattern.test.mutrack.simple.utils.AbstractTest;

public class VendedorRepositoryTest extends AbstractTest {

	private static final Logger LOGGER = Logger.getLogger(VendedorRepositoryTest.class);

	@Autowired
	private VendedorRepository vendedorRepository;

	@Test
	public void findAllTest() {
		List<Vendedor> vendedores = this.vendedorRepository.findAll();

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Test FindAll(): " + vendedores);
		}
	}

}
