package br.inf.safetech.cd.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import br.inf.safetech.cd.dao.ProdutoDAO;
import br.inf.safetech.cd.models.Produto;
import br.inf.safetech.cd.models.Relatorio;

@Controller
@RequestMapping("/relatorio-produtos")
public class RelatorioProdutosController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ProdutoDAO produtoDao;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Relatorio gerarRelatorio(@RequestParam("data") Optional<String> data) throws ParseException {

		List<Produto> produtos;

		if(!data.isPresent()) {
			produtos = produtoDao.listar();
		}else {
			String dia = data.get().split("-")[2];
			String mes = data.get().split("-")[1];
			String ano = data.get().split("-")[0];
			String dataLancamento = dia + "/" + mes + "/" + ano;

			SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
			Date dateObj = curFormater.parse(dataLancamento);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateObj);
			produtos = produtoDao.listarPorData(calendar);
		}

		Date dataGeracao = new Date();
		int quantidade = produtos.size();

		return new Relatorio(dataGeracao, quantidade, produtos);
	}

}