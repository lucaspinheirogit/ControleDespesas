package br.inf.safetech.cd.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.inf.safetech.cd.dao.UsuarioDAO;
import br.inf.safetech.cd.models.Usuario;

@RequestMapping("/usuarios")
@Controller
public class UsuarioController {

	@Autowired
	private UsuarioDAO usuarioDao;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		//binder.addValidators(new UsuarioValidation());
	}
	
	@RequestMapping(value="/roles/form", method = RequestMethod.POST)
	public ModelAndView alterarRoles(@RequestParam String email) {
		
		Usuario usuario = usuarioDao.find(email.substring(1));
		System.out.println(usuario.getNome());
		System.out.println(usuario.getLogin());
		System.out.println(usuario.getSenha());
		System.out.println(usuario.getRoles());
		
		
		ModelAndView modelAndView = new ModelAndView("usuarios/formRoles");
		modelAndView.addObject("usuario", usuario);
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listar() {
		List<Usuario> usuarios = usuarioDao.listar();
		ModelAndView modelAndView = new ModelAndView("usuarios/listaUsuarios");
		modelAndView.addObject("usuarios", usuarios);
		return modelAndView;
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public ModelAndView form(Usuario usuario) {
		ModelAndView modelAndView = new ModelAndView("usuarios/form");
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView gravar(@Valid Usuario usuario, BindingResult result, RedirectAttributes redirectAttributes) {

		System.out.println(usuario);
		
		usuarioDao.gravar(usuario);
		redirectAttributes.addFlashAttribute("message", "Usuário cadastrado com sucesso!");

		/*if (usuarioDao.usuarioJaExiste(usuario)) {
			redirectAttributes.addFlashAttribute("message", "Erro! Usuario já existe!");
			return new ModelAndView("redirect:/usuarios/form");
		} else {
			usuarioDao.gravar(usuario);
			redirectAttributes.addFlashAttribute("message", "usuario cadastrado com sucesso!");
		}*/

		return new ModelAndView("redirect:/");
	}
	
	
	@RequestMapping(value= "/roles/gravar", method = RequestMethod.POST)
	public ModelAndView gravarRoles(Usuario usuario, RedirectAttributes redirectAttributes) {
		
		System.out.println("ROLES:" + usuario.getRoles());
		
		usuarioDao.gravarRoles(usuario.getLogin(), usuario.getRoles());

		redirectAttributes.addFlashAttribute("message", "Permissoes alteradas com sucesso!");
		return new ModelAndView("redirect:/usuarios");
	}

}
