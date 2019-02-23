package br.inf.safetech.cd.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.inf.safetech.cd.models.ContaDespesa;
import br.inf.safetech.cd.models.MovimentacaoConta;

@Repository
@Transactional
public class MovimentacaoContaDAO{


	@PersistenceContext
	private EntityManager manager;

	public List<MovimentacaoConta> listar() {
		System.out.println("listando movimentacoes");
		return manager.createQuery("select m from MovimentacaoConta m", MovimentacaoConta.class).getResultList();
	}

	public void gravar(MovimentacaoConta movimentacao) {
		System.out.println("gravando movimentacao na conta");
		manager.persist(movimentacao);
	}

}