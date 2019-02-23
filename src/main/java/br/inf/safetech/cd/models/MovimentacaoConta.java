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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "a";
		//return "ID: " + this.id + ", Nome: " + this.nome;
	}
}
