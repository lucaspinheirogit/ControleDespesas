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
		System.out.println("Finding conta de despesa");
		return manager.find(ContaDespesa.class, id);
	}

	public List<ContaDespesa> listar() {
		System.out.println("listando contas de despesas");
		return manager.createQuery("select c from ContaDespesa c where c.situacao = :situacao", ContaDespesa.class)
				.setParameter("situacao", Situacao.ATIVA)
				.getResultList();
	}

	public void gravar(ContaDespesa conta) {
		System.out.println("gravando conta de despesa");
		manager.persist(conta);
	}

	public List<ContaDespesa> listarPorColaborador(Usuario usuario) {
		System.out.println("listando contas do colaborador: " + usuario.getLogin());
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
		System.out.println(movimentacoes);
		for (MovimentacaoConta movimentacao : movimentacoes) {
			System.out.println(movimentacao.getResponsavel());
			if (movimentacao.getConciliada() == Conciliada.NAO || movimentacao.getResponsavel() == null) {
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
			if (m.getTipo() == Tipo.CREDITO) {
				credito = credito.add(m.getValor());
			} else if (m.getTipo() == Tipo.DEBITO) {
				debito = debito.add(m.getValor());
			}
		}

		saldo = saldo.add(credito.subtract(debito));
		return saldo;
	}
	
	public BigDecimal calculaCredito(int id) {
		List<MovimentacaoConta> movimentacoes = movimentacaoContaDAO.listarPorId(id);
		BigDecimal credito = new BigDecimal(0);

		for (MovimentacaoConta m : movimentacoes) {
			if (m.getTipo() == Tipo.CREDITO) {
				credito = credito.add(m.getValor());
			}
		}
		
		return credito;
	}
	
	public BigDecimal calculaDebito(int id) {
		List<MovimentacaoConta> movimentacoes = movimentacaoContaDAO.listarPorId(id);
		BigDecimal debito = new BigDecimal(0);

		for (MovimentacaoConta m : movimentacoes) {
			if (m.getTipo() == Tipo.DEBITO) {
				debito = debito.add(m.getValor());
			}
		}

		return debito;
	}

	public List<ContaDespesa> listarComFiltro(String user, String cliente, Calendar dataInicio,
			Calendar dataFinal, Situacao situacao) {
		System.out.println("listando contas filtradas do colaborador: " + user);

		//dataInicio.setTimeInMillis(dataInicio.getTimeInMillis() - 86400000);
		//dataFinal.setTimeInMillis(dataFinal.getTimeInMillis() + 86400000);
		
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
			System.out.println("User foi informado");
	        Predicate usuarioIgual = criteriaBuilder.equal(usuarioPath , user);
	        predicates.add(usuarioIgual);
	    }
		
		if (!cliente.isEmpty()) {
			System.out.println("cliente foi informado");
	        Predicate clienteIgual = criteriaBuilder.equal(clientePath , cliente);
	        predicates.add(clienteIgual);
	    }
		
		if (situacao != null) {
			System.out.println("situacao foi informada");
	        Predicate situacaoIgual = criteriaBuilder.equal(situacaoPath , situacao);
	        predicates.add(situacaoIgual);
	    }
		
		if (dataInicio != null) {
			System.out.println("data de inicio foi informada");
	        Predicate dataInicioIgual = criteriaBuilder.equal(dataInicioPath , dataInicio);
	        predicates.add(dataInicioIgual);
	    }
		
		if (dataFinal != null) {
			System.out.println("data final foi informada");
	        Predicate dataFinalIgual = criteriaBuilder.equal(dataFimPath, dataFinal);
	        predicates.add(dataFinalIgual);
	    }
	    
		query.where((Predicate[]) predicates.toArray(new Predicate[0]));
	    TypedQuery<ContaDespesa> typedQuery = manager.createQuery(query);
		resultado = typedQuery.getResultList();
		System.out.println("Resultado: " + resultado.size());
		System.out.println(resultado);
		
		return resultado;
	}

}