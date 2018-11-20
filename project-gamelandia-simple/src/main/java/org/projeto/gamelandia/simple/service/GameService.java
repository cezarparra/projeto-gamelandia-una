package org.projeto.gamelandia.simple.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.transaction.Transactional;

import org.projeto.gamelandia.simple.entity.Game;
import org.projeto.gamelandia.simple.repositories.GameRepository;
import org.projeto.gamelandia.simple.utils.GenericService;
import org.projeto.gamelandia.simple.utils.ServicePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/*
 * CLASSE RESPONSÁVEL POR PESQUISAR O JOGO CADASTRADO
 */
@RestController
@Service
@Transactional
@RequestMapping(path = ServicePath.GAME_PATH)
public class GameService extends GenericService<Game, Long> {

	@Autowired
	private GameRepository gameRepository;

	public List<Game> findAll() {
		return gameRepository.findAll();
	}

	String url = "jdbc:mysql://localhost:3306/desjavagame?autoReconnect=true&useSSL=false";
	private String user = "root";
	private String password = "123456";

	@RequestMapping(path = "/searchGame", method = RequestMethod.GET)
	public Game findByName(@RequestParam("gameSearched") String gameSearched) throws SQLException, RuntimeException {

		Game gameResult = null;

		Connection conexao = DriverManager.getConnection(url, user, password);
		Statement st = conexao.createStatement();

		ResultSet rs = st
				.executeQuery("SELECT NOME_JOGO FROM game WHERE NOME_JOGO LIKE" + "'" + "%" + gameSearched + "%" + "'");

		if (rs.next()) {
			gameSearched = rs.getString("NOME_JOGO");
			gameResult = gameRepository.findByNomeJogo(gameSearched);
		} else {
			throw new RuntimeException("Erro");
		}

		return gameResult;

	}

	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Game findById(@PathVariable("id") Long id) {
		return gameRepository.findOne(id);
	}

}
