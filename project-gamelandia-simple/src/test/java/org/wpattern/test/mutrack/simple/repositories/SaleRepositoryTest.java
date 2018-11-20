package org.wpattern.test.mutrack.simple.repositories;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.projeto.gamelandia.simple.entity.Sale;
import org.projeto.gamelandia.simple.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.wpattern.test.mutrack.simple.utils.AbstractTest;

public class SaleRepositoryTest extends AbstractTest {

	private static final Logger	LOGGER	= Logger.getLogger(SaleRepositoryTest.class);

	@Autowired
	private SaleRepository		saleRepository;

	@Test
	public void findAllTest() {
		List<Sale> sales = this.saleRepository.findAll();

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Test FindAll(): " + sales);
		}
	}

}
