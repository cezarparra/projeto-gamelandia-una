package org.projeto.gamelandia.simple.others;




/*
 * 
 * CLASSE RESPONSÁVEL PARA VALIDAÇÃO DE TELEFONE E CELULAR INVÁLIDO
 * 
 */
public class ValidaTelefone {

	public static ValidaTelefone instance = new ValidaTelefone();

	public static ValidaTelefone getInstance() {
		return instance;
	}

	public boolean validaCelular(String celular) {
		if (celular.equals("00000000000") || celular.equals("(11111111111") || celular.equals("22222222222")
				|| celular.equals("33333333333") || celular.equals("44444444444") || celular.equals("55555555555")
				|| celular.equals("66666666666") || celular.equals("77777777777") || celular.equals("88888888888")
				|| celular.equals("99999999999") || (celular.length() != 11)) {
			return false;
		}
		return true;

	}

	public boolean validaTelefone(String telefone) {

		if (telefone.equals("0000000000") || telefone.equals("1111111111") || telefone.equals("2222222222")
				|| telefone.equals("3333333333") || telefone.equals("4444444444") || telefone.equals("5555555555")
				|| telefone.equals("6666666666") || telefone.equals("7777777777") || telefone.equals("8888888888")
				|| telefone.equals("9999999999") || (telefone.length() != 10)) {
			return false;
		}
		return true;
	}

}
