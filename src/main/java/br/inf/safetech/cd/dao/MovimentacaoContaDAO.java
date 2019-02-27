package br.inf.safetech.cd.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.inf.safetech.cd.models.Conciliada;
import br.inf.safetech.cd.models.ContaDespesa;
import br.inf.safetech.cd.models.MovimentacaoConta;
import br.inf.safetech.cd.models.Produto;

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

	public List<MovimentacaoConta> listarPorId(Integer contaId) {
		System.out.println("listando movimentacoes por id: " + contaId);
		return manager.createQuery("select m from MovimentacaoConta m where m.conta.id = :pId", MovimentacaoConta.class)
				.setParameter("pId", contaId)
				.getResultList();
	}

	public void conciliar(int id) {
		MovimentacaoConta conta = find(id);
		conta.setConciliada(Conciliada.SIM);
	}
	public void desconciliar(int id) {
		MovimentacaoConta conta = find(id);
		conta.setConciliada(Conciliada.NAO);
	}

	private MovimentacaoConta find(int id) {
		return manager.find(MovimentacaoConta.class, id);
	}

	public void remover(int id) {
		MovimentacaoConta conta = find(id);
		manager.remove(conta);
	}

	public boolean estaConciliada(int id) {
		MovimentacaoConta conta = find(id);
		if(conta.getConciliada() == Conciliada.SIM) return true;
		return false;
	}
	

}