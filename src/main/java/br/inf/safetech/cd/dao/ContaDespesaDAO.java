package br.inf.safetech.cd.dao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.inf.safetech.cd.models.Conciliada;
import br.inf.safetech.cd.models.ContaDespesa;
import br.inf.safetech.cd.models.MovimentacaoConta;
import br.inf.safetech.cd.models.Responsavel;
import br.inf.safetech.cd.models.Situacao;
import br.inf.safetech.cd.models.Tipo;
import br.inf.safetech.cd.models.Usuario;

@Repository
@Transactional
public class ContaDespesaDAO{

	@Autowired
	private MovimentacaoContaDAO movimentacaoContaDAO;

	@PersistenceContext
	private EntityManager manager;
	
	public ContaDespesa find(Integer id) {
		System.out.println("Finding conta de despesa");
		return manager.find(ContaDespesa.class, id);
	}

	public List<ContaDespesa> listar() {
		System.out.println("listando contas de despesas");
		return manager.createQuery("select c from ContaDespesa c", ContaDespesa.class)
				.getResultList();
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

	public void encerrar(int id) throws ParseException {
		ContaDespesa conta = this.find(id);
		
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = sf.parse(sf.format(new Date()));
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		conta.setDataFim(cal);
		conta.setSituacao(Situacao.ENCERRADA);
	}

	public boolean estaLiquidada(int id) {
		List<MovimentacaoConta> movimentacoes = movimentacaoContaDAO.listarPorId(id);
		System.out.println(movimentacoes);
		for (MovimentacaoConta movimentacao : movimentacoes) {
			if(movimentacao.getConciliada() == Conciliada.NAO) {
				System.out.println("mov. nao conciliada");
				return false;
			}
		}
		return true;
	}

	public BigDecimal calculaSaldo(int id) {
		List<MovimentacaoConta> movimentacoes = movimentacaoContaDAO.listarPorId(id);
		BigDecimal credito = new BigDecimal(0);
		BigDecimal debito = new BigDecimal(0);
		BigDecimal saldo = new BigDecimal(0);
		
		for (MovimentacaoConta m : movimentacoes) {
			if(m.getTipo() == Tipo.CREDITO) {
				credito = credito.add(m.getValor());
			}else if(m.getTipo() == Tipo.DEBITO && m.getResponsavel() != Responsavel.COLABORADOR) {
				debito = debito.add(m.getValor());
			}
		}
		
		saldo = saldo.add(credito.subtract(debito));
		return saldo;
	}

}