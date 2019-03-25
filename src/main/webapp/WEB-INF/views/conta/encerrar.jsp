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
	<script
			src="https://cdnjs.cloudflare.com/ajax/libs/jquery-maskmoney/3.0.2/jquery.maskMoney.min.js"></script>
<script>
$(document).ready(function() {
		var saldoTotal = parseFloat(${saldo});
		console.log(saldoTotal);
		
		$('#checkbox1').change(() => {
			$('#vale').prop('disabled', function(i, v) { return !v; });
		});
		
		$('#checkbox2').change(() => {
			$('#devolucao').prop('disabled', function(i, v) { return !v; });
		});
		
		$('#vale').on('blur', function() {
			vale = parseFloat(this.value.replace( '.', '' ).replace( ',', '.' ));
			
			if(vale > saldoTotal){
				$('#vale').val(saldoTotal);
				$('#devolucao').val(0);
			}else{
				if(vale == 0) $('#vale').val(0);
				resultado = String((saldoTotal - vale).toFixed(2));
				resultado = resultado.replace( '.', ',' );
				$('#devolucao').val(resultado);
			}
		});
		
		$('#devolucao').on('blur', function() {
			devolucao = parseFloat(this.value.replace( '.', '' ).replace( ',', '.' ));
			resultado = String(saldoTotal - devolucao);

			if(devolucao > saldoTotal){
				$('#devolucao').val(saldoTotal);
				$('#vale').val(0);
			}else{
				if(devolucao == 0) $('#devolucao').val(0);
				resultado = String((saldoTotal - devolucao).toFixed(2));
				resultado = resultado.replace( '.', ',' );
				$('#vale').val(resultado);
			}
		});
		
		$("form").submit(function(e){	
			devolucao = parseFloat($('#devolucao').val().replace( '.', '' ).replace( ',', '.' ));
			vale = parseFloat($('#vale').val().replace( '.', '' ).replace( ',', '.' ));
			
			valorInformado = devolucao + vale;
			
			console.log("valor informado: " + valorInformado);
			console.log("Saldo total: " + saldoTotal)
			
			if(valorInformado < saldoTotal){
				$('#message').text("Ainda há saldo remanescente de R$ " + (saldoTotal - valorInformado));
            	e.preventDefault();
			}else if (valorInformado > saldoTotal){
				$('#message').text("O valor informado excede o saldo.");
            	e.preventDefault();
			}
			else{
				$('#message').text("");
				return confirm('Deseja encerrar?');
			}
        });
		
			$('#devolucao, #vale').maskMoney({
				decimal : ',',
				thousands : '.',
				precision : 2
			});

});	
</script>
</jsp:attribute>

	<jsp:body>
	<div class="container container-login">
		<h5 id="message" class="color-red">${ message }</h5>

		<div class="jumbotron">
			<h2 style="font-size: 2.5rem" class="display-3">Encerrar conta?</h2>
			<p class="lead">O saldo da conta é:
			<fmt:formatNumber value="${ saldo }" minFractionDigits="2"
				type="currency" />, <br/>
				informe como gostaria de encerrar a conta: </p>
			<hr class="my-4">
			<form action="${s:mvcUrl('CDC#encerrar').build() }" method="post">
				<div class="form-group">
						<div class="d-flex mb-2">
								<label class="m-0 d-flex align-self-center" style="min-width: 6em" for="valorVale">Vale: </label>
								<input style="flex-grow: 0" id="vale" type="text"
								class="form-control formatNumber w-100 w-sm-50" name="valorVale" required />
						</div>
					
						<div class="d-flex">
								<label class="m-0 d-flex align-self-center" style="min-width: 6em" for="valorDevolucao">Devolução: </label>
								<input style="flex-grow: 0" id="devolucao" type="text"
								class="form-control formatNumber w-100 w-sm-50" name="valorDevolucao"
								required />
						</div>
				</div>
				<input type="hidden" name="id" value="${ conta.id }" />
				<button type="submit" class="btn btn-danger">Encerrar</button>
			</form>
		</div>
	</div>
	</jsp:body>

</tags:pageTemplate>