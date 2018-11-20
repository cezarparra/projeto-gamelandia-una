package org.projeto.gamelandia.simple.entity;

import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.projeto.gamelandia.simple.utils.BaseEntity;

/*
 * 
 * ENTIDADE VENDEDOR CORRESPONDENTE A TABELA VENDEDOR NO BANCO
 */

@Entity
@Table(name = "CONSOLES")
@AttributeOverride(name = "id", column = @Column(name = "ID_CONSOLE"))
public class Console extends BaseEntity<Long> {

	private static final long serialVersionUID = 201505091506L;

	@Column(name = "NOME_CONSOLE", length = 255, nullable = false)
	private String nomeConsole;

	@Column(name = "QTDE_ESTOQUE", length = 255, nullable = false)
	private Integer qtdeEstoque;

	@Column(name = "STATUS_CONSOLE", length = 255, nullable = false)
	private String statusConsole;

	@Column(name = "PRECO", length = 255, nullable = false)
	private BigDecimal precoConsole;

	@Column(name = "ARQUIVO_CONSOLE")
	private String arquivoConsole;

	public String getNomeConsole() {
		return nomeConsole;
	}

	public void setNomeConsole(String nomeConsole) {
		this.nomeConsole = nomeConsole;
	}

	public Integer getQtdeEstoque() {
		return qtdeEstoque;
	}

	public void setQtdeEstoque(Integer qtdeEstoque) {
		this.qtdeEstoque = qtdeEstoque;
	}

	public String getStatusConsole() {
		return statusConsole;
	}

	public void setStatusConsole(String statusConsole) {
		this.statusConsole = statusConsole;
	}

	public BigDecimal getPrecoConsole() {
		return precoConsole;
	}

	public void setPrecoConsole(BigDecimal precoConsole) {
		this.precoConsole = precoConsole;
	}

	public String getArquivoConsole() {
		return arquivoConsole;
	}

	public void setArquivoConsole(String arquivoConsole) {
		this.arquivoConsole = arquivoConsole;
	}

	public Console() {
	}

}
