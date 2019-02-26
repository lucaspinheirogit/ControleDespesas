package br.inf.safetech.cd.controllers;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.inf.safetech.cd.dao.ClienteDAO;
import br.inf.safetech.cd.dao.ContaDespesaDAO;
import br.inf.safetech.cd.dao.UsuarioDAO;
import br.inf.safetech.cd.models.Cliente;
import br.inf.safetech.cd.models.ContaDespesa;
import br.inf.safetech.cd.models.Usuario;

@RequestMapping("/contas")
@Controller
public class ContaDespesaController {

	@Autowired
	private ClienteDAO clienteDAO;

	@Autowired
	private ContaDespesaDAO contaDespesaDAO;

	@Autowired
	private UsuarioDAO usuarioDAO;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping(value = "/todas", method = RequestMethod.GET)
	public ModelAndView listarTodas() {
		ModelAndView modelAndView = new ModelAndView("home");

		List<ContaDespesa> contas = contaDespesaDAO.listarTodas();

		modelAndView.addObject("contas", contas);
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listar(Principal principal, Authentication auth) {
		ModelAndView modelAndView = new ModelAndView("home");

		List<ContaDespesa> contas;
		boolean hasUserRole = auth.getAuthorities().stream()
		          .anyMatch(r -> r.getAuthority().equals("ROLE_USER"));
		
		if(hasUserRole) {
			Usuario usuario = usuarioDAO.loadUserByUsername(principal.getName());
			contas = contaDespesaDAO.listarPorColaborador(usuario);
			if(contas.size() == 0) 
				modelAndView.addObject("message", "Não existe nenhuma conta no seu nome");
		}else {
			System.out.println("ADMIN");
			contas = contaDespesaDAO.listar();
		}

		modelAndView.addObject("contas", contas);
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
	
	@RequestMapping(value = "/excluir", method = RequestMethod.POST)
	public ModelAndView excluir(@RequestParam("id") String id, RedirectAttributes redirectAttributes) {
		id = id.substring(1);
		
		contaDespesaDAO.excluir(Integer.parseInt(id));

		redirectAttributes.addFlashAttribute("message", "Conta excluida com sucesso!");

		return new ModelAndView("redirect:/");
	}

}
