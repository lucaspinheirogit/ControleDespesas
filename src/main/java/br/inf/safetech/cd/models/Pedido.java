package br.inf.safetech.cd.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Pedido {
	
	public Pedido() {
		
	}
	
	public Pedido(Integer id, BigDecimal valor, Calendar data, List<Produto> produtos) {
		this.id = id;
		this.valor = valor;
		this.data = data;
		this.produtos = produtos;
	}
	
	private Integer id;
	private BigDecimal valor;
	private Calendar data;
	private List<Produto> produtos = new ArrayList<Produto>();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public Calendar getData() {
		return data;
	}
	public void setData(Calendar data) {
		this.data = data;
	}
	public List<Produto> getProdutos() {
		return produtos;
	}
	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	
	
}
