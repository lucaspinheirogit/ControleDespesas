package br.inf.safetech.cd.models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class MovimentacaoConta {

	private static final long serialVersionUID = 1L;

	public MovimentacaoConta() {

	}

	public MovimentacaoConta(ContaDespesa conta, Tipo tipo, Conciliada conciliada, BigDecimal valor,
			String descricao, Responsavel responsavel, Usuario criadoPor) {
		this.conta = conta;
		this.tipo = tipo;
		this.conciliada = conciliada;
		this.valor = valor;
		this.descricao = descricao;
		this.responsavel = responsavel;
		this.criadoPor = criadoPor;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	private ContaDespesa conta;
	private Tipo tipo;
	private Responsavel responsavel;
	private Conciliada conciliada;
	private BigDecimal valor;
	private String descricao;
	
	@ManyToOne
	private Usuario criadoPor;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public ContaDespesa getConta() {
		return conta;
	}

	public void setConta(ContaDespesa conta) {
		this.conta = conta;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Conciliada getConciliada() {
		return conciliada;
	}

	public void setConciliada(Conciliada conciliada) {
		this.conciliada = conciliada;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	public Usuario getCriadoPor() {
		return criadoPor;
	}

	public void setCriadoPor(Usuario criadoPor) {
		this.criadoPor = criadoPor;
	}

	@Override
	public String toString() {
		return "\nID: " + this.id + ", contaId: " + this.conta.getId() + ", desc: " + this.descricao + ", valor: " + this.valor + ", Tipo: " + this.tipo + ", Conc.: " + this.conciliada ;
	}
}
