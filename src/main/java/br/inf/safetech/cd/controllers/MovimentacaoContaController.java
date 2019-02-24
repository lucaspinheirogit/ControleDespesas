package br.inf.safetech.cd.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.inf.safetech.cd.dao.ContaDespesaDAO;
import br.inf.safetech.cd.dao.MovimentacaoContaDAO;
import br.inf.safetech.cd.models.ContaDespesa;
import br.inf.safetech.cd.models.MovimentacaoConta;

@RequestMapping("/movimentacoes")
@Controller
public class MovimentacaoContaController {

	@Autowired
	private ContaDespesaDAO contaDespesaDAO;

	@Autowired
	private MovimentacaoContaDAO movimentacaoContaDAO;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView listar(@PathVariable("id") Integer id) {
		List<MovimentacaoConta> movimentacoes = movimentacaoContaDAO.listarPorId(id);
		System.out.println(movimentacoes);

		ModelAndView modelAndView = new ModelAndView("movimentacoes/lista");
		modelAndView.addObject("movimentacoes", movimentacoes);
		return modelAndView;
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public ModelAndView form(MovimentacaoConta movimentacaoConta) {
		ModelAndView modelAndView = new ModelAndView("movimentacoes/form");
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView gravar(MovimentacaoConta movimentacaoConta, RedirectAttributes redirectAttributes) {

		System.out.println(movimentacaoConta.getDescricao());
		System.out.println(movimentacaoConta.getValor());
		System.out.println(movimentacaoConta.getTipo());

		// contaDespesaDao.gravar(conta);
		redirectAttributes.addFlashAttribute("message", "Conta cadastrada com sucesso!");

		return new ModelAndView("redirect:/");
	}

}
