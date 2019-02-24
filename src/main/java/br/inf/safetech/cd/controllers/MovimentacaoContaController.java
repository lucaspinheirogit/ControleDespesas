package br.inf.safetech.cd.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.inf.safetech.cd.dao.ContaDespesaDAO;
import br.inf.safetech.cd.dao.MovimentacaoContaDAO;
import br.inf.safetech.cd.models.MovimentacaoConta;

@RequestMapping("/movimentacoes")
@Controller
public class MovimentacaoContaController {

	@Autowired
	private ContaDespesaDAO contaDespesaDAO;

	@Autowired
	private MovimentacaoContaDAO movimentacaoContaDAO;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listar(@RequestParam Integer id) {
		List<MovimentacaoConta> movimentacoes = movimentacaoContaDAO.listarPorId(id);
		System.out.println(movimentacoes);
		
		ModelAndView modelAndView = new ModelAndView("movimentacoes/lista");
		modelAndView.addObject("movimentacoes", movimentacoes);
		return modelAndView;
	}

	/*
	 * @RequestMapping(value = "/form", method = RequestMethod.GET) public
	 * ModelAndView form(ContaDespesa contaDespesa) { ModelAndView modelAndView =
	 * new ModelAndView("conta/form");
	 * 
	 * List<Cliente> clientes = clienteDAO.listar();
	 * modelAndView.addObject("clientes", clientes);
	 * 
	 * List<Usuario> usuarios = usuarioDAO.listar();
	 * modelAndView.addObject("usuarios", usuarios);
	 * 
	 * return modelAndView;
	 * 
	 * }
	 * 
	 * @RequestMapping(method = RequestMethod.POST) public ModelAndView
	 * gravar(ContaDespesa conta, RedirectAttributes redirectAttributes) {
	 * 
	 * Cliente c = clienteDAO.find(conta.getCliente().getId()); Usuario u =
	 * usuarioDAO.find(conta.getUsuario().getId());
	 * System.out.println(conta.getDataInicio());
	 * System.out.println(conta.getDataFim());
	 * 
	 * System.out.println(c); System.out.println(u);
	 * 
	 * // contaDespesaDao.gravar(conta);
	 * redirectAttributes.addFlashAttribute("message",
	 * "Conta cadastrada com sucesso!");
	 * 
	 * return new ModelAndView("redirect:/"); }
	 */
}
