package br.inf.safetech.cd.controllers;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Arrays;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.inf.safetech.cd.dao.ClienteDAO;
import br.inf.safetech.cd.dao.ContaDespesaDAO;
import br.inf.safetech.cd.dao.MovimentacaoContaDAO;
import br.inf.safetech.cd.dao.RoleDAO;
import br.inf.safetech.cd.dao.UsuarioDAO;
import br.inf.safetech.cd.models.Cliente;
import br.inf.safetech.cd.models.Conciliada;
import br.inf.safetech.cd.models.ContaDespesa;
import br.inf.safetech.cd.models.MovimentacaoConta;
import br.inf.safetech.cd.models.Responsavel;
import br.inf.safetech.cd.models.Role;
import br.inf.safetech.cd.models.Situacao;
import br.inf.safetech.cd.models.Tipo;
import br.inf.safetech.cd.models.Usuario;

@Controller
public class HomeController {

	@Autowired
	private UsuarioDAO usuarioDAO;

	@Autowired
	private ClienteDAO clienteDAO;

	@Autowired
	private ContaDespesaDAO contaDespesaDAO;

	@Autowired
	private MovimentacaoContaDAO movimentacaoContaDAO;

	@Autowired
	private RoleDAO roleDAO;

	//Raiz do projeto redireciona para /contas ou /login caso o usuario nao esteja logado
	@RequestMapping("/")
	public ModelAndView index() {
		return new ModelAndView("redirect:/contas");
	}

	//Geracao de dados para o dev testar a aplicacao
	@Transactional
	@ResponseBody
	@RequestMapping("/magia")
	public String urlMagicaMaluca() {
		
		Role admin = new Role("ROLE_ADMIN");
		Role user = new Role("ROLE_USER");

		roleDAO.gravar(admin);
		roleDAO.gravar(user);

		Cliente cliente1 = new Cliente("KLIN");
		Cliente cliente2 = new Cliente("BIBI");
		Cliente cliente3 = new Cliente("Paqueta");
		Cliente cliente4 = new Cliente("Altero");

		clienteDAO.gravar(cliente1);
		clienteDAO.gravar(cliente2);
		clienteDAO.gravar(cliente3);
		clienteDAO.gravar(cliente4);

		Usuario usuario = new Usuario();
		usuario.setNome("Marcia");
		usuario.setLogin("admin@123");
		usuario.setSenha("123456");
		usuario.setSituacao(Situacao.ATIVA);
		usuario.setRoles(Arrays.asList(admin));
		Usuario usuario2 = new Usuario();
		usuario2.setNome("Daniel R.");
		usuario2.setLogin("daniel@123");
		usuario2.setSenha("123456");
		usuario2.setSituacao(Situacao.ATIVA);
		usuario2.setRoles(Arrays.asList(user));

		usuarioDAO.gravar(usuario);
		usuarioDAO.gravar(usuario2);

		ContaDespesa conta1 = new ContaDespesa(usuario2, cliente1, new GregorianCalendar(2019, 3, 20),
				null, Situacao.ATIVA, usuario);
		ContaDespesa conta2 = new ContaDespesa(usuario2, cliente2, new GregorianCalendar(2019, 3, 22),
				null, Situacao.ATIVA, usuario);
		ContaDespesa conta3 = new ContaDespesa(usuario, cliente2, new GregorianCalendar(2019, 3, 25),
				null, Situacao.ATIVA, usuario);

		MovimentacaoConta mc1 = new MovimentacaoConta(conta1, Tipo.CREDITO, Conciliada.SIM, new BigDecimal(1000),
				"Aporte para viagem", Responsavel.EMPRESA, usuario);
		MovimentacaoConta mc2 = new MovimentacaoConta(conta1, Tipo.DEBITO, Conciliada.NAO, new BigDecimal(100), "Jantar", Responsavel.EMPRESA, usuario2);
		MovimentacaoConta mc3 = new MovimentacaoConta(conta1, Tipo.DEBITO, Conciliada.SIM, new BigDecimal(200), "Taxi", Responsavel.EMPRESA, usuario2);
		MovimentacaoConta mc4 = new MovimentacaoConta(conta1, Tipo.DEBITO, Conciliada.SIM, new BigDecimal(280), "Almoço", Responsavel.EMPRESA, usuario2);
		MovimentacaoConta mc5 = new MovimentacaoConta(conta1, Tipo.DEBITO, Conciliada.SIM, new BigDecimal(410), "Taxi Aeroporto", Responsavel.EMPRESA, usuario2);
		
		MovimentacaoConta mc6 = new MovimentacaoConta(conta2, Tipo.CREDITO, Conciliada.SIM, new BigDecimal(1200), "Passagem", Responsavel.EMPRESA, usuario);
		MovimentacaoConta mc7 = new MovimentacaoConta(conta2, Tipo.DEBITO, Conciliada.SIM, new BigDecimal(50), "Comida", Responsavel.COLABORADOR, usuario);
		MovimentacaoConta mc8 = new MovimentacaoConta(conta2, Tipo.DEBITO, Conciliada.NAO, new BigDecimal(100), "Transporte", Responsavel.CLIENTE, usuario2);
		MovimentacaoConta mc9 = new MovimentacaoConta(conta3, Tipo.DEBITO, Conciliada.SIM, new BigDecimal(1500), "Hotel", Responsavel.EMPRESA, usuario);

		movimentacaoContaDAO.gravar(mc1);
		movimentacaoContaDAO.gravar(mc2);
		movimentacaoContaDAO.gravar(mc3);
		movimentacaoContaDAO.gravar(mc4);
		movimentacaoContaDAO.gravar(mc5);
		movimentacaoContaDAO.gravar(mc6);
		movimentacaoContaDAO.gravar(mc7);
		movimentacaoContaDAO.gravar(mc8);
		movimentacaoContaDAO.gravar(mc9);

		contaDespesaDAO.gravar(conta1);
		contaDespesaDAO.gravar(conta2);
		contaDespesaDAO.gravar(conta3);

		return "Url Mágica executada";
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
