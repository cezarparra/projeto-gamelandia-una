package org.projeto.gamelandia.simple.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.projeto.gamelandia.simple.utils.BaseEntity;



/*
 * 
 * ENTIDADE GAME CORRESPONDENTE A TABELA GAME NO BANCO
 */
@Entity
@Table(name = "GAME")
@AttributeOverride(name = "id", column = @Column(name = "ID_GAME"))
public class Game extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -648841035520986736L;

	/**
	 * 
	 */

	@Column(name = "NOME_JOGO", length = 255)
	private String nomeJogo;

	@Column(name = "QTDE_ESTOQUE", length = 255)
	private int qtdeEstoque;

	@Column(name = "STATUS_JOGO", length = 255)
	private String statusJogo;

	@Column(name = "ANOLANCAMENTO")
	private int anoLancamento;

	@Column(name = "PRECO", length = 255)
	private int preco;

	@Column(name = "NOME_CONSOLE", length = 255)
	private String console;
	
	@Column(name = "file")
	private String file;

	
	public String getFile() {
		return file;
	}
	
	public void setFile(String file) {
		this.file = file;
	}

	public String getNomeJogo() {
		return nomeJogo;
	}

	public void setNomeJogo(String nomeJogo) {
		this.nomeJogo = nomeJogo;
	}

	public int getQtdeEstoque() {
		return qtdeEstoque;
	}

	public void setQtdeEstoque(int qtdeEstoque) {
		this.qtdeEstoque = qtdeEstoque;
	}

	public String getStatusJogo() {
		return statusJogo;
	}

	public void setStatusJogo(String statusJogo) {
		this.statusJogo = statusJogo;
	}

	public int getAnoLancamento() {
		return anoLancamento;
	}

	public void setAnoLancamento(int anoLancamento) {
		this.anoLancamento = anoLancamento;
	}

	public int getPreco() {
		return preco;
	}

	public void setPreco(int preco) {
		this.preco = preco;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Game(String nomeJogo, int qtdeEstoque, String statusJogo, int anoLancamento, int preco, 
			String console) {
		super();
		this.nomeJogo = nomeJogo;
		this.qtdeEstoque = qtdeEstoque;
		this.statusJogo = statusJogo;
		this.anoLancamento = anoLancamento;
		this.preco = preco;
		this.console = console;
	}

	public String getConsole() {
		return console;
	}

	public void setConsole(String console) {
		this.console = console;
	}

	public Game() {
	}

}