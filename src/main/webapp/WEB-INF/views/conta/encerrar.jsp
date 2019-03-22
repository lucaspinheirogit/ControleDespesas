<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<c:url value="/" var="contextPath" />

<style>
.custom-control {
	display: flex !important;
	justify-content: space-between;
}
</style>

<tags:pageTemplate titulo="Cadastro de contas de despesa">

	<jsp:attribute name="extraScripts">
<script>
$(document).ready(function() {
		var saldoTotal = ${saldo};
		console.log(saldoTotal);
		
		$('#checkbox1').change(() => {
			$('#vale').prop('disabled', function(i, v) { return !v; });
		});
		
		$('#checkbox2').change(() => {
			$('#devolucao').prop('disabled', function(i, v) { return !v; });
		});
		
		$('#vale').on('change', function() {
			$('#devolucao').val(saldoTotal - this.value);
		});
		
		$('#devolucao').on('change', function() {
			$('#vale').val(saldoTotal - this.value);
		});
		
		$("form").submit(function(e){
			valorDevolucao = parseInt($('#devolucao').val());
			valorVale = parseInt($('#vale').val());
			valorInformado = valorDevolucao + valorVale;
			
			console.log(valorInformado);
			console.log(saldoTotal)
			
			if(valorInformado < saldoTotal){
				$('#message').text("Ainda há saldo remanescente de R$ " + (saldoTotal - valorInformado));
            	e.preventDefault();
			}else{
				$('#message').text("");
				return confirm('Deseja encerrar?');
			}
        });
		
});	
</script>
</jsp:attribute>

	<jsp:body>
	<div class="container container-login">
		<h5 id="message" class="color-red">${ message }</h5>

		<div class="jumbotron">
			<h1 class="display-3">Encerrar conta?</h1>
			<p class="lead">O saldo da conta é: ${ saldo } ,
				informe como gostaria de encerrar a conta: </p>
			<hr class="my-4">
			<form action="${s:mvcUrl('CDC#encerrar').build() }" method="post">
<%-- 					onsubmit="return confirm('Deseja encerrar?');"> --%>
				<div class="form-group">
					<div class="custom-control custom-radio">
						<div>
							<input type="checkbox" id="checkbox1" name="vale" value="Vale"
									Class="custom-control-input" required /> <label
									class="custom-control-label" for="checkbox1">Vale</label>
						</div>
						<input id="vale" type="number" name="valorVale" value="0"
								max="${saldo }" min="0" step="0.01" disabled />
					</div>
					<div class="custom-control custom-radio">
						<div>
							<input type="checkbox" id="checkbox2" name="devolucao"
									value="Devolução" Class="custom-control-input" required /> <label
									class="custom-control-label" for="checkbox2">Devolução</label>
						</div>
							<input id="devolucao" type="number" name="valorDevolucao"
								value="0" max="${saldo }" min="0" step="0.01" disabled />
					</div>
				</div>
				<input type="hidden" name="id" value="${ conta.id }" />
				<input type="hidden" name="saldo" value="${ saldo }" />
				<button type="submit" class="btn btn-danger">Encerrar</button>
			</form>
		</div>
	</div>
	</jsp:body>

</tags:pageTemplate>