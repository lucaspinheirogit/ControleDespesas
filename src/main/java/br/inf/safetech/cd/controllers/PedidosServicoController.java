package br.inf.safetech.cd.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import br.inf.safetech.cd.models.Pedido;

@RequestMapping("/pedidos")
@Controller
public class PedidosServicoController {

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listarPedidos() {
		
		String uri = "https://book-payment.herokuapp.com/orders";

		ResponseEntity<List<Pedido>> response = restTemplate.exchange(uri,
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Pedido>>() {
				});
		
		System.out.println("Response: " +response);
		
		List<Pedido> pedidos = response.getBody();
		
		System.out.println(pedidos);
		
		ModelAndView modelAndView = new ModelAndView("pedidos/pedidos");
		modelAndView.addObject("pedidos", pedidos);
		return modelAndView;

	}

}
