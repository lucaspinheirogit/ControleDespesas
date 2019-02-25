package br.inf.safetech.cd.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.inf.safetech.cd.models.Cliente;
import br.inf.safetech.cd.models.ContaDespesa;
import br.inf.safetech.cd.models.Usuario;

@Repository
@Transactional
public class ContaDespesaDAO{


	@PersistenceContext
	private EntityManager manager;
	
	public ContaDespesa find(Integer id) {
		System.out.println("Finding conta de despesa");
		return manager.find(ContaDespesa.class, id);
	}

	public List<ContaDespesa> listar() {
		System.out.println("listando contas de despesas");
		return manager.createQuery("select c from ContaDespesa c", ContaDespesa.class).getResultList();
	}  

	public void gravar(ContaDespesa conta) {
		System.out.println("gravando conta de despesa");
		manager.persist(conta);
	}

	public List<ContaDespesa> listarPorColaborador(Usuario usuario) {
		System.out.println("listando contas do colaborador: " + usuario.getLogin());
		return manager.
				createQuery("select c from ContaDespesa c where c.usuario.id = :id", ContaDespesa.class)
				.setParameter("id", usuario.getId())
				.getResultList();

	}

}