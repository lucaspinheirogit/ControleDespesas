package br.inf.safetech.cd.controllers;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.inf.safetech.cd.dao.ContaDespesaDAO;
import br.inf.safetech.cd.dao.MovimentacaoContaDAO;
import br.inf.safetech.cd.dao.UsuarioDAO;
import br.inf.safetech.cd.models.Conciliada;
import br.inf.safetech.cd.models.ContaDespesa;
import br.inf.safetech.cd.models.MovimentacaoConta;
import br.inf.safetech.cd.models.Responsavel;
import br.inf.safetech.cd.models.Usuario;

@RequestMapping("/movimentacoes")
@Controller
public class MovimentacaoContaController {

	@Autowired
	private ContaDespesaDAO contaDespesaDAO;
	
	@Autowired
	private UsuarioDAO usuarioDAO;

	@Autowired
	private MovimentacaoContaDAO movimentacaoContaDAO;

	@RequestMapping(value = "/ver", method = RequestMethod.POST)
	public ModelAndView listar(@RequestParam("id") String id) {
		id = id.substring(1);
		List<MovimentacaoConta> movimentacoes = movimentacaoContaDAO.listarPorId(Integer.parseInt(id));
		BigDecimal saldo = contaDespesaDAO.calculaSaldo(Integer.parseInt(id));
		
		ModelAndView modelAndView = new ModelAndView("movimentacoes/lista");
		modelAndView.addObject("movimentacoes", movimentacoes);
		modelAndView.addObject("saldo", saldo);
		return modelAndView;
	}
	
	@RequestMapping(value = "/editar", method = RequestMethod.POST)
	public ModelAndView editar(@RequestParam("id") String id) {
		id = id.substring(1);
		List<MovimentacaoConta> movimentacoes = movimentacaoContaDAO.listarPorId(Integer.parseInt(id));
		BigDecimal saldo = contaDespesaDAO.calculaSaldo(Integer.parseInt(id));
		
		ModelAndView modelAndView = new ModelAndView("movimentacoes/editar");
		modelAndView.addObject("movimentacoes", movimentacoes);
		modelAndView.addObject("saldo", saldo);
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
	public ModelAndView gravar(Principal principal, MovimentacaoConta movimentacaoConta, RedirectAttributes redirectAttributes) {

		ContaDespesa c = contaDespesaDAO.find(movimentacaoConta.getConta().getId());
		Usuario u = usuarioDAO.loadUserByUsername(principal.getName());
		movimentacaoConta.setConta(c);
		movimentacaoConta.setConciliada(Conciliada.NAO);
		movimentacaoConta.setCriadoPor(u);

		movimentacaoContaDAO.gravar(movimentacaoConta);

		redirectAttributes.addFlashAttribute("message", "Movimentação cadastrada com sucesso!");

		return new ModelAndView("redirect:/contas");
	}

	@RequestMapping(value = "/admin/conciliar", method = RequestMethod.POST)
	public ModelAndView conciliar(Authentication auth, RedirectAttributes redirectAttributes,
			@RequestParam("id") String id, @RequestParam("conta") String contaId, @RequestParam("tipo") String tipo) {
		id = id.substring(1);
		tipo = tipo.substring(1);
		contaId = contaId.substring(1);

		if (hasRole(auth, "ROLE_ADMIN")) {
			if (tipo.equals("SIM")) {
				movimentacaoContaDAO.conciliar(Integer.parseInt(id));
			} else {
				movimentacaoContaDAO.desconciliar(Integer.parseInt(id));
			}
			List<MovimentacaoConta> movimentacoes = movimentacaoContaDAO.listarPorId(Integer.parseInt(contaId));
			BigDecimal saldo = contaDespesaDAO.calculaSaldo(Integer.parseInt(id));
			
			ModelAndView modelAndView = new ModelAndView("movimentacoes/editar");
			modelAndView.addObject("movimentacoes", movimentacoes);
			modelAndView.addObject("saldo", saldo);
			modelAndView.addObject("message", "Movimentação atualizada com sucesso!");
			return modelAndView;
		} else {
			redirectAttributes.addFlashAttribute("message", "Erro! Operação restrita à administradores do sistema");
			return new ModelAndView("redirect:/contas");
		}

	}

	@RequestMapping(value = "/remover", method = RequestMethod.POST)
	public ModelAndView remover(@RequestParam("id") String id, @RequestParam("conta") String contaId) {
		ModelAndView modelAndView = new ModelAndView("movimentacoes/editar");
		id = id.substring(1);
		contaId = contaId.substring(1);

		if(movimentacaoContaDAO.estaConciliada(Integer.parseInt(id))) {
			System.out.println("Conciliada");
			modelAndView.addObject("message", "Não é possível remover uma movimentação conciliada!");
		}else {
			System.out.println("Nao Conciliada");
			movimentacaoContaDAO.remover(Integer.parseInt(id));
			modelAndView.addObject("message", "Movimentação removida com sucesso!");
		}

		List<MovimentacaoConta> movimentacoes = movimentacaoContaDAO.listarPorId(Integer.parseInt(contaId));
		BigDecimal saldo = contaDespesaDAO.calculaSaldo(Integer.parseInt(id));
		
		modelAndView.addObject("movimentacoes", movimentacoes);
		modelAndView.addObject("saldo", saldo);
		return modelAndView;
	}	
	
	@RequestMapping(value = "/alterarResponsavel", method = RequestMethod.POST)
	public ModelAndView alterarResponsavel(@RequestParam("id") String id, @RequestParam("conta") String contaId, @RequestParam("responsavel") String responsavel) {
		ModelAndView modelAndView = new ModelAndView("movimentacoes/editar");
		id = id.substring(1);
		contaId = contaId.substring(1);
		responsavel = responsavel.substring(1);
		
		Responsavel res = Responsavel.valueOf(responsavel);

		movimentacaoContaDAO.alterarResponsavel(Integer.parseInt(id), res);
		modelAndView.addObject("message", "Responsável alterado com sucesso!");

		List<MovimentacaoConta> movimentacoes = movimentacaoContaDAO.listarPorId(Integer.parseInt(contaId));
		BigDecimal saldo = contaDespesaDAO.calculaSaldo(Integer.parseInt(id));
		
		modelAndView.addObject("movimentacoes", movimentacoes);
		modelAndView.addObject("saldo", saldo);
		return modelAndView;
	}	

	private boolean hasRole(Authentication auth, String role) {
		return auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(role));
	}

}
