package org.projeto.gamelandia.simple.entity;

import java.util.List;



import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.projeto.gamelandia.simple.permission.PermissionEntity;
import org.projeto.gamelandia.simple.utils.BaseEntity;

/*
 * ENTIDADE CLIENTE CORRESPONDENTE A TABELA CLIENTE NO BANCO
 * 
 */

@Entity
@Table(name = "CLIENTE")
@AttributeOverride(name = "id", column = @Column(name = "ID_CLIENTE"))
public class Client extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;

	@Column(name = "NOME_CLIENTE", length = 255)
	private String name;

	@Column(name = "END_RUA_CLIENTE", length = 255)
	private String enderecoRuaCliente;

	@Column(name = "END_BAIRRO_CLIENTE", length = 255)
	private String enderecoBairroCliente;

	@Column(name = "END_CIDADE_CLIENTE", length = 255)
	private String enderecoCidadeCliente;

	@Column(name = "END_ESTADO_CLIENTE", length = 2)
	private String enderecoEstadoCliente;

	@Column(name = "TELEFONE_CONTATO", length = 14)
	private String phone;

	@Column(name = "EMAIL_CLIENTE", length = 255, nullable = false)
	private String emailCliente;

	@Column(name = "PASSWORD", length = 255, nullable = false)
	private String password;

	@NotNull
	@Column(name = "CPF", length = 255, nullable = false)
	private String cpf;

	@NotNull
	@Column(name = "celular_cliente", length = 255, nullable = false)
	private String celularCliente;

	@NotNull
	@Column(name = "end_numero_cliente", length = 255, nullable = false)
	private int endNumeroCliente;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tb_client_user_permission", joinColumns = @JoinColumn(name = "client_user_id"), inverseJoinColumns = @JoinColumn(name = "client_permission_id"))
	private List<PermissionEntity> permissions;

	public Client(String name, String enderecoRuaCliente, String enderecoBairroCliente, String enderecoCidadeCliente,
			String enderecoEstadoCliente, String phone, String emailCliente, String password, String cpf,
			String celularCliente, int endNumeroCliente, List<PermissionEntity> permissions) {
		super();
		this.name = name;
		this.enderecoRuaCliente = enderecoRuaCliente;
		this.enderecoBairroCliente = enderecoBairroCliente;
		this.enderecoCidadeCliente = enderecoCidadeCliente;
		this.enderecoEstadoCliente = enderecoEstadoCliente;
		this.phone = phone;
		this.emailCliente = emailCliente;
		this.password = password;
		this.cpf = cpf;
		this.celularCliente = celularCliente;
		this.endNumeroCliente = endNumeroCliente;
		this.permissions = permissions;
	}
	
	public String getEnderecoEstadoCliente() {
		return enderecoEstadoCliente;
	}

	public void setEnderecoEstadoCliente(String enderecoEstadoCliente) {
		this.enderecoEstadoCliente = enderecoEstadoCliente;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnderecoRuaCliente() {
		return enderecoRuaCliente;
	}

	public void setEnderecoRuaCliente(String enderecoRuaCliente) {
		this.enderecoRuaCliente = enderecoRuaCliente;
	}

	public String getEnderecoBairroCliente() {
		return enderecoBairroCliente;
	}

	public void setEnderecoBairroCliente(String enderecoBairroCliente) {
		this.enderecoBairroCliente = enderecoBairroCliente;
	}

	public String getEnderecoCidadeCliente() {
		return enderecoCidadeCliente;
	}

	public void setEnderecoCidadeCliente(String enderecoCidadeCliente) {
		this.enderecoCidadeCliente = enderecoCidadeCliente;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmailCliente() {
		return emailCliente;
	}

	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public List<PermissionEntity> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermissionEntity> permissions) {
		this.permissions = permissions;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCelularCliente() {
		return celularCliente;
	}

	public void setCelularCliente(String celularCliente) {
		this.celularCliente = celularCliente;
	}

	public int getEndNumeroCliente() {
		return endNumeroCliente;
	}

	public void setEndNumeroCliente(int endNumeroCliente) {
		this.endNumeroCliente = endNumeroCliente;
	}

	public Client() {

	}

	@Override
	public int hashCode() {
		Client id = new Client();
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id.getId() == null) ? 0 : id.getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		Client id = new Client();
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		if (id.getId() == null) {
			if (id.getId() != null) {
				return false;
			}
		} else if (!id.getId().equals(id.getId())) {
			return false;
		}
		return true;
	}

}
