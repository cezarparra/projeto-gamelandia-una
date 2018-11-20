package org.projeto.gamelandia.simple.others;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;


/*
 * 
 * CLASSE RESPONSÁVEL PARA ENVIO DE EMAILS, COM OS DADOS DA VENDA VIA PAGSEGURO
 * QUE NO QUAL É INSTÂNCIADA PELA CLASSE RequestService.java
 */

public class SendRequestEmail {
	public SendRequestEmail() throws EmailException, MalformedURLException {
	}

	public void enviaEmailSimples(String customername, String emailCliente, List<String> arrayDescricao,
			BigDecimal valorItem, BigDecimal netAmount, String dataVenda) throws EmailException {
		MultiPartEmail email = new MultiPartEmail();
		
		// Prepara os dados para envio de emails

		try {
			if (StringUtils.contains(emailCliente, "@gmail.com")) {
				email.setHostName("smtp.gmail.com"); // o servidor SMTP para envio do e-mail
				email.setSmtpPort(465); // Porta SMTP para envio do e-email
			} else if (StringUtils.contains(emailCliente, "@bol.com.br")) {
				email.setHostName("smtps.bol.com.br"); // o servidor SMTP para envio do e-mail
				email.setSmtpPort(587);
			} else if (StringUtils.contains(emailCliente, "@outlook.com")) {
				email.setHostName("smtp.live.com");
				email.setSmtpPort(587);
			}

			StringBuilder sbSelect = new StringBuilder();

			sbSelect.append("Obrigado, sr. " + customername + " pela preferência por comprar na Gamelândia");
			sbSelect.append("\nSegue abaixo os dados do seu pedido");
			sbSelect.append("\nJogos: " + arrayDescricao);
			sbSelect.append("\nPreços: " + valorItem);
			sbSelect.append("\nPreço Total: " + "R$" + netAmount);
			sbSelect.append("\nData Compra: " + dataVenda);
			sbSelect.append("\nEm breve enviaremos o seu jogo");

			String pathFile = "c:/temp/arquivo.xml"; //Arquivo gerado temporáriamente
			String description = "Nota Fiscal do Pedido"; 
			EmailAttachment anexoNota = new EmailAttachment();
			anexoNota.setPath(pathFile); 
			anexoNota.setDisposition(EmailAttachment.ATTACHMENT);
			anexoNota.setDescription(description);
			anexoNota.setName("notaFiscal.xml");
			email.attach(anexoNota);
			email.addTo(emailCliente, customername); // destinatario
			email.setSubject("Dados da sua compra via PagSeguro");
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