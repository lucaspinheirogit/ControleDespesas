package br.inf.safetech.cd.controllers;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import br.inf.safetech.cd.models.Situacao;
import br.inf.safetech.cd.models.Tipo;
import br.inf.safetech.cd.models.Usuario;

@RequestMapping("/movimentacoes")
@Controller
public class MovimentacaoContaController {
	//TODO: Refatorar o método de listar e editar, pois há muito código duplicado

	@Autowired
	private ContaDespesaDAO contaDespesaDAO;

	@Autowired
	private UsuarioDAO usuarioDAO;

	@Autowired
	private MovimentacaoContaDAO movimentacaoContaDAO;

	//Listagem das movimentacoes de uma conta de despesa
	@RequestMapping(value = "/ver", method = RequestMethod.GET)
	public ModelAndView listar(Authentication auth, Principal principal, @RequestParam("id") String id,
			RedirectAttributes redirectAttributes) {
		List<MovimentacaoConta> movimentacoes = movimentacaoContaDAO.listarPorId(Integer.parseInt(id));
		BigDecimal saldoGeral = contaDespesaDAO.calculaSaldoGeral(Integer.parseInt(id));
		BigDecimal saldoLiquido = contaDespesaDAO.calculaSaldoLiquido(Integer.parseInt(id));
		
		
		
		if(movimentacoes.size() == 0) {
			redirectAttributes.addFlashAttribute("message", "Essa conta não possui movimentações!");
			return new ModelAndView("redirect:/contas");
		}

		int colaborador = movimentacoes.get(0).getConta().getUsuario().getId();
		Usuario usuarioLogado = (Usuario) ((Authentication) principal).getPrincipal();
		
//		if(movimentacoes.get(0).getConta().getSituacao() == Situacao.ENCERRADA) {
//			saldoLiquido = new BigDecimal(0);
//		}

		if (!hasRole(auth, "ROLE_ADMIN")) {
			if (colaborador != usuarioLogado.getId()) {
				redirectAttributes.addFlashAttribute("message", "ERRO! Você só pode ver as contas que lhe pertencem!");
				return new ModelAndView("redirect:/contas");
			}
		}

		ModelAndView modelAndView = new ModelAndView("movimentacoes/lista");
		modelAndView.addObject("movimentacoes", movimentacoes);
		modelAndView.addObject("saldoGeral", saldoGeral);
		modelAndView.addObject("saldoLiquido", saldoLiquido);
		return modelAndView;
	}

	//Listagem das movimentacoes na pagina de edição das mesmas
	@RequestMapping(value = "/editar", method = RequestMethod.GET)
	public ModelAndView editar(Authentication auth, Principal principal, RedirectAttributes redirectAttributes,
			@RequestParam("id") String id) {
		//id = id.substring(1);
		List<MovimentacaoConta> movimentacoes = movimentacaoContaDAO.listarPorId(Integer.parseInt(id));
		BigDecimal saldoGeral = contaDespesaDAO.calculaSaldoGeral(Integer.parseInt(id));
		BigDecimal saldoLiquido = contaDespesaDAO.calculaSaldoLiquido(Integer.parseInt(id));

		if(movimentacoes.size() == 0) {
			redirectAttributes.addFlashAttribute("message", "Essa conta não possui movimentações!");
			return new ModelAndView("redirect:/contas");
		}

		int colaborador = movimentacoes.get(0).getConta().getUsuario().getId();
		Usuario usuarioLogado = (Usuario) ((Authentication) principal).getPrincipal();
		
		Situacao situacao = movimentacoes.get(0).getConta().getSituacao();

		if (!hasRole(auth, "ROLE_ADMIN")) {
			if (colaborador != usuarioLogado.getId()) {
				redirectAttributes.addFlashAttribute("message", "ERRO! Você só pode editar as contas que lhe pertencem!");
				return new ModelAndView("redirect:/contas");
			} else if (situacao.equals(Situacao.ENCERRADA)) {
				redirectAttributes.addFlashAttribute("message", "ERRO! A conta está encerrada !");
				return new ModelAndView("redirect:/contas");
			}
		} else {
			if (situacao.equals(Situacao.ENCERRADA)) {
				redirectAttributes.addFlashAttribute("message", "ERRO! A conta está encerrada !");
				return new ModelAndView("redirect:/contas");
			}
		}

		List<Responsavel> responsaveis = new ArrayList<>();
		responsaveis.add(Responsavel.EMPRESA);
		responsaveis.add(Responsavel.COLABORADOR);
		responsaveis.add(Responsavel.CLIENTE);
		
		ModelAndView modelAndView = new ModelAndView("movimentacoes/editar");
		modelAndView.addObject("movimentacoes", movimentacoes);
		modelAndView.addObject("responsaveis", responsaveis);
		modelAndView.addObject("saldoGeral", saldoGeral);
		modelAndView.addObject("saldoLiquido", saldoLiquido);
		return modelAndView;
	}

