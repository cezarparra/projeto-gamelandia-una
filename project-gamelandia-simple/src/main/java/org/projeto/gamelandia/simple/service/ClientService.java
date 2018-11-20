package org.projeto.gamelandia.simple.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.projeto.gamelandia.simple.entity.Client;
import org.projeto.gamelandia.simple.others.ValidaCPF;
import org.projeto.gamelandia.simple.others.ValidaTelefone;
import org.projeto.gamelandia.simple.permission.PermissionEntity;
import org.projeto.gamelandia.simple.repositories.ClientRepository;
import org.projeto.gamelandia.simple.utils.GenericService;
import org.projeto.gamelandia.simple.utils.ServicePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/*
 * CLASSE SERVICE PARA INSERÇÃO, DELEÇÃO, EDIÇÃO DO CLIENTE
 * PRESENTE TAMBÉM NA CLASSE GenericService.java QUE NO QUAL HERDADA OS MÉTODOS DA MESMA
 * 
 */
@RestController
@Service
@Transactional
@RequestMapping(path = ServicePath.CLIENTE_PATH)
public class ClientService extends GenericService<Client, Long> {

	@Autowired
	private ClientRepository clienteRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//Singleton
	ValidaCPF validatorCPF = ValidaCPF.getInstance();
	ValidaTelefone validatorPhone = ValidaTelefone.getInstance();


	public List<Client> findAll() {
		return clienteRepository.findAll();
	}

	public Client findById(Long id) {
		return clienteRepository.findOne(id);
	}

	@Override
	public Client insert(@RequestBody Client newCliente) {

		String CPFsemPontos = newCliente.getCpf().replaceAll("[.-]", "");
		String celularSemPontosParenteses = newCliente.getCelularCliente().replaceAll("[()-]", "");
		String telefoneSemPontosParenteses = newCliente.getPhone().replaceAll("[()-]", "");

		List<PermissionEntity> permissions = new ArrayList<PermissionEntity>();

		PermissionEntity entity = new PermissionEntity();

		entity.setId((long) 2);
		entity.setRole("ROLE_USER");

		permissions.add(entity);
		newCliente.setPermissions(permissions);

		if (validatorCPF.isCPF(CPFsemPontos) == false) {
			throw new RuntimeException("Erro, CPF inválido");
		} else if (validatorPhone.validaCelular(celularSemPontosParenteses) == false) {
			throw new RuntimeException("Erro, Celular inválido");
		} else if (validatorPhone.validaTelefone(telefoneSemPontosParenteses) == false) {
			throw new RuntimeException("Erro, Telefone inválido");
		} else if (validatorCPF.repeteCPF(newCliente.getCpf()) == false) {
			throw new RuntimeException("Erro, CPF já existente no sistema");
		} else {
			newCliente.setPassword(this.passwordEncoder.encode(newCliente.getPassword()));
			return super.insert(newCliente);
		}

	}

	@Override
	public void update(@RequestBody Client newCliente) {
		String CPFsemPontos = newCliente.getCpf().replaceAll("[.-]", "");
		String celularSemPontosParenteses = newCliente.getCelularCliente().replaceAll("[()-]", "");
		String telefoneSemPontosParenteses = newCliente.getPhone().replaceAll("[()-]", "");

		if (validatorCPF.isCPF(CPFsemPontos) == false) {
			throw new RuntimeException("Erro, CPF inválido");
		} else if (validatorPhone.validaCelular(celularSemPontosParenteses) == false) {
			throw new RuntimeException("Erro, Celular inválido");
		} else if (validatorPhone.validaTelefone(telefoneSemPontosParenteses) == false) {
			throw new RuntimeException("Erro, Telefone inválido");
		} else {
			newCliente.setPassword(this.passwordEncoder.encode(newCliente.getPassword()));
			super.insert(newCliente);
		}

		return;
	}

}
