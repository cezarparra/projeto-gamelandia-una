package org.projeto.gamelandia.simple.service;

import java.util.List;

import javax.transaction.Transactional;

import org.projeto.gamelandia.simple.entity.Console;
import org.projeto.gamelandia.simple.repositories.ConsoleRepository;
import org.projeto.gamelandia.simple.utils.GenericService;
import org.projeto.gamelandia.simple.utils.ServicePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Service
@Transactional
@RequestMapping(path = ServicePath.CONSOLE_PATH)
public class ConsoleService extends GenericService<Console, Long> {

	@Autowired
	private ConsoleRepository consoleRepository;

	public List<Console> findAll() {
		return consoleRepository.findAll();
	}


}
