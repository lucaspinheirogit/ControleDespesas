package br.inf.safetech.cd.dao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.inf.safetech.cd.models.Cliente;
import br.inf.safetech.cd.models.Conciliada;
import br.inf.safetech.cd.models.ContaDespesa;
import br.inf.safetech.cd.models.MovimentacaoConta;
import br.inf.safetech.cd.models.Responsavel;
import br.inf.safetech.cd.models.Situacao;
import br.inf.safetech.cd.models.Tipo;
import br.inf.safetech.cd.models.Usuario;

@Repository
@Transactional
public class ContaDespesaDAO {

	@Autowired
	private MovimentacaoContaDAO movimentacaoContaDAO;

	@PersistenceContext
	private EntityManager manager;

	public ContaDespesa find(Integer id) {
		return manager.find(ContaDespesa.class, id);
	}

	public List<ContaDespesa> listar() {
		return manager.createQuery("select c from ContaDespesa c where c.situacao = :situacao", ContaDespesa.class)
				.setParameter("situacao", Situacao.ATIVA)
				.getResultList();
	}

	public void gravar(ContaDespesa conta) {
		manager.persist(conta);
	}

	public List<ContaDespesa> listarPorColaborador(Usuario usuario) {
		return manager.createQuery("select c from ContaDespesa c where c.usuario.id = :id and c.situacao = :situacao", ContaDespesa.class)
				.setParameter("id", usuario.getId())
				.setParameter("situacao", Situacao.ATIVA)
				.getResultList();
	}

	public void encerrar(int id) throws ParseException {
		ContaDespesa conta = this.find(id);

		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = sf.parse(sf.format(date));
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		conta.setDataFim(cal);
		conta.setSituacao(Situacao.ENCERRADA);
	}

	public boolean estaLiquidada(int id) {
		List<MovimentacaoConta> movimentacoes = movimentacaoContaDAO.listarPorId(id);
		for (MovimentacaoConta movimentacao : movimentacoes) {
			if (movimentacao.getConciliada() == Conciliada.NAO || movimentacao.getResponsavel() == null) {
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
			if (m.getTipo() == Tipo.CREDITO) {
				credito = credito.add(m.getValor());
			} else if (m.getTipo() == Tipo.DEBITO && m.getResponsavel() != Responsavel.COLABORADOR) {
				debito = debito.add(m.getValor());
			}
		}

		saldo = saldo.add(credito.subtract(debito));
		return saldo;
	}
	
	public BigDecimal calculaSaldoMovimentacoes(List<MovimentacaoConta> movimentacoes) {
		BigDecimal credito = new BigDecimal(0);
		BigDecimal debito = new BigDecimal(0);
		BigDecimal saldo = new BigDecimal(0);

		for (MovimentacaoConta m : movimentacoes) {
			if (m.getTipo() == Tipo.CREDITO) {
				credito = credito.add(m.getValor());
			} else if (m.getTipo() == Tipo.DEBITO && m.getResponsavel() != Responsavel.COLABORADOR) {
				debito = debito.add(m.getValor());
			}
		}

		saldo = saldo.add(credito.subtract(debito));
		return saldo;
	}
	
	public BigDecimal calculaCreditoMovimentacoes(List<MovimentacaoConta> movimentacoes) {
		BigDecimal credito = new BigDecimal(0);

		for (MovimentacaoConta m : movimentacoes) {
			if (m.getTipo() == Tipo.CREDITO) {
				credito = credito.add(m.getValor());
			}
		}
		
		return credito;
	}
	
	public BigDecimal calculaDebitoMovimentacoes(List<MovimentacaoConta> movimentacoes) {
		BigDecimal debito = new BigDecimal(0);

		for (MovimentacaoConta m : movimentacoes) {
			if (m.getTipo() == Tipo.DEBITO && m.getResponsavel() != Responsavel.COLABORADOR) {
				debito = debito.add(m.getValor());
			}
		}

		return debito;
	}

	public List<ContaDespesa> listarComFiltro(String user, String cliente, Calendar dataInicio,
			Calendar dataFinal, Situacao situacao) {
		
		List<ContaDespesa> resultado;
		
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<ContaDespesa> query = criteriaBuilder.createQuery(ContaDespesa.class);
		Root<ContaDespesa> root = query.from(ContaDespesa.class);
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		Path<String> usuarioPath = root.<Usuario> get("usuario").<String> get("nome");
		Path<String> clientePath = root.<Cliente> get("cliente").<String> get("nome");
		Path<Calendar> dataInicioPath = root.<Calendar> get("dataInicio");
		Path<Calendar> dataFimPath = root.<Calendar> get("dataFim");
		Path<Situacao> situacaoPath = root.<Situacao> get("situacao");
		
		if (!user.isEmpty()) {
	        Predicate usuarioIgual = criteriaBuilder.equal(usuarioPath , user);
	        predicates.add(usuarioIgual);
	    }
		
		if (!cliente.isEmpty()) {
	        Predicate clienteIgual = criteriaBuilder.equal(clientePath , cliente);
	        predicates.add(clienteIgual);
	    }
		
		if (situacao != null) {
	        Predicate situacaoIgual = criteriaBuilder.equal(situacaoPath , situacao);
	        predicates.add(situacaoIgual);
	    }
		
		if (dataInicio != null) {
	        Predicate dataInicioIgual = criteriaBuilder.equal(dataInicioPath , dataInicio);
	        predicates.add(dataInicioIgual);
	    }
		
		if (dataFinal != null) {
	        Predicate dataFinalIgual = criteriaBuilder.equal(dataFimPath, dataFinal);
	        predicates.add(dataFinalIgual);
	    }
	    
		query.where((Predicate[]) predicates.toArray(new Predicate[0]));
	    TypedQuery<ContaDespesa> typedQuery = manager.createQuery(query);
		resultado = typedQuery.getResultList();
		
		return resultado;
	}

}