	//Formulario de cadastro de movimentacao
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public ModelAndView form(MovimentacaoConta movimentacaoConta,HttpServletRequest request, @RequestParam("id") String id) {
		id = id.substring(1);
		ContaDespesa conta = contaDespesaDAO.find(Integer.parseInt(id));
		movimentacaoConta.setConta(conta);
		
		String referer = request.getHeader("Referer");

		ModelAndView modelAndView = new ModelAndView("movimentacoes/form");
		modelAndView.addObject("conta", conta);
		modelAndView.addObject("referer", referer);
		return modelAndView;
	}

	//Criacao de movimentacao de conta
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView gravar(Principal principal, MovimentacaoConta movimentacaoConta,
			RedirectAttributes redirectAttributes, HttpServletRequest request, @RequestParam("valor") String valor) {
		valor = valor.substring(1);
		
		valor=valor.replace(".","");
		valor=valor.replace(",",".");
		
		BigDecimal valorMovimentacao = new BigDecimal(valor);

		ContaDespesa c = contaDespesaDAO.find(movimentacaoConta.getConta().getId());
		Usuario u = (Usuario) ((Authentication) principal).getPrincipal();
		movimentacaoConta.setConta(c);
		
		if(movimentacaoConta.getTipo() == Tipo.CREDITO){
			movimentacaoConta.setConciliada(Conciliada.SIM);
			movimentacaoConta.setResponsavel(Responsavel.EMPRESA);
		}else {
			movimentacaoConta.setConciliada(Conciliada.NAO);
		}
	
		movimentacaoConta.setCriadoPor(u);
		movimentacaoConta.setValor(valorMovimentacao);

		movimentacaoContaDAO.gravar(movimentacaoConta);
		
		redirectAttributes.addFlashAttribute("message", "Movimentação cadastrada com sucesso!");
		return new ModelAndView("redirect:/contas");
	}

	//Conciliacao e desconciliacao de movimentacao 
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

			redirectAttributes.addFlashAttribute("message", "Movimentação atualizada com sucesso");
			return new ModelAndView("redirect:/movimentacoes/editar?id="+contaId);
		} else {
			redirectAttributes.addFlashAttribute("message", "Erro! Operação restrita à administradores do sistema");
			return new ModelAndView("redirect:/contas");
		}

	}

	//Remoção de movimentação, caso a mesma esteja desconciliada pelo administrador
	@RequestMapping(value = "/remover", method = RequestMethod.POST)
	public ModelAndView remover(RedirectAttributes redirectAttributes, @RequestParam("id") String id, @RequestParam("conta") String contaId) {
		ModelAndView modelAndView = new ModelAndView("movimentacoes/editar");
		id = id.substring(1);
		contaId = contaId.substring(1);

		if (movimentacaoContaDAO.estaConciliada(Integer.parseInt(id))) {
			redirectAttributes.addFlashAttribute("message", "Não é possível remover uma movimentação conciliada!");
		} else {
			movimentacaoContaDAO.remover(Integer.parseInt(id));
			redirectAttributes.addFlashAttribute("message", "Movimentação removida com sucesso");
		}

		return new ModelAndView("redirect:/movimentacoes/editar?id="+contaId);
	}

	//Alteração do responsável por determinada movimentação
	@RequestMapping(value = "/admin/alterarResponsavel", method = RequestMethod.POST)
	public ModelAndView alterarResponsavel(RedirectAttributes redirectAttributes, @RequestParam("id") String id, @RequestParam("conta") String contaId,
			@RequestParam("responsavel") String responsavel) {
		ModelAndView modelAndView = new ModelAndView("movimentacoes/editar");
		id = id.substring(1);
		contaId = contaId.substring(1);
		responsavel = responsavel.substring(1);

		Responsavel res = Responsavel.valueOf(responsavel);

		movimentacaoContaDAO.alterarResponsavel(Integer.parseInt(id), res);
		
		redirectAttributes.addFlashAttribute("message", "Responsável alterado com sucesso!");
		return new ModelAndView("redirect:/movimentacoes/editar?id="+contaId);
	}

	//Método que checa se o usuario tem alguma Role (permissão)
	private boolean hasRole(Authentication auth, String role) {
		return auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(role));
	}

}
