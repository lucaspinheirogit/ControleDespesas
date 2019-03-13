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
			redirectAttributes.addFlashAttribute("message", "Erro! Colaborador já existe!");
			return new ModelAndView("redirect:/usuarios/form");
		}
		usuarioDao.gravar(usuario);
		redirectAttributes.addFlashAttribute("message", "Usuário cadastrado com sucesso!");
		return new ModelAndView("redirect:/contas");
	}

	@RequestMapping(value = "/alterarSenhaForm", method = RequestMethod.GET)
	public ModelAndView alterarSenhaForm(Principal principal) {
		Usuario usuarioLogado = (Usuario) ((Authentication) principal).getPrincipal();
		usuarioLogado.setSenha("");
		ModelAndView modelAndView = new ModelAndView("usuarios/formSenha");
		modelAndView.addObject("usuario", usuarioLogado);
		return modelAndView;
	}

	@RequestMapping(value = "/alterarSenha", method = RequestMethod.POST)
	public ModelAndView alterarSenha(Usuario usuario, RedirectAttributes redirectAttributes,
			BindingResult result, Principal principal) {
		if(!usuario.getSenha().equals(usuario.getSenhaRepetida())) {
			redirectAttributes.addFlashAttribute("message", "As senhas precisam ser iguais!");
			return new ModelAndView("redirect:/usuarios/alterarSenhaForm");
		}
		usuarioDao.alterarSenha(usuario.getId(), usuario.getSenha());
		redirectAttributes.addFlashAttribute("message", "senha alterada com sucesso!");
		return new ModelAndView("redirect:/contas");
	}

}
