package br.inf.safetech.cd.models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class MovimentacaoConta {

	private static final long serialVersionUID = 1L;

	public MovimentacaoConta() {

	}

	public MovimentacaoConta(ContaDespesa conta, Tipo tipo, Conciliada conciliada, BigDecimal valor,
			String descricao) {
		this.conta = conta;
		this.tipo = tipo;
		this.conciliada = conciliada;
		this.valor = valor;
		this.descricao = descricao;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	private ContaDespesa conta;
	private Tipo tipo;
	private Conciliada conciliada;
	private BigDecimal valor;
	private String descricao;

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

	@Override
	public String toString() {
		return "ID: " + this.id + ", contaId: " + this.conta.getId() + ", desc: " + this.descricao + ", valor: " + this.valor + ", Tipo: " + this.tipo + ", Conc.: " + this.conciliada ;
	}
}
