package org.projeto.gamelandia.simple.others;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.InputMismatchException;

import org.apache.commons.lang3.StringUtils;


/*
 * 
 * CLASSE RESPONSÁVEL PARA VALIDAÇÃO DE CPF, INDEPENDENTE SE FOR CPF REPETIDO NO SISTEMA 
 * (CLIENTE E FUNCIONÁRIO) OU INVÁLIDO
 * 
 */
public class ValidaCPF {

	private static ValidaCPF instance = new ValidaCPF();

	public static ValidaCPF getInstance() {
		return instance;
	}

	public boolean repeteCPF(String CPF) {

		try {
			String url = "jdbc:mysql://localhost:3306/desjavagame?autoReconnect=true&useSSL=false";
			String user = "root";
			String password = "123456";

			Connection conexao1 = null;
			conexao1 = DriverManager.getConnection(url, user, password);
			
			Connection conexao2 = null;
			conexao2 = DriverManager.getConnection(url, user, password);
			Statement st = conexao1.createStatement();
			Statement st2 = conexao2.createStatement();
			ResultSet rs = st.executeQuery("select CPF from cliente");
			ResultSet rs2 = st2.executeQuery("select cpf_vendedor from vendedor;");

			while (rs.next()) {
				String cpfCliente = rs.getString("CPF");
				if (StringUtils.contains(CPF, cpfCliente)) {
					return false;
				}
			}

			while (rs2.next()) {
				String cpfVendedor = rs2.getString("cpf_vendedor");
				if (StringUtils.contains(CPF, cpfVendedor)) {
					return false;
				}
			}
			
			conexao1.close();
			conexao2.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}
	// A lógica cabulosa para que o CPF não seja inválida
	public boolean isCPF(String CPF) {
		// considera-se erro CPF's formados por uma sequencia de numeros iguais
		if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222")
				|| CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555")
				|| CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888")
				|| CPF.equals("99999999999") || (CPF.length() != 11))
			return (false);

		char dig10, dig11;
		int sm, i, r, num, peso;

		// "try" - protege o codigo para eventuais erros de conversao de tipo (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				// converte o i-esimo caractere do CPF em um numero:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posicao de '0' na tabela ASCII)
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48); // converte no respectivo caractere numerico

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);

			// Verifica se os digitos calculados conferem com os digitos informados.
			if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

}