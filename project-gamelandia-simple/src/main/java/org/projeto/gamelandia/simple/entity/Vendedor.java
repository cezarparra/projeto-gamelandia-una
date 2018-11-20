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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.projeto.gamelandia.simple.permission.PermissionEntity;
import org.projeto.gamelandia.simple.utils.BaseEntity;



/*
 * 
 * ENTIDADE VENDEDOR CORRESPONDENTE A TABELA VENDEDOR NO BANCO
 */

@Entity
@Table(name = "VENDEDOR")
@AttributeOverride(name = "id", column = @Column(name = "ID_VENDEDOR"))
public class Vendedor extends BaseEntity<Long> {

	private static final long serialVersionUID = 201505091506L;

	@Column(name = "name", length = 255, nullable = false)
	private String name;

	@Column(name = "email", length = 255, nullable = false)
	private String email;

	@Column(name = "PASSWORD", length = 255, nullable = false)
	private String password;

	@Column(name = "end_rua_vendedor", length = 255, nullable = false)
	private String endRuaVendedor;

	@Column(name = "end_bairro_vendedor", length = 255, nullable = false)
	private String endBairroVendedor;

	@Column(name = "end_cidade_vendedor", length = 255, nullable = false)
	private String endCidadeVendedor;

	@Column(name = "telefone_vendedor", length = 255, nullable = false)
	private String telefoneVendedor;

	@Column(name = "cpf_vendedor", length = 255, nullable = false)
	private String cpfVendedor;

	@NotNull
	@Column(name = "celular_vendedor", length = 255, nullable = false)
	private String celularVendedor;

	@NotNull
	@Column(name = "end_numero_vendedor", length = 255, nullable = false)
	private int endNumeroVendedor;

	@Column(name = "total_quantidade_vendida", length = 255, nullable = false)
	private double totalQtdeVendida;

	@Column(name = "total_preco_vendida", length = 255, nullable = false)
	private double totalPrecoVendido;

	@Column(name = "comissao", length = 255, nullable = false)
	private double comissao;

	@NotNull
	@Column(name = "salario", length = 255, nullable = false)
	private int salario;
	
	@NotNull
	@Column(name = "porc_salario", length = 255, nullable = false)
	private int porcSalario;
	
	@Transient
	private int totalVendas;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tb_user_permission", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
	private List<PermissionEntity> permissions;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Vendedor() {
	}
	
	public int getPorcSalario() {
		return porcSalario;
	}
	
	public void setPorcSalario(int porcSalario) {
		this.porcSalario = porcSalario;
	}
	
	public int getSalario() {
		return salario;
	}
	
	public void setSalario(int salario) {
		this.salario = salario;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEndRuaVendedor() {
		return endRuaVendedor;
	}

	public void setEndRuaVendedor(String endRuaVendedor) {
		this.endRuaVendedor = endRuaVendedor;
	}

	public String getEndBairroVendedor() {
		return endBairroVendedor;
	}

	public void setEndBairroVendedor(String endBairroVendedor) {
		this.endBairroVendedor = endBairroVendedor;
	}

	public String getEndCidadeVendedor() {
		return endCidadeVendedor;
	}

	public void setEndCidadeVendedor(String endCidadeVendedor) {
		this.endCidadeVendedor = endCidadeVendedor;
	}

	public String getTelefoneVendedor() {
		return telefoneVendedor;
	}

	public void setTelefoneVendedor(String telefoneVendedor) {
		this.telefoneVendedor = telefoneVendedor;
	}

	public String getCpfVendedor() {
		return cpfVendedor;
	}

	public void setCpfVendedor(String cpfVendedor) {
		this.cpfVendedor = cpfVendedor;
	}

	public List<PermissionEntity> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermissionEntity> permissions) {
		this.permissions = permissions;
	};

	public int getEndNumeroVendedor() {
		return endNumeroVendedor;
	}

	public void setEndNumeroVendedor(int endNumeroVendedor) {
		this.endNumeroVendedor = endNumeroVendedor;
	}

	public String getCelularVendedor() {
		return celularVendedor;
	}

	public void setCelularVendedor(String celularVendedor) {
		this.celularVendedor = celularVendedor;
	}

	public double getTotalPrecoVendido() {
		return totalPrecoVendido;
	}

	public void setTotalPrecoVendido(double totalPrecoVendido) {
		this.totalPrecoVendido = totalPrecoVendido;
	}

	public double getTotalQtdeVendida() {
		return totalQtdeVendida;
	}

	public void setTotalQtdeVendida(double totalQtdeVendida) {
		this.totalQtdeVendida = totalQtdeVendida;
	}

	public double getComissao() {
		return comissao;
	}

	public void setComissao(double comissao) {
		this.comissao = comissao;
	}
	
	public int getTotalVendas() {
		return totalVendas;
	}
	
	public void setTotalVendas(int totalVendas) {
		this.totalVendas = totalVendas;
	}

	public Vendedor(String name, String email, String password, String endRuaVendedor, String endBairroVendedor,
			String endCidadeVendedor, String telefoneVendedor, String cpfVendedor, String celularVendedor,
			int endNumeroVendedor, double totalQtdeVendida, double totalPrecoVendido, double comissao,
			List<PermissionEntity> permissions) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.endRuaVendedor = endRuaVendedor;
		this.endBairroVendedor = endBairroVendedor;
		this.endCidadeVendedor = endCidadeVendedor;
		this.telefoneVendedor = telefoneVendedor;
		this.cpfVendedor = cpfVendedor;
		this.celularVendedor = celularVendedor;
		this.endNumeroVendedor = endNumeroVendedor;
		this.totalQtdeVendida = totalQtdeVendida;
		this.totalPrecoVendido = totalPrecoVendido;
		this.comissao = comissao;
		this.permissions = permissions;
	}
	
	
}
