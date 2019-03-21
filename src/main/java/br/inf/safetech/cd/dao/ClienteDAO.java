package br.inf.safetech.cd.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.inf.safetech.cd.models.Cliente;
import br.inf.safetech.cd.models.Role;
import br.inf.safetech.cd.models.Usuario;

@Repository
@Transactional
public class ClienteDAO{


	@PersistenceContext
	private EntityManager manager;
	
	public Cliente find(Integer id) {
		return manager.find(Cliente.class, id);
	}

	public List<Cliente> listar() {
		return manager.createQuery("select c from Cliente c", Cliente.class).getResultList();
	}

	public void gravar(Cliente cliente) {
		manager.persist(cliente);
	}

}