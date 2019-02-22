package br.inf.safetech.cd.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.inf.safetech.cd.dao.ClienteDAO;
import br.inf.safetech.cd.models.Cliente;
import br.inf.safetech.cd.models.Usuario;

@RequestMapping("/clientes")
@Controller
public class ClienteController {

	@Autowired
	private ClienteDAO clienteDao;

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
	public ModelAndView form(Cliente cliente) {
		ModelAndView modelAndView = new ModelAndView("clientes/form");
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView gravar(Cliente cliente, RedirectAttributes redirectAttributes) {

		System.out.println(cliente);
		
		clienteDao.gravar(cliente);
		redirectAttributes.addFlashAttribute("message", "Cliente cadastrado com sucesso!");

		return new ModelAndView("redirect:/");
	}

}
