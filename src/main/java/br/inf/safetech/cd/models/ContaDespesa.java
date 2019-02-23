package br.inf.safetech.cd.models;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ContaDespesa {

	private static final long serialVersionUID = 1L;

	public ContaDespesa() {

	}

	public ContaDespesa(Usuario usuario, Cliente cliente, Calendar dataInicio, Calendar dataFim,
			Situacao situacao) {
		this.usuario = usuario;
		this.cliente = cliente;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.situacao = situacao;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private Usuario usuario;
	
	@ManyToOne
	//@JoinColumn(name = "id")
	private Cliente cliente;
	
	private Calendar dataInicio;
	private Calendar dataFim;
	private Situacao situacao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Calendar getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Calendar dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Calendar getDataFim() {
		return dataFim;
	}

	public void setDataFim(Calendar dataFim) {
		this.dataFim = dataFim;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	@Override
	public String toString() {
		return "ID: " + this.id + ", cliente: " + this.cliente;
	}
}
