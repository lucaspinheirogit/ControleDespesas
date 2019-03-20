package br.inf.safetech.cd.relatorio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.inf.safetech.cd.dao.MovimentacaoContaDAO;
import br.inf.safetech.cd.models.MovimentacaoConta;

public class Relatorio {

	public List<Map<String, ?>> findAll(){
		System.out.println("Metodo findAll");
		
		List<Map<String,?>> listaMovimentacoes = new ArrayList<Map<String,?>>();
		
		MovimentacaoContaDAO movimentacaoContaDAO = new MovimentacaoContaDAO();
		System.out.println("ate aqui chegou");
		
		List<MovimentacaoConta> movimentacoes = movimentacaoContaDAO.listar();
		System.out.println(movimentacoes);
		System.out.println("ate aqui chegou??");
		
		/*
		for(MovimentacaoConta mov : movimentacoes) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("tipo", mov.getTipo());
			m.put("responsavel", mov.getResponsavel());
			m.put("conciliada", mov.getConciliada());
			m.put("valor", mov.getValor());
			m.put("descricao", mov.getDescricao());
			m.put("criadoPor", mov.getCriadoPor());
			listaMovimentacoes.add(m);
		} */
		
		return listaMovimentacoes;
	};
	
}
