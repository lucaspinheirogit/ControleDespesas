package br.inf.safetech.cd.controllers;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.inf.safetech.cd.dao.UsuarioDAO;
import br.inf.safetech.cd.models.Usuario;

@RequestMapping("/usuarios")
@Controller
public class UsuarioController {

	@Autowired
	private UsuarioDAO usuarioDao;

	//Formulario de cadastro de novo usuário
	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public ModelAndView form(Usuario usuario) {
		List<Usuario> usuarios = usuarioDao.listar();
		ModelAndView modelAndView = new ModelAndView("usuarios/form");
		modelAndView.addObject("usuarios", usuarios);
		return modelAndView;
	}

	//Criação de novo usuário
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView gravar(Usuario usuario, RedirectAttributes redirectAttributes) {
		if (usuarioDao.usuarioJaExiste(usuario)) {
			redirectAttributes.addFlashAttribute("message", "Erro! Colaborador já existe!");
			return new ModelAndView("redirect:/usuarios/form");
		}
		System.out.println("usuario no controller");
		System.out.println(usuario);
		usuarioDao.gravar(usuario);
		redirectAttributes.addFlashAttribute("message", "Usuário cadastrado com sucesso!");
		return new ModelAndView("redirect:/contas");
	}

	//Formulário de alteração de senha do usuário
	@RequestMapping(value = "/alterarSenhaForm", method = RequestMethod.GET)
	public ModelAndView alterarSenhaForm(Principal principal) {
		Usuario usuarioLogado = (Usuario) ((Authentication) principal).getPrincipal();
		usuarioLogado.setSenha("");
		ModelAndView modelAndView = new ModelAndView("usuarios/formSenha");
		modelAndView.addObject("usuario", usuarioLogado);
		return modelAndView;
	}

	//Alteração de senha do usuário
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
