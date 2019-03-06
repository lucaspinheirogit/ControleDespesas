package br.inf.safetech.cd.controllers;

import java.math.BigDecimal;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.inf.safetech.cd.dao.ClienteDAO;
import br.inf.safetech.cd.dao.ContaDespesaDAO;
import br.inf.safetech.cd.dao.MovimentacaoContaDAO;
import br.inf.safetech.cd.dao.UsuarioDAO;
import br.inf.safetech.cd.models.Cliente;
import br.inf.safetech.cd.models.Conciliada;
import br.inf.safetech.cd.models.ContaDespesa;
import br.inf.safetech.cd.models.MovimentacaoConta;
import br.inf.safetech.cd.models.Responsavel;
import br.inf.safetech.cd.models.Situacao;
import br.inf.safetech.cd.models.Tipo;
import br.inf.safetech.cd.models.Usuario;

@RequestMapping("/contas")
@Controller
public class ContaDespesaController {

	@Autowired
	private ClienteDAO clienteDAO;

	@Autowired
	private ContaDespesaDAO contaDespesaDAO;

	@Autowired
	private MovimentacaoContaDAO movimentacaoContaDAO;

	@Autowired
	private UsuarioDAO usuarioDAO;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listar(Principal principal, Authentication auth) {
		ModelAndView modelAndView = new ModelAndView("home");

		List<ContaDespesa> contas;
		boolean hasUserRole = auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_USER"));

		if (hasUserRole) {
			Usuario usuario = usuarioDAO.loadUserByUsername(principal.getName());
			contas = contaDespesaDAO.listarPorColaborador(usuario);
			if (contas.size() == 0)
				modelAndView.addObject("message", "Não existe nenhuma conta no seu nome");
		} else {
			System.out.println("ADMIN");
			contas = contaDespesaDAO.listar();
		}

		List<Usuario> usuarios = usuarioDAO.listar();
		List<Cliente> clientes = clienteDAO.listar();

		modelAndView.addObject("usuarios", usuarios);
		modelAndView.addObject("clientes", clientes);
		modelAndView.addObject("contas", contas);
		return modelAndView;
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public ModelAndView form(ContaDespesa contaDespesa) {
		ModelAndView modelAndView = new ModelAndView("conta/form");

		List<Cliente> clientes = clienteDAO.listar();
		modelAndView.addObject("clientes", clientes);

		List<Usuario> usuarios = usuarioDAO.listar();
		modelAndView.addObject("usuarios", usuarios);

		return modelAndView;

	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView gravar(ContaDespesa conta, RedirectAttributes redirectAttributes) throws ParseException {

		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = sf.parse(sf.format(new Date()));
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		Cliente c = clienteDAO.find(conta.getCliente().getId());
		Usuario u = usuarioDAO.find(conta.getUsuario().getId());

		conta.setCliente(c);
		conta.setUsuario(u);
		conta.setDataInicio(cal);
		conta.setSituacao(Situacao.ATIVA);

		contaDespesaDAO.gravar(conta);

		redirectAttributes.addFlashAttribute("message", "Conta cadastrada com sucesso!");

		return new ModelAndView("redirect:/contas");
	}

	@RequestMapping(value = "/encerrar", method = RequestMethod.POST)
	public ModelAndView encerrar(Principal principal, @RequestParam("id") String id,
			@RequestParam("opcao") String opcao, @RequestParam("saldo") String saldo,
			RedirectAttributes redirectAttributes) throws NumberFormatException, ParseException {
		id = id.substring(1);
		opcao = opcao.substring(1);
		saldo = saldo.substring(1);

		MovimentacaoConta m = new MovimentacaoConta();
		ContaDespesa c = contaDespesaDAO.find(Integer.parseInt(id));
		Usuario u = usuarioDAO.loadUserByUsername(principal.getName());
		m.setConta(c);
		m.setTipo(Tipo.DEBITO);
		m.setConciliada(Conciliada.SIM);
		m.setCriadoPor(u);
		m.setDescricao(opcao);
		m.setValor(new BigDecimal(saldo)); // Valor que vem do form
		m.setResponsavel(Responsavel.EMPRESA);

		movimentacaoContaDAO.gravar(m);

		contaDespesaDAO.encerrar(Integer.parseInt(id));
		redirectAttributes.addFlashAttribute("message", "Conta encerrada com sucesso!");

		return new ModelAndView("redirect:/contas");
	}

	@RequestMapping(value = "/encerrar/form", method = RequestMethod.POST)
	public ModelAndView encerrarForm(@RequestParam("id") String id, RedirectAttributes redirectAttributes)
			throws NumberFormatException, ParseException {
		ModelAndView modelAndView = new ModelAndView("conta/encerrar");
		id = id.substring(1);
		ContaDespesa conta = contaDespesaDAO.find(Integer.parseInt(id));
		BigDecimal saldo = contaDespesaDAO.calculaSaldo(Integer.parseInt(id));

		System.out.println(saldo);

		if (contaDespesaDAO.estaLiquidada(Integer.parseInt(id))) {

			if (saldo.compareTo(BigDecimal.ZERO) != 0) {
				System.out.println("diferente de zero, vou pro form");
				modelAndView.addObject("conta", conta);
				modelAndView.addObject("saldo", saldo);
				return modelAndView;
			} else {
				System.out.println("igual a zero, vou encerrar");
				contaDespesaDAO.encerrar(Integer.parseInt(id));
				redirectAttributes.addFlashAttribute("message", "Conta encerrada com sucesso!");
				return new ModelAndView("redirect:/contas");
			}

		} else {
			redirectAttributes.addFlashAttribute("message",
					"Não é possível encerrar uma conta que não está liquidada!");
			return new ModelAndView("redirect:/contas");
		}

	}

	@RequestMapping(value = "/buscar", method = RequestMethod.POST)
	public ModelAndView buscar(Principal principal, Authentication auth, @RequestParam("usuario") String usuario,
			@RequestParam("cliente") String cliente, @RequestParam("dataInicio") String dataInicio,
			@RequestParam("dataFinal") String dataFinal, RedirectAttributes redirectAttributes) throws ParseException {
		ModelAndView modelAndView = new ModelAndView("home");
		usuario = usuario.substring(1);
		cliente = cliente.substring(1);
		dataFinal = dataFinal.substring(1);
		dataInicio = dataInicio.substring(1);
		
		Usuario teste = (Usuario) principal;
		System.out.println(teste);

		Calendar cal_dataInicio = null;
		cal_dataInicio = StringToDate("20/01/2000");

		Calendar cal_dataFinal = null;
		cal_dataFinal = StringToDate("20/01/2200");

		if (dataInicio.length() > 0) {
			if (!dataInicio.matches("\\d{2}/\\d{2}/\\d{4}")) {
				redirectAttributes.addFlashAttribute("message",
						"Erro! Data de início deve estar no formato dd/MM/yyyy!");
				return new ModelAndView("redirect:/contas");
			} else {
				System.out.println("data final valida");
				cal_dataInicio = StringToDate(dataInicio);
			}
		}

		if (dataFinal.length() > 0) {
			if (!dataFinal.matches("\\d{2}/\\d{2}/\\d{4}")) {
				redirectAttributes.addFlashAttribute("message",
						"Erro! Data de encerramento deve estar no formato dd/MM/yyyy!");
				return new ModelAndView("redirect:/contas");
			} else {
				System.out.println("data final valida");
				cal_dataFinal = StringToDate(dataFinal);
			}
		}

		System.out.println("Printando calendars");
		System.out.println(cal_dataInicio);
		System.out.println(cal_dataFinal);

		List<ContaDespesa> contas;
		boolean hasUserRole = auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_USER"));

		if (hasUserRole) {
			Usuario user = usuarioDAO.loadUserByUsername(principal.getName());
			contas = contaDespesaDAO.listarComFiltro(user.getNome(), cliente, cal_dataInicio, cal_dataFinal);
			if (contas.size() == 0)
				modelAndView.addObject("message", "Nenhuma conta encontrada");
		} else {
			System.out.println("ADMIN");
			contas = contaDespesaDAO.listarComFiltro(usuario, cliente, cal_dataInicio, cal_dataFinal);
		}

		List<Usuario> usuarios = usuarioDAO.listar();
		List<Cliente> clientes = clienteDAO.listar();

		modelAndView.addObject("message", "Resultados da busca: ");
		modelAndView.addObject("usuarios", usuarios);
		modelAndView.addObject("clientes", clientes);
		modelAndView.addObject("contas", contas);
		return modelAndView;
	}

	private Calendar StringToDate(String data) throws ParseException {
		String[] datas = data.split("/");
		data = datas[2] + "-" + datas[1] + "-" + datas[0];

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date date = sdf.parse(data);
		Calendar cal_data = Calendar.getInstance();
		cal_data.setTime(date);

		return cal_data;
	}

}
