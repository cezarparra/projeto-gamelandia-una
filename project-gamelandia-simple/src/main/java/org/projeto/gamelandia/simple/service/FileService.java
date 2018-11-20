package org.projeto.gamelandia.simple.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;

import org.projeto.gamelandia.simple.entity.Console;
import org.projeto.gamelandia.simple.entity.Game;
import org.projeto.gamelandia.simple.repositories.ConsoleRepository;
import org.projeto.gamelandia.simple.repositories.GameRepository;
import org.projeto.gamelandia.simple.utils.ServicePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
 * CLASSE RESPONSÁVEL POR PESQUISAR O JOGO CADASTRADO
 */
@RestController
@RequestMapping(ServicePath.FILEUP_PATH)
public class FileService {

	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private ConsoleRepository consoleRepository;

	@RequestMapping(method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public Game handleFileUpload(@RequestParam(value = "file") String file,
			@RequestParam(value = "nomeJogo") String nomeJogo, @RequestParam(value = "qtdeEstoque") String qtdeEstoque,
			@RequestParam(value = "statusJogo") String statusJogo,
			@RequestParam(value = "anoLancamento") String anoLancamento, @RequestParam(value = "preco") String preco,
			@RequestParam(value = "console") String console) throws IOException, NoSuchAlgorithmException {

		Game newGame = new Game();

		if (!file.equals("null")) {
			newGame.setNomeJogo(nomeJogo);
			newGame.setQtdeEstoque(Integer.parseInt(qtdeEstoque));
			newGame.setStatusJogo(statusJogo);
			newGame.setAnoLancamento(Integer.parseInt(anoLancamento));
			newGame.setConsole(console);
			newGame.setPreco(Integer.parseInt(preco));
			newGame.setFile(file);
		}

		return gameRepository.save(newGame);

	}
	
	
	@RequestMapping(path = "/cadastrarConsole", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public Console handleFileUploadConsole(
			@RequestParam(value = "file") String file,
			@RequestParam(value = "nomeConsole") String nomeConsole, 
			@RequestParam(value = "qtdeEstoque") String qtdeEstoque,
			@RequestParam(value = "statusConsole") String statusConsole,
			@RequestParam(value = "preco") String preco)
					throws IOException, NoSuchAlgorithmException {

		Console consoleGame = new Console();

		if (!file.equals("null")) {
			consoleGame.setArquivoConsole(file);
		}

		consoleGame.setNomeConsole(nomeConsole);
		consoleGame.setQtdeEstoque(Integer.parseInt(qtdeEstoque));
		consoleGame.setStatusConsole(statusConsole);
		consoleGame.setPrecoConsole(new BigDecimal(preco));

		return consoleRepository.save(consoleGame);

	}
	


	@RequestMapping(path = "/editGame", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public Game editarJogo(@RequestParam(value = "file") String file,
			@RequestParam(value = "nomeJogo") String nomeJogo, @RequestParam(value = "qtdeEstoque") String qtdeEstoque,
			@RequestParam(value = "statusJogo") String statusJogo,
			@RequestParam(value = "anoLancamento") String anoLancamento, @RequestParam(value = "preco") String preco,
			@RequestParam(value = "console") String console, @RequestParam(value = "id") Long id) throws IOException {

		Game editGame = gameRepository.findOne(id);

		if (!file.equals("null")) {
			editGame.setFile(file);
		}

		editGame.setNomeJogo(nomeJogo);
		editGame.setQtdeEstoque(Integer.parseInt(qtdeEstoque));
		editGame.setStatusJogo(statusJogo);
		editGame.setAnoLancamento(Integer.parseInt(anoLancamento));
		editGame.setConsole(console);
		editGame.setPreco(Integer.parseInt(preco));


		return gameRepository.save(editGame);

	}
	
	@RequestMapping(path = "/editConsole", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public String editarConsole(@RequestParam(value = "file") String file,
			@RequestParam(value = "nomeConsole") String nomeConsole, @RequestParam(value = "qtdeEstoque") String qtdeEstoque,
			@RequestParam(value = "statusConsole") String statusConsole,
			@RequestParam(value = "preco") String preco, @RequestParam(value = "id") Long id) throws IOException {

		Console consoleGame = consoleRepository.findOne(id);

		if (!file.equals("null")) {
			consoleGame.setArquivoConsole(file);
		}

		consoleGame.setNomeConsole(nomeConsole);
		consoleGame.setQtdeEstoque(Integer.parseInt(qtdeEstoque));
		consoleGame.setStatusConsole(statusConsole);
		consoleGame.setPrecoConsole(new BigDecimal(preco));

		consoleRepository.save(consoleGame);

		return null;

	}


}