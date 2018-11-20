package org.projeto.gamelandia.simple.service;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.mail.EmailException;
import org.projeto.gamelandia.simple.entity.Client;
import org.projeto.gamelandia.simple.entity.Console;
import org.projeto.gamelandia.simple.entity.Game;
import org.projeto.gamelandia.simple.entity.Sale;
import org.projeto.gamelandia.simple.entity.Vendedor;
import org.projeto.gamelandia.simple.others.GenerateXMLSale;
import org.projeto.gamelandia.simple.others.SendSaleEmail;
import org.projeto.gamelandia.simple.repositories.ClientRepository;
import org.projeto.gamelandia.simple.repositories.ConsoleRepository;
import org.projeto.gamelandia.simple.repositories.GameRepository;
import org.projeto.gamelandia.simple.repositories.SaleRepository;
import org.projeto.gamelandia.simple.repositories.VendedorRepository;
import org.projeto.gamelandia.simple.utils.GenericService;
import org.projeto.gamelandia.simple.utils.ServicePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/*
 * CLASSE RESPONSÁVEL PARA REALIZAÇÃO DAS VENDAS, E TAMBÉM O CÁLCULO DAS COMISSÕES DOS VENDEDORES 
 * E ATUALIZAÇÃO DE ESTOQUE DOS JOGOS CADASTRADOS
 */
@RestController
@Service
@Transactional
@RequestMapping(path = ServicePath.SALE_PATH)
public class SaleService extends GenericService<Sale, Long> {

	@Autowired
	private SaleRepository saleRepository;

	@Autowired
	private VendedorRepository vendedorRepository;

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private ConsoleRepository consoleRepository;

	@Autowired
	private ClientRepository clientRepository;

	private List<Game> carrinhoVendas = new ArrayList<Game>();
	private List<Console> carrinhoVendasConsole = new ArrayList<Console>();

	public Vendedor findByIdVendedor(Long id) {
		return vendedorRepository.findOne(id);
	}

	public Game findByIdGame(Long id) {
		return gameRepository.findOne(id);
	}

	public List<Sale> findAll() {
		return saleRepository.findAll();
	}

	public Sale findById(Long id) {
		return saleRepository.findOne(id);
	}

	public Client findByIdClient(Long id) {
		return clientRepository.findOne(id);
	}

	@Override
	public Sale insert(@RequestBody Sale sales) {

		GenerateXMLSale xmlSale = new GenerateXMLSale();
		sales.setGame(carrinhoVendas);

		sales.setConsole(carrinhoVendasConsole);

		Game games = null;
		Console console = null;
		
		int consolePrecoTotal = 0;
		int subTotal = 0;

		try {

			String url = "jdbc:mysql://localhost:3306/desjavagame?autoReconnect=true&useSSL=false";
			String user = "root";
			String password = "123456";

			Connection conexao = null;
			conexao = DriverManager.getConnection(url, user, password);
			Statement st = conexao.createStatement();

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String name = auth.getName();

			Date dataAtual = new Date();
			String dataVenda = new SimpleDateFormat("dd/MM/yyyy").format(dataAtual);

			sales.setDataVenda(dataVenda);

			Long clientID = sales.getCliente().getId();
			Client client = clientRepository.findOne(clientID);
			sales.setCliente(client);

			ResultSet rs = st.executeQuery("select * from vendedor where name = " + "'" + name + "'");

			if (rs.next()) {
				Long idVendedor = rs.getLong("ID_VENDEDOR");
				Vendedor vendedor = vendedorRepository.findOne(idVendedor);
				sales.setVendedor(vendedor);

			}
			conexao.close();

			int atualizarEstoque = 0;

			if (!carrinhoVendas.isEmpty()) {
				List<Game> game = sales.getGame();
				for (int i = 0; i < game.size(); i++) {
					games = game.get(i);
					atualizarEstoque = (games.getQtdeEstoque() - 1);
					games.setQtdeEstoque(atualizarEstoque);
					if(games.getQtdeEstoque() <= 0) {
						throw new RuntimeException("O jogo " + games.getNomeJogo() + " encontra-se com estoque indisponível.");
					}else {
						subTotal = (subTotal + games.getPreco());
						gameRepository.save(game);	
					}
					
				}
				;

			}

			if (!carrinhoVendasConsole.isEmpty()) {
				List<Console> consoles = sales.getConsole();
				for (int i = 0; i < consoles.size(); i++) {
					console = consoles.get(i);
					atualizarEstoque = (console.getQtdeEstoque() - 1);
					console.setQtdeEstoque(atualizarEstoque);
					if(console.getQtdeEstoque() <= 0) {
						throw new RuntimeException("O jogo " + console.getNomeConsole() + " encontra-se com estoque indisponível.");
					}else {
						subTotal = (subTotal + console.getPrecoConsole().intValue());
						consoleRepository.save(console);
					}
					consolePrecoTotal = console.getPrecoConsole().intValue();
				};
			}

			Vendedor v = vendedorRepository.findOne(sales.getVendedor().getId());

			v.setTotalQtdeVendida((v.getTotalQtdeVendida()));
			
			
			v.setTotalPrecoVendido((games.getPreco() + consolePrecoTotal));
			double comissao = ((games.getPreco() + consolePrecoTotal)
					* v.getPorcSalario() / 100);
			v.setComissao(comissao);

			sales.setPrecoVenda(subTotal);

			vendedorRepository.save(v);

			xmlSale.generateXMLSale(sales.getVendedor().getName(), sales.getCliente().getName(), sales.getGame(),
					sales.getConsole(), sales.getFormPayment(), sales.getQuantidade(), sales.getTempoGarantia(),
					sales.getDataVenda(), sales.getCliente().getEmailCliente());

			try {
				SendSaleEmail sendEmail = new SendSaleEmail();

				sendEmail.enviaEmailVenda(sales.getVendedor().getName(), sales.getCliente().getName(), sales.getGame(),
						sales.getConsole(), sales.getFormPayment(), sales.getQuantidade(), sales.getTempoGarantia(),
						sales.getDataVenda(), sales.getCliente().getEmailCliente());
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (EmailException e1) {
				e1.printStackTrace();
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return super.insert(sales);

	}

	@RequestMapping(path = "/addCartSale", method = RequestMethod.POST)
	public List<Game> insertCartSale(@RequestBody Game game) {

		carrinhoVendas.add(game);

		return carrinhoVendas;
	}

	@RequestMapping(path = "/adicionarConsoleCarrinho", method = RequestMethod.POST)
	public List<Console> inserirItemConsole(@RequestBody Console console) {

		carrinhoVendasConsole.add(console);

		return carrinhoVendasConsole;
	}

	@RequestMapping(path = "/adicionarCarrinho", method = RequestMethod.GET)
	public List<Game> findCart() {
		List<Game> items = new ArrayList<Game>();

		items.addAll(carrinhoVendas);

		return items;
	}

	@RequestMapping(path = "/getItensConsole", method = RequestMethod.GET)
	public List<Console> findCartConsole() {
		List<Console> items = new ArrayList<Console>();

		items.addAll(carrinhoVendasConsole);

		return items;
	}

	@RequestMapping(path = "/deletarItem", method = RequestMethod.DELETE)
	public void deleteItem(@RequestBody Game item) {
		carrinhoVendas.remove(item);
		return;
	}

	@RequestMapping(path = "/deletarItemConsole", method = RequestMethod.DELETE)
	public void deleteItem(@RequestBody Console item) {
		carrinhoVendasConsole.remove(item);
		return;
	}

	public SaleService() {

	}

}
