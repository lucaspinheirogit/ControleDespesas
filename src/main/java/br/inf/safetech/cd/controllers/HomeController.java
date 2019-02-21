package br.inf.safetech.cd.controllers;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.inf.safetech.cd.dao.RoleDAO;
import br.inf.safetech.cd.dao.UsuarioDAO;
import br.inf.safetech.cd.models.Situacao;
import br.inf.safetech.cd.models.Role;
import br.inf.safetech.cd.models.Usuario;

@Controller
public class HomeController {
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Autowired
	private RoleDAO roleDAO;
	
	@RequestMapping("/")
	public ModelAndView index() {
		System.out.println("Entrou no controller");
		ModelAndView modelAndView = new ModelAndView("home");
		return modelAndView;
	}
	
	@Transactional
	@ResponseBody
	@RequestMapping("/magia")
	public String urlMagicaMaluca() {
				
		Role admin = new Role("ROLE_ADMIN");
		Role colaborador = new Role("ROLE_COLABORADOR");
		
		roleDAO.gravar(admin);
		roleDAO.gravar(colaborador);

		Usuario usuario = new Usuario(); 
	    usuario.setNome("Admin");
	    usuario.setLogin("admin@123");
	    usuario.setSenha("123456");
	    usuario.setSituacao(Situacao.A);
	    usuario.setRoles(Arrays.asList(admin));

	    usuarioDAO.gravar(usuario);

	    return "Url Mágica executada";
	}
}
