package org.projeto.gamelandia.simple.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.projeto.gamelandia.simple.utils.BaseEntity;



/*
 * 
 * ENTIDADE REQUEST (PEDIDO) CORRESPONDENTE A TABELA REQUEST NO BANCO
 */

@Entity
@Table(name = "request")
@AttributeOverride(name = "id", column = @Column(name = "request_id"))
public class Request extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "status")
	private String status;

	@Column(name = "generate_note")
	private boolean generateNote;

	@Column(name = "transaction_code")
	private String transactionCode;

	@Column(name = "url")
	private String url;

	@Column(name = "date_request")
	private String dateRequest;

	@Column(name = "generate_note_str")
	private String genNote;

	public String getGenNote() {
		return genNote;
	}
	
	public void setGenNote(String genNote) {
		this.genNote = genNote;
	}
	
	public String getDateRequest() {
		return dateRequest;
	}

	public void setDateRequest(String dateRequest) {
		this.dateRequest = dateRequest;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public boolean isGenerateNote() {
		return generateNote;
	}

	public void setGenerateNote(boolean generateNote) {
		this.generateNote = generateNote;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Request() {

	}

}