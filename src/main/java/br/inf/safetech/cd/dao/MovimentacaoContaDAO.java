package br.inf.safetech.cd.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.inf.safetech.cd.models.Conciliada;
import br.inf.safetech.cd.models.MovimentacaoConta;
import br.inf.safetech.cd.models.Responsavel;

@Repository
@Transactional
public class MovimentacaoContaDAO{


	@PersistenceContext
	private EntityManager manager;

	//Criação de uma movimentação em uma conta
	public void gravar(MovimentacaoConta movimentacao) {;
		manager.persist(movimentacao);
	}

	//Listagem das movimentações de uma conta que possui um id específico
	public List<MovimentacaoConta> listarPorId(Integer contaId) {
		return manager.createQuery("select m from MovimentacaoConta m where m.conta.id = :pId", MovimentacaoConta.class)
				.setParameter("pId", contaId)
				.getResultList();
	}
	
	//Listagem das movimentações de uma conta que possui um id específico e o responsável definido como Cliente
	public List<MovimentacaoConta> listarDoClientePorId(Integer contaId) {
		return manager.createQuery("select m from MovimentacaoConta m where m.conta.id = :pId and m.responsavel = :pResp", MovimentacaoConta.class)
				.setParameter("pId", contaId)
				.setParameter("pResp", Responsavel.CLIENTE)
				.getResultList();
	}

	//Conciliar uma movimentação com id específico
	public void conciliar(int id) {
		MovimentacaoConta conta = find(id);
		conta.setConciliada(Conciliada.SIM);
	}
	
	//Desconciliar uma movimentação com id específico
	public void desconciliar(int id) {
		MovimentacaoConta conta = find(id);
		conta.setConciliada(Conciliada.NAO);
	}

	//Buscar uma movimentação com id específico
	private MovimentacaoConta find(int id) {
		return manager.find(MovimentacaoConta.class, id);
	}

	//Remover uma movimentação com id específico
	public void remover(int id) {
		MovimentacaoConta conta = find(id);
		manager.remove(conta);
	}
	
	//Alterar o responsável de uma movimentação com id específico
	public void alterarResponsavel(int id, Responsavel responsavel) {
		MovimentacaoConta conta = find(id);
		conta.setResponsavel(responsavel);
	}

	//Método que checa se uma determinada movimentação está conciliada
	public boolean estaConciliada(int id) {
		MovimentacaoConta conta = find(id);
		if(conta.getConciliada() == Conciliada.SIM) return true;
		return false;
	}
	

}