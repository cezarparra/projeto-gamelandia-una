
package org.projeto.gamelandia.simple.others;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

import br.com.uol.pagseguro.domain.Transaction;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.properties.PagSeguroConfig;
import br.com.uol.pagseguro.service.TransactionSearchService;


/*
 * CLASSE RESPONSÁVEL POR GERAR ARQUIVOS XML COM OS PARÂMETROS (DADOS) RECEBIDOS DA VENDA
 * ATRAVÉS DO PAGSEGURO 
 * 
 */
public class GenerateXML {

	public static void transactionXML(String transactionCode) throws PagSeguroServiceException {
		
		/* Pega os dados da transação gerada pelo PagSeguro e adiciona informações
		 * para depois gerar o arquivo XML utilizando a api JDOM 
		 */

		Transaction transaction = null;

		transaction = TransactionSearchService.searchByCode(PagSeguroConfig.getAccountCredentials(), transactionCode);

		Element itemsCompra = null;

		Element dadosCompra = new Element("DadosCompra");

		Document documento = new Document(dadosCompra);

		Element dataBuy = new Element("DataCompra");

		dataBuy.setAttribute("code", transaction.getCode());

		for (int i = 0; i < transaction.getItems().size(); i++) {

			itemsCompra = new Element("id");
			itemsCompra.setText(transaction.getItems().get(i).getId());
			dadosCompra.addContent(itemsCompra);

			Element itemsDescription = new Element("description");
			itemsDescription.setText(transaction.getItems().get(i).getDescription());
			dadosCompra.addContent(itemsDescription);

			Element quantity = new Element("gameid");
			quantity.setText(transaction.getItems().get(i).getQuantity().toString());
			dadosCompra.addContent(quantity);

			Element valorItem = new Element("valorItem");
			valorItem.setText("R$" + transaction.getItems().get(i).getAmount().toString());
			dadosCompra.addContent(valorItem);

		}

		Element customerName = new Element("nomeCliente");

		customerName.setText(transaction.getSender().getName());

		Element customerEmail = new Element("emailCliente");

		customerEmail.setText(transaction.getSender().getEmail());

		Element tipoEnvio = new Element("tipoEnvio");

		tipoEnvio.setText(transaction.getShipping().getType().toString());

		Element custoFrete = new Element("custoFrete");

		custoFrete.setText(transaction.getShipping().getCost().toString());

		Element valorTotal = new Element("valorTotal");

		valorTotal.setText("R$" + transaction.getGrossAmount().toString());

		Element taxa = new Element("taxa");

		taxa.setText("R$" + transaction.getFeeAmount().toString());

		dadosCompra.addContent(valorTotal);

		dadosCompra.addContent(taxa);

		dadosCompra.addContent(customerName);

		dadosCompra.addContent(customerEmail);

		dadosCompra.addContent(tipoEnvio);

		dadosCompra.addContent(custoFrete);

		XMLOutputter xout = new XMLOutputter();

		try {
			
			//FileWriter : UTILIZADO PARA CRIAR O ARQUIVO 
			
			FileWriter arquivo = new FileWriter(new File("c:/temp/arquivo.xml"));
			xout.output(documento, arquivo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;

	}
	
}
