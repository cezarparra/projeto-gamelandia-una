package org.projeto.gamelandia.simple.others;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.projeto.gamelandia.simple.entity.Console;
import org.projeto.gamelandia.simple.entity.Game;

/*
 * 
 * CLASSE RESPONSÁVEL PARA ENVIO DE EMAILS, COM OS DADOS DA VENDA VIA LOJA
 * QUE NO QUAL É INSTÂNCIADA PELA CLASSE SaleService.java
 */
public class SendSaleEmail {

	public SendSaleEmail() throws EmailException, MalformedURLException {
	}

	public void enviaEmailVenda(String nomeVendedor, String customerName, List<Game> jogosLista, List<Console> consoleLista, String formPayment,
			int quantidade, int tempoGarantia, String dataVenda, String emailCliente) throws EmailException {
		MultiPartEmail email = new MultiPartEmail();
		
		Game games = null;
		Console consoles = null;

		try {
			if (StringUtils.contains(emailCliente, "@gmail.com")) {
				email.setHostName("smtp.gmail.com");
				email.setSmtpPort(465);
			} else if (StringUtils.contains(emailCliente, "@bol.com.br")) {
				email.setHostName("smtps.bol.com.br");
				email.setSmtpPort(587);
			} else if (StringUtils.contains(emailCliente, "@outlook.com")) {
				email.setHostName("smtp.live.com");
				email.setSmtpPort(587);
			}

			StringBuilder sbSelect = new StringBuilder();
			int precoTotal = 0;

			sbSelect.append("Obrigado, sr.(a) " + customerName + " pela preferência por comprar na Gamelândia");
			sbSelect.append("\nSegue abaixo os dados do seu pedido");

			sbSelect.append("\nJogos: ");
			for (int i = 0; i < jogosLista.size(); i++) {
				games = jogosLista.get(i);
				sbSelect.append("\n" + games.getNomeJogo() + " - " + games.getConsole());
				precoTotal = (precoTotal + games.getPreco());
				
				if(!consoleLista.isEmpty()) {
					sbSelect.append("\nConsoles: ");
					for (int k = 0; k < consoleLista.size(); k++) {
						consoles = consoleLista.get(k);
						sbSelect.append("\n" + consoles.getNomeConsole());
						precoTotal = (precoTotal + consoles.getPrecoConsole().intValue());
					};	
				}
				
				
			};
			
			


			sbSelect.append("\nPreço Total: " + "R$" + precoTotal + ".00");

			sbSelect.append("\nData Compra: " + dataVenda);

			String pathFile = "c:/temp/notaFiscalCliente.xml";
			String description = "Nota Fiscal da Venda";
			EmailAttachment anexoNota = new EmailAttachment();
			anexoNota.setPath(pathFile);
			anexoNota.setDisposition(EmailAttachment.ATTACHMENT);
			anexoNota.setDescription(description);
			anexoNota.setName("notaFiscal.xml");
			email.attach(anexoNota);
			email.addTo(emailCliente, customerName); // destinatario
			email.setSubject("Dados da sua compra diretamente na Loja Gamelândia");
			email.setFrom("unknowsname4@gmail.com", "Gamelândia Venda de Games");// remetente
			email.setMsg(sbSelect.toString()); // conteudo do e-mail
			email.setAuthentication("unknowsname4@gmail.com", "Wdo6tA%O?7yd");
			email.setSSL(true);
			email.setTLS(true);
			email.send();

		} catch (EmailException e) {
			e.printStackTrace();
		}

	}

}
