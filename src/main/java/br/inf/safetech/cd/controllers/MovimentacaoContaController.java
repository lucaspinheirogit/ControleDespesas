package br.inf.safetech.cd.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.inf.safetech.cd.dao.ContaDespesaDAO;
import br.inf.safetech.cd.dao.MovimentacaoContaDAO;
import br.inf.safetech.cd.models.Conciliada;
import br.inf.safetech.cd.models.ContaDespesa;
import br.inf.safetech.cd.models.MovimentacaoConta;

@RequestMapping("/movimentacoes")
@Controller
public class MovimentacaoContaController {

	@Autowired
	private ContaDespesaDAO contaDespesaDAO;

	@Autowired
	private MovimentacaoContaDAO movimentacaoContaDAO;

	@RequestMapping(value = "/ver", method = RequestMethod.POST)
	public ModelAndView listar(@RequestParam("id") String id) {
		id = id.substring(1);
		List<MovimentacaoConta> movimentacoes = movimentacaoContaDAO.listarPorId(Integer.parseInt(id));
		ContaDespesa conta = contaDespesaDAO.find(Integer.parseInt(id));

		ModelAndView modelAndView = new ModelAndView("movimentacoes/lista");
		modelAndView.addObject("movimentacoes", movimentacoes);
		modelAndView.addObject("conta", conta);
		return modelAndView;
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public ModelAndView form(MovimentacaoConta movimentacaoConta, @RequestParam("id") String id) {
		id = id.substring(1);
		ContaDespesa conta = contaDespesaDAO.find(Integer.parseInt(id));
		movimentacaoConta.setConta(conta);
		
		ModelAndView modelAndView = new ModelAndView("movimentacoes/form");
		modelAndView.addObject("conta", conta);
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView gravar(MovimentacaoConta movimentacaoConta, RedirectAttributes redirectAttributes) {

		ContaDespesa c = contaDespesaDAO.find(movimentacaoConta.getConta().getId());
		movimentacaoConta.setConta(c);
		movimentacaoConta.setConciliada(Conciliada.NAO);

		movimentacaoContaDAO.gravar(movimentacaoConta);
		
		redirectAttributes.addFlashAttribute("message", "Conta cadastrada com sucesso!");

		return new ModelAndView("redirect:/contas");
	}

}
