package br.inf.safetech.cd.controllers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
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

	@RequestMapping("/")
	public ModelAndView index() {
		return new ModelAndView("redirect:/contas");
	}

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

		clienteDAO.gravar(cliente1);
		clienteDAO.gravar(cliente2);

		Usuario usuario = new Usuario();
		usuario.setNome("Admin");
		usuario.setLogin("admin@123");
		usuario.setSenha("123456");
		usuario.setSituacao(Situacao.ATIVO);
		usuario.setRoles(Arrays.asList(admin));
		Usuario usuario2 = new Usuario();
		usuario2.setNome("user");
		usuario2.setLogin("user@123");
		usuario2.setSenha("123456");
		usuario2.setSituacao(Situacao.ATIVO);
		usuario2.setRoles(Arrays.asList(user));

		usuarioDAO.gravar(usuario);
		usuarioDAO.gravar(usuario2);

		ContaDespesa conta1 = new ContaDespesa(usuario2, cliente1, new GregorianCalendar(2004, 2, 3),
				new GregorianCalendar(2004, 2, 16), Situacao.ATIVO);
		ContaDespesa conta2 = new ContaDespesa(usuario2, cliente2, new GregorianCalendar(2004, 3, 25),
				new GregorianCalendar(2004, 4, 5), Situacao.ATIVO);
		ContaDespesa conta3 = new ContaDespesa(usuario, cliente2, new GregorianCalendar(2004, 7, 16),
				new GregorianCalendar(2004, 8, 1), Situacao.ATIVO);

		MovimentacaoConta mc1 = new MovimentacaoConta(conta1, Tipo.CREDITO, Conciliada.SIM, new BigDecimal(300),
				"Taxi");
		MovimentacaoConta mc2 = new MovimentacaoConta(conta1, Tipo.DEBITO, Conciliada.SIM, new BigDecimal(500), "Uber");

		movimentacaoContaDAO.gravar(mc1);
		movimentacaoContaDAO.gravar(mc2);

		contaDespesaDAO.gravar(conta1);
		contaDespesaDAO.gravar(conta2);
		contaDespesaDAO.gravar(conta3);

		return "Url Mágica executada";
	}
}
