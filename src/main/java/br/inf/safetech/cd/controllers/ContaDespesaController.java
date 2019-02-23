package br.inf.safetech.cd.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.inf.safetech.cd.dao.ClienteDAO;
import br.inf.safetech.cd.dao.ContaDespesaDAO;
import br.inf.safetech.cd.dao.UsuarioDAO;
import br.inf.safetech.cd.models.Cliente;
import br.inf.safetech.cd.models.ContaDespesa;
import br.inf.safetech.cd.models.Usuario;

@RequestMapping("/conta")
@Controller
public class ContaDespesaController {

	@Autowired
	private ClienteDAO clienteDao;

	@Autowired
	private ContaDespesaDAO contaDespesaDao;

	@Autowired
	private UsuarioDAO usuarioDao;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listar() {
		List<Cliente> clientes = clienteDao.listar();
		System.out.println(clientes);
		System.out.println(clientes.get(0));
		ModelAndView modelAndView = new ModelAndView("clientes/lista");
		modelAndView.addObject("clientes", clientes);
		return modelAndView;
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public ModelAndView form(ContaDespesa contaDespesa) {
		ModelAndView modelAndView = new ModelAndView("conta/form");

		List<Cliente> clientes = clienteDao.listar();
		modelAndView.addObject("clientes", clientes);

		List<Usuario> usuarios = usuarioDao.listar();
		modelAndView.addObject("usuarios", usuarios);

		return modelAndView;

	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView gravar(ContaDespesa conta, RedirectAttributes redirectAttributes) {

		System.out.println(conta.getCliente());
		System.out.println(conta.getCliente().getId());

		// contaDespesaDao.gravar(conta);
		redirectAttributes.addFlashAttribute("message", "Conta cadastrada com sucesso!");

		return new ModelAndView("redirect:/");
	}

}
