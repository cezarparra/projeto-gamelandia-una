package org.projeto.gamelandia.simple.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.projeto.gamelandia.simple.entity.Sale;
import org.projeto.gamelandia.simple.entity.Vendedor;
import org.projeto.gamelandia.simple.others.ValidaCPF;
import org.projeto.gamelandia.simple.others.ValidaTelefone;
import org.projeto.gamelandia.simple.permission.PermissionEntity;
import org.projeto.gamelandia.simple.repositories.SaleRepository;
import org.projeto.gamelandia.simple.repositories.VendedorRepository;
import org.projeto.gamelandia.simple.utils.GenericService;
import org.projeto.gamelandia.simple.utils.ServicePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/*
 * CLASSE RESPONSÁVEL POR INSERÇÃO, DELEÇÃO DOS VENDEDORES
 * E TAMBÉM O FILTRO DAS VENDAS REALIZADAS ENTRE AS DATAS DIGITADAS NO SISTEMA
 */
@RestController
@Service
@Transactional
@RequestMapping(path = ServicePath.VENDEDOR_PATH)
public class VendedorService extends GenericService<Vendedor, Long> {

	@Autowired
	private VendedorRepository vendedorRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private SaleRepository saleRepository;

	String url = "jdbc:mysql://localhost:3306/desjavagame?autoReconnect=true&useSSL=false";
	String user = "root";
	String password = "123456";

	List<Object> salesBetween = new ArrayList<Object>();

	ValidaCPF validatorCPF = ValidaCPF.getInstance();
	ValidaTelefone validatorPhone = ValidaTelefone.getInstance();

	public List<Vendedor> findAll() {
		return vendedorRepository.findAll();
	}

	@Override
	public Vendedor insert(@RequestBody Vendedor newVendedor) {

		String CPFsemPontos = newVendedor.getCpfVendedor().replaceAll("[.-]", "");

		String celularSemPontosParenteses = newVendedor.getCelularVendedor().replaceAll("[()-]", "");
		String telefoneSemPontosParenteses = newVendedor.getTelefoneVendedor().replaceAll("[()-]", "");

		List<PermissionEntity> permissions = new ArrayList<PermissionEntity>();

		PermissionEntity entity = new PermissionEntity();

		entity.setId((long) 3);
		entity.setRole("ROLE_EMPLOYEE");

		permissions.add(entity);
		newVendedor.setPermissions(permissions);

		if (validatorCPF.isCPF(CPFsemPontos) == false) {
			throw new RuntimeException("Erro, CPF inválido");
		} else if (validatorPhone.validaCelular(celularSemPontosParenteses) == false) {
			throw new RuntimeException("Erro, Celular inválido");
		} else if (validatorPhone.validaTelefone(telefoneSemPontosParenteses) == false) {
			throw new RuntimeException("Erro, Telefone inválido");
		} else if (validatorCPF.repeteCPF(newVendedor.getCpfVendedor()) == false) {
			throw new RuntimeException("Erro, CPF já existente no sistema");
		} else {
			newVendedor.setPassword(this.passwordEncoder.encode(newVendedor.getPassword()));
			return super.insert(newVendedor);
		}
	}

	@Override
	public void update(@RequestBody Vendedor newVendedor) {

		String CPFsemPontos = newVendedor.getCpfVendedor().replaceAll("[.-]", "");

		String celularSemPontosParenteses = newVendedor.getCelularVendedor().replaceAll("[()-]", "");
		String telefoneSemPontosParenteses = newVendedor.getTelefoneVendedor().replaceAll("[()-]", "");

		if (validatorCPF.isCPF(CPFsemPontos) == false) {
			throw new RuntimeException("Erro, CPF inválido");
		} else if (validatorPhone.validaCelular(celularSemPontosParenteses) == false) {
			throw new RuntimeException("Erro, Celular inválido");
		} else if (validatorPhone.validaTelefone(telefoneSemPontosParenteses) == false) {
			throw new RuntimeException("Erro, Telefone inválido");
		} else {
			newVendedor.setPassword(this.passwordEncoder.encode(newVendedor.getPassword()));
			super.insert(newVendedor);
		}

		return;
	}

	@RequestMapping(path = "/findSaleDateBetween", method = RequestMethod.GET)
	public List<Object> buscaRelatoriosEntreData(@RequestParam("data1") String dataDate1,
			@RequestParam("data2") String dataDate2) throws SQLException {
		Sale saleData = null;
		try {
			Long saleID = null;
			Connection conexao = null;
			conexao = DriverManager.getConnection(url, user, password);
			Statement st = conexao.createStatement();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT ID_VENDA FROM realiza_venda");
			sb.append("  where DT_VENDA BETWEEN ");
			sb.append("'");
			sb.append(dataDate1);
			sb.append("'");
			sb.append(" AND ");
			sb.append("'");
			sb.append(dataDate2);
			sb.append("'");
			ResultSet rs = st.executeQuery(sb.toString());
			if (!salesBetween.isEmpty()) {
				salesBetween.removeAll(salesBetween);
			}
			while (rs.next()) {
				saleID = rs.getLong("ID_VENDA");
				saleData = saleRepository.findOne(saleID);
				salesBetween.add(saleData);
			}
			conexao.close();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return salesBetween;
	}

	@RequestMapping(path = "/gerarRelatorioPDF", method = RequestMethod.GET)
	public List<Object> gerarRelatorioPDF(@RequestParam("vendaID[]") List<Long> vendaID,
			@RequestParam("montanteVenda") Integer montanteVenda, @RequestParam("quantidadeTotal") Integer qtdeTotal,
			@RequestParam("listaJogos[]") List<String> listaJogos,
			@RequestParam("listaConsole[]") List<String> listaConsoles)
			throws SQLException, DocumentException, MalformedURLException, IOException {

		Document document = new Document();
		try {
			File arquivoRelatorio = new File("C:\\temp\\RelatorioVendasGamelandia.pdf");
			PdfWriter.getInstance(document, new FileOutputStream(arquivoRelatorio));
			document.open();

			// adicionando um parágrafo no documento

			Sale vendaRelatorio = new Sale();

			Iterator<Long> it = vendaID.iterator();
			
			document.add(new Paragraph("Relatório de Vendas"));

			document.add(new Paragraph("==========================================================="));

			while (it.hasNext()) {

				for (String games : listaJogos) {
					document.add(new Paragraph("Jogo: " + games));
				}
				
				for (String consoles : listaConsoles) {
					document.add(new Paragraph("Console: " + consoles));
				}
				Long element = (Long) it.next();
				vendaRelatorio = saleRepository.findOne(element);

				document.add(new Paragraph("Nome do Cliente: " + vendaRelatorio.getCliente().getName()));
				document.add(new Paragraph("Nome do Vendedor: " + vendaRelatorio.getVendedor().getName()));
				document.add(new Paragraph("Data da Venda: " + vendaRelatorio.getDataVenda()));
				document.add(new Paragraph("Preço: " + "R$ " + vendaRelatorio.getPrecoVenda() + ".00"));
				document.add(new Paragraph("==========================================================="));
			}
			document.add(new Paragraph("MONTANTE TOTAL: " + "R$ " + montanteVenda + ".00"));
			document.add(new Paragraph("QUANTIDADE TOTAL: " + qtdeTotal));

		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			document.close();
		}

		return null;

	}

}
