package org.projeto.gamelandia.simple.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.transaction.Transactional;

import org.projeto.gamelandia.simple.entity.Game;
import org.projeto.gamelandia.simple.entity.Request;
import org.projeto.gamelandia.simple.repositories.GameRepository;
import org.projeto.gamelandia.simple.repositories.RequestRepository;
import org.projeto.gamelandia.simple.utils.ServicePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.uol.pagseguro.domain.Address;
import br.com.uol.pagseguro.domain.Item;
import br.com.uol.pagseguro.domain.PaymentRequest;
import br.com.uol.pagseguro.domain.Phone;
import br.com.uol.pagseguro.domain.Sender;
import br.com.uol.pagseguro.domain.SenderDocument;
import br.com.uol.pagseguro.enums.Currency;
import br.com.uol.pagseguro.enums.DocumentType;
import br.com.uol.pagseguro.enums.ShippingType;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.properties.PagSeguroConfig;


/*
 * CLASSE RESPONSÁVEL POR ADICIONAR, REMOVER ITENS NO CARRINHO, REALIZAR CHECKOUT (PAGAMENTO)
 * VIA PAGSEGURO
 * 
 */
@RestController
@Transactional
@RequestMapping(path = ServicePath.CART_PATH)
public class CartService {

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private RequestRepository requestRepository;

	private List<Game> carrinho = new ArrayList<Game>();
	private List<Game> carrinhoVendas = new ArrayList<Game>();

	@RequestMapping(path = "/addCart", method = RequestMethod.POST)
	public List<Game> insert(@RequestBody Game game) {
		carrinho.add(game);
		return carrinho;
	}
	
	@RequestMapping(path = "/getCarrinho", method = RequestMethod.GET)
	public List<Game> getCarrinho() {
		List<Game> items = new ArrayList<Game>();
		items.addAll(carrinhoVendas);
		return items;
	}

	@RequestMapping(path = "/checkoutItems", method = RequestMethod.POST)
	public @ResponseBody Request updStock(@RequestParam("gameid[]") List<Long> gameid) throws SQLException, PagSeguroServiceException {

		String url = "jdbc:mysql://localhost:3306/desjavagame?autoReconnect=true&useSSL=false";
		String user = "root";
		String password = "123456";

		Connection conexao = null;
		conexao = DriverManager.getConnection(url, user, password);
		Statement st = conexao.createStatement();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();

		ResultSet rs = st.executeQuery("select * from cliente where NOME_CLIENTE = " + "'" + name + "'");

		String nome = null;
		String rua = null;
		String bairro = null;
		String cidade = null;
		String estado = null;
		String num = null;
		String cpf = null;
		String email = null;
		Long idGame = null;

		if (rs.next()) {
			nome = rs.getString("NOME_CLIENTE");
			rua = rs.getString("END_RUA_CLIENTE");
			bairro = rs.getString("END_BAIRRO_CLIENTE");
			cidade = rs.getString("END_CIDADE_CLIENTE");
			estado = rs.getString("END_ESTADO_CLIENTE");
			num = rs.getString("end_numero_cliente");
			cpf = rs.getString("CPF");
			email = rs.getString("EMAIL_CLIENTE");
		}

		StringTokenizer emailSandBox = new StringTokenizer(email);

		String emailSdx = "@sandbox.pagseguro.com.br";

		PaymentRequest requestPayment = new PaymentRequest();

		for (Long games : gameid) {
			idGame = games.longValue();

			Game findGame = gameRepository.findOne(idGame);
			String id = Long.toString(findGame.getId());
			String descricao = findGame.getNomeJogo();
			BigDecimal valor = BigDecimal.valueOf(findGame.getPreco()).setScale(2, BigDecimal.ROUND_HALF_UP);

			int atualizarEstoque = (findGame.getQtdeEstoque() - Integer.valueOf(1));

			if (atualizarEstoque < 0) {
				throw new RuntimeException("O jogo " + descricao + " encontra-se com estoque indisponível. Por favor revise a sua compra!");
			} else {
				requestPayment.addItem(new Item(id, descricao, Integer.valueOf(1), valor));
			}

		}

		requestPayment.setSender(new Sender(nome, emailSandBox.nextToken("@") + emailSdx, new Phone("", ""),
				new SenderDocument(DocumentType.CPF, cpf)));
		requestPayment.setShippingAddress(new Address("BRA", estado, cidade, bairro, "38408714", rua, num, "Casa"));

		requestPayment.setCurrency(Currency.BRL);

		requestPayment.setShippingType(ShippingType.SEDEX);

		// CALCULA FRETE
		requestPayment.setShippingCost(new BigDecimal("5.00"));

		Boolean onlyCheckoutCode = true;
		
		String code = requestPayment.register(PagSeguroConfig.getAccountCredentials(), onlyCheckoutCode);

		String paymentURL = "https://sandbox.pagseguro.uol.com.br/v2/checkout/payment.html?code=" + code;
		carrinho.removeAll(carrinho);

		Date dataAtual = new Date();
		String dataVenda = new SimpleDateFormat("dd/MM/yyyy").format(dataAtual);

		Request request = new Request();
		request.setCustomerName(nome);
		request.setStatus("Aguardando Pagamento");
		request.setUrl(paymentURL);
		request.setTransactionCode(code);
		request.setDateRequest(dataVenda);
		request.setGenNote("Não");

		requestRepository.save(request);

		return request;
	}

	@RequestMapping(path = "/updStockGame", method = RequestMethod.GET)
	public List<Request> findAll() {
		return requestRepository.findAll();
	}

	@RequestMapping(path = "/addCart", method = RequestMethod.GET)
	public List<Game> findCart() {
		List<Game> items = new ArrayList<Game>();

		items.addAll(carrinho);

		return items;
	}

	@RequestMapping(path = "/addCart", method = RequestMethod.DELETE)
	public void deleteItem(@RequestBody Game item) {
		carrinho.remove(item);
		return;
	}

}