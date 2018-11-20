package org.projeto.gamelandia.simple.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.projeto.gamelandia.simple.entity.Game;
import org.projeto.gamelandia.simple.entity.Request;
import org.projeto.gamelandia.simple.others.GenerateXML;
import org.projeto.gamelandia.simple.others.SendRequestEmail;
import org.projeto.gamelandia.simple.repositories.GameRepository;
import org.projeto.gamelandia.simple.repositories.RequestRepository;
import org.projeto.gamelandia.simple.utils.GenericService;
import org.projeto.gamelandia.simple.utils.ServicePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.uol.pagseguro.domain.Transaction;
import br.com.uol.pagseguro.enums.TransactionType;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.properties.PagSeguroConfig;
import br.com.uol.pagseguro.service.TransactionSearchService;


/*
 * CLASSE RESPONSÁVEL POR GERAR O ARQUIVO XML, E BUSCAR OS CÓDIGOS DAS TRANSAÇÕES PELO SITE DO PAGSEGURO
 * E TAMBÉM ATUALIZA O ESTOQUE DE JOGOS CADASTRADOS NO SISTEMA
 */
@RestController
@Service
@Transactional
@RequestMapping(path = ServicePath.REQUEST_PATH)
public class RequestsService extends GenericService<Request, Long> {

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private RequestRepository requestRepository;

	private boolean hasGenerateNote = true;

	private Long idCustomer;

	private Long idLastRequest;

	String url = "jdbc:mysql://localhost:3306/desjavagame?autoReconnect=true&useSSL=false";
	private String user = "root";
	private String password = "123456";

	@RequestMapping(path = "/infoBuy", method = RequestMethod.GET)
	public @ResponseBody Transaction infoBuy(@RequestParam("code") String code)
			throws SQLException, PagSeguroServiceException {

		Transaction transaction = null;
		Integer quantidade = null;
		String descricao = null;
		BigDecimal valorItem = null;

		transaction = TransactionSearchService.searchByCode(PagSeguroConfig.getAccountCredentials(), code);

		String dataVenda = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(transaction.getDate());

		List<Integer> arrayQuantidade = new ArrayList<Integer>();
		List<String> arrayDescricao = new ArrayList<String>();
		List<BigDecimal> arrayValorItem = new ArrayList<BigDecimal>();

		if (transaction.getStatus().getValue() == 1) {
			throw new RuntimeException(
					"Erro ! Motivo: O Cliente" + transaction.getSender().getName() + "ainda não efetuou o pagamento");
		}

		findCustomerRequest(transaction.getSender().getName());

		if (hasGenerateNote) {

			for (int i = 0; i < transaction.getItems().size(); i++) {
				if (transaction.getStatus().getValue() == 3) {
					quantidade = transaction.getItems().get(i).getQuantity();

					arrayQuantidade.add(quantidade);

					descricao = transaction.getItems().get(i).getDescription();
					arrayDescricao.add(descricao);

					valorItem = transaction.getItems().get(i).getAmount();

					arrayValorItem.add(valorItem);

					atualizaEstoque(transaction.getItems().get(i).getDescription());

				} else {
					throw new RuntimeException("Erro ! Motivo: Nota já foi gerada");
				}

			}

			Request requestData = requestRepository.findOne(idCustomer);

			requestData.setTransactionCode(code);
			requestData.setStatus("Pago");
			requestData.setGenerateNote(true);
			requestData.setGenNote("Sim");

			requestRepository.save(requestData);
		} else {
			throw new RuntimeException("Nota já foi gerada");
		}

		GenerateXML.transactionXML(code);

		enviarNota(transaction.getSender().getName(), transaction.getType(),arrayDescricao, arrayQuantidade, valorItem,transaction.getNetAmount(), dataVenda);

		return transaction;
	}

	public String findCustomerRequest(String customerName) {
		try {

			Connection conexao = DriverManager.getConnection(url, user, password);
			Statement st = conexao.createStatement();

			Connection conexao2 = DriverManager.getConnection(url, user, password);
			Statement st2 = conexao.createStatement();

			ResultSet rs = st.executeQuery("select * from request where customer_name = " + "'" + customerName + "'");

			if (rs.next()) {
				idCustomer = rs.getLong("request_id");
			}

			ResultSet rs2 = st2.executeQuery("SELECT MAX(request_id) as LastRegistry FROM request");

			if (rs2.next()) {
				idLastRequest = rs2.getLong("LastRegistry");
				Request requestData = requestRepository.findOne(idLastRequest);
				requestData.setStatus("Pago");
				if (requestData.getGenNote().equals("Não")) {
					hasGenerateNote = true;
					requestData.setGenNote("Sim");
					requestRepository.save(requestData);
				} else {
					hasGenerateNote = false;
				}
			}

			conexao.close();
			conexao2.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return customerName;
	}

	public void atualizaEstoque(String gameName) {

		try {
			Connection conexao = null;
			conexao = DriverManager.getConnection(url, user, password);
			Statement st = conexao.createStatement();

			ResultSet rs = st.executeQuery("select * from game where NOME_JOGO = " + "'" + gameName + "'");

			if (rs.next()) {
				Long id = rs.getLong("ID_GAME");
				Game games = gameRepository.findOne(id);
				int updQdy = (games.getQtdeEstoque() - 1);
				games.setQtdeEstoque(updQdy);
				gameRepository.save(games);
			}

			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return;
	}

	public void enviarNota(String customername, TransactionType type, List<String> arrayDescricao,
			List<Integer> arrayQuantidade, BigDecimal valorItem, BigDecimal netAmount, String dataVenda) {

		try {

			Connection conexao = null;
			conexao = DriverManager.getConnection(url, user, password);
			Statement st = conexao.createStatement();

			ResultSet rs = st.executeQuery("select * from cliente where NOME_CLIENTE = " + "'" + customername + "'");

			if (rs.next()) {

				String emailCustomer = rs.getString("EMAIL_CLIENTE");
				SendRequestEmail emailSend = new SendRequestEmail();

				emailSend.enviaEmailSimples(customername, emailCustomer, arrayDescricao, valorItem, netAmount,
						dataVenda);

			}
			conexao.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}