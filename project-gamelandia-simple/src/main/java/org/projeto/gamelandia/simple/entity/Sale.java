package org.projeto.gamelandia.simple.entity;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.projeto.gamelandia.simple.utils.BaseEntity;



/*
 * 
 * ENTIDADE REALIZA_VENDA AONDE QUE É REALIZADO AS VENDAS
 * ATRAVÉS DA LOJA CORRESPONDENTE A TABELA REALIZA_VENDA NO BANCO
 */


@Entity
@Table(name = "REALIZA_VENDA")
@AttributeOverride(name = "id", column = @Column(name = "ID_VENDA"))
public class Sale extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "ID_VENDEDOR", insertable = true, updatable = true)
	private Vendedor vendedor;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_GAME", insertable = true, updatable = true)
	@JoinTable(name = "tb_venda_jogo", joinColumns = @JoinColumn(name = "lista_carrinho_id"), inverseJoinColumns = @JoinColumn(name = "venda_jogo_id"))
	private List<Game> game;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_CONSOLE", insertable = true, updatable = true)
	@JoinTable(name = "tb_venda_console", joinColumns = @JoinColumn(name = "lista_carrinho_id"), inverseJoinColumns = @JoinColumn(name = "venda_console_id"))
	private List<Console> console;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "ID_CLIENTE", nullable = true)
	private Client cliente;

	@Column(name = "FORM_PAYMENT")
	private String formPayment;

	@NotNull
	@Column(name = "QTDE_VENDIDA", length = 255)
	private int quantidade;

	@Column(name = "DT_VENDA", nullable = false)
	private String dataVenda;

	@NotNull
	@Column(name = "TEMPO_GARANTIA", length = 255)
	private int tempoGarantia;

	@NotNull
	@Column(name = "PRECO_VENDA", length = 255)
	private int precoVenda;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Client getCliente() {
		return cliente;
	}

	public void setCliente(Client cliente) {
		this.cliente = cliente;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public List<Game> getGame() {
		return game;
	}

	public void setGame(List<Game> game) {
		this.game = game;
	}

	public String getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(String dataVenda) {
		this.dataVenda = dataVenda;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public int getTempoGarantia() {
		return tempoGarantia;
	}

	public void setTempoGarantia(int tempoGarantia) {
		this.tempoGarantia = tempoGarantia;
	}

	public int getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(int precoVenda) {
		this.precoVenda = precoVenda;
	}

	public Sale() {

	}

	public String getFormPayment() {
		return formPayment;
	}

	public void setFormPayment(String formPayment) {
		this.formPayment = formPayment;
	}
	
	public List<Console> getConsole() {
		return console;
	}
	
	public void setConsole(List<Console> console) {
		this.console = console;
	}

	public Sale(Vendedor vendedor, List<Game> game, Client cliente, String formPayment, int quantidade, String dataVenda,
			int tempoGarantia, int precoVenda) {
		super();
		this.vendedor = vendedor;
		this.game = game;
		this.cliente = cliente;
		this.formPayment = formPayment;
		this.quantidade = quantidade;
		this.dataVenda = dataVenda;
		this.tempoGarantia = tempoGarantia;
		this.precoVenda = precoVenda;
	}

}