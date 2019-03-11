package br.inf.safetech.cd.controllers;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.inf.safetech.cd.dao.UsuarioDAO;
import br.inf.safetech.cd.models.Usuario;
import br.inf.safetech.cd.validation.UsuarioValidation;

@RequestMapping("/usuarios")
@Controller
public class UsuarioController {

	@Autowired
	private UsuarioDAO usuarioDao;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new UsuarioValidation());
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listar() {
		List<Usuario> usuarios = usuarioDao.listar();
		ModelAndView modelAndView = new ModelAndView("usuarios/lista");
		modelAndView.addObject("usuarios", usuarios);
		return modelAndView;
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public ModelAndView form(Usuario usuario) {
		List<Usuario> usuarios = usuarioDao.listar();
		ModelAndView modelAndView = new ModelAndView("usuarios/form");
		modelAndView.addObject("usuarios", usuarios);
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView gravar(@Valid Usuario usuario, BindingResult result, RedirectAttributes redirectAttributes) {

		if (usuarioDao.usuarioJaExiste(usuario)) {
			System.out.println("Ja existe");
			redirectAttributes.addFlashAttribute("message", "Erro! Colaborador já existe!");
			return new ModelAndView("redirect:/usuarios/form");
		}

		System.out.println("gravando novo colaborador");
		usuarioDao.gravar(usuario);
		redirectAttributes.addFlashAttribute("message", "Usuário cadastrado com sucesso!");
		return new ModelAndView("redirect:/contas");
	}
	
	@RequestMapping(value = "/testar", method = RequestMethod.GET)
	public ModelAndView teste(Principal principal) {

		Usuario usuarioLogado = (Usuario) ((Authentication) principal).getPrincipal();
		
		System.out.println("Usuario: ");
		System.out.println(usuarioLogado);
		System.out.println(usuarioLogado.getId());
		
		ModelAndView modelAndView = new ModelAndView("usuarios/formSenha");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/alterarSenha", method = RequestMethod.GET)
	public ModelAndView alterarSenha(@Valid Usuario usuario, BindingResult result, Principal principal) {

		if (result.hasErrors()) {
			return form(usuario);
		}
		
		Usuario usuarioLogado = (Usuario) ((Authentication) principal).getPrincipal();
		
		System.out.println("Usuario: ");
		System.out.println(usuarioLogado);
		System.out.println(usuarioLogado.getId());
		
		ModelAndView modelAndView = new ModelAndView("usuarios/formSenha");
		
		return modelAndView;
	}

}
