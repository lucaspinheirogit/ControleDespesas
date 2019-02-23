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
	private ClienteDAO clienteDAO;

	@Autowired
	private ContaDespesaDAO contaDespesaDAO;

	@Autowired
	private UsuarioDAO usuarioDAO;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listar() {
		List<Cliente> clientes = clienteDAO.listar();
		System.out.println(clientes);
		System.out.println(clientes.get(0));
		ModelAndView modelAndView = new ModelAndView("clientes/lista");
		modelAndView.addObject("clientes", clientes);
		return modelAndView;
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public ModelAndView form(ContaDespesa contaDespesa) {
		ModelAndView modelAndView = new ModelAndView("conta/form");

		List<Cliente> clientes = clienteDAO.listar();
		modelAndView.addObject("clientes", clientes);

		List<Usuario> usuarios = usuarioDAO.listar();
		modelAndView.addObject("usuarios", usuarios);

		return modelAndView;

	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView gravar(ContaDespesa conta, RedirectAttributes redirectAttributes) {

		Cliente c = clienteDAO.find(conta.getCliente().getId());
		Usuario u = usuarioDAO.find(conta.getUsuario().getId());
		System.out.println(conta.getDataInicio());
		System.out.println(conta.getDataFim());
		
		System.out.println(c);
		System.out.println(u);

		// contaDespesaDao.gravar(conta);
		redirectAttributes.addFlashAttribute("message", "Conta cadastrada com sucesso!");

		return new ModelAndView("redirect:/");
	}

}
