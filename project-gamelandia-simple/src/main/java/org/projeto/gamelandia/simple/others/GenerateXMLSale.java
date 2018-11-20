package org.projeto.gamelandia.simple.others;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.projeto.gamelandia.simple.entity.Console;
import org.projeto.gamelandia.simple.entity.Game;


/*
 * CLASSE RESPONSÁVEL POR GERAR ARQUIVOS XML COM OS PARÂMETROS (DADOS) RECEBIDOS DA VENDA
 * ATRAVÉS DA LOJA 
 * 
 */
public class GenerateXMLSale {
	
	public void generateXMLSale(String nomeVendedor, String nomeCliente, List<Game> jogos, List<Console> consoles,
			String formPagamento, int quantidade, int tempoGarantia, String dataVenda, String emailCliente) {
		
		/* Pega os dados da venda realizada e adiciona informações
		 * para depois gerar o arquivo XML utilizando a api JDOM 
		 */

		Element venda = new Element("DadosVenda");

		Document documento = new Document(venda);

		Element vendedorName = new Element("NomeVendedor");
		vendedorName.setText(nomeVendedor);

		Element customerName = new Element("NomeCliente");
		customerName.setText(nomeCliente);

		Element emailCustomer = new Element("EmailCliente");
		emailCustomer.setText(emailCliente);
		
		Game games = null;
		Console consolesObj = null;
		
		List<String> jogosXML = new ArrayList<String>();
		List<String> consoleXML = new ArrayList<String>();
		List<String> precoXML = new ArrayList<String>();
		
		List<String> consolesXML = new ArrayList<String>();
		List<String> precoConsoleXML = new ArrayList<String>();
		
		for (int i = 0; i < jogos.size(); i++) {
			games = jogos.get(i);
			jogosXML.add(games.getNomeJogo());
			consoleXML.add(games.getConsole());
			precoXML.add(String.valueOf(games.getPreco()));
		};
		
		for (int i = 0; i < consoles.size(); i++) {
			consolesObj = consoles.get(i);
			consolesXML.add(consolesObj.getNomeConsole());
		};

		Element gameName = new Element("Jogos");
		gameName.setText(jogosXML.toString());
		
		
		Element consoleName = new Element("Console");
		consoleName.setText(consoleXML.toString());

		Element precoSale = new Element("preco");
		precoSale.setText("R$" + precoXML.toString());
		
		Element nomeConsole = new Element("NomeConsole");
		nomeConsole.setText(consolesXML.toString());
		
		Element formPayment = new Element("Pagamento");
		formPayment.setText(formPagamento);

		Element qtde = new Element("Quantidade");
		qtde.setText(String.valueOf(quantidade));

		Element garantia = new Element("TempoGarantia");
		garantia.setText(String.valueOf(tempoGarantia));

		Element dtVenda = new Element("DataVenda");
		dtVenda.setText(dataVenda);

		venda.addContent(vendedorName);

		venda.addContent(customerName);

		venda.addContent(consoleName);

		venda.addContent(precoSale);

		venda.addContent(gameName);
		
		venda.addContent(nomeConsole);

		venda.addContent(formPayment);

		venda.addContent(qtde);

		venda.addContent(garantia);

		XMLOutputter xout = new XMLOutputter();

		try {
			FileWriter arquivo = new FileWriter(new File("c:/temp/notaFiscalCliente.xml"));
			xout.output(documento, arquivo);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return;
	}
}