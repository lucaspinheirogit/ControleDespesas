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
			vale = this.value.replace( '.', '' ).replace( ',', '.' );
			vale = parseFloat(vale);
			resultado = String(saldoTotal - vale);
			
			resultado = resultado.replace( '.', ',' );
			$('#devolucao').val(resultado);
		});
		
		$('#devolucao').on('blur', function() {
			devolucao = this.value.replace( '.', '' ).replace( ',', '.' );
			devolucao = parseFloat(devolucao);
			resultado = String(saldoTotal - devolucao);
			
			resultado = resultado.replace( '.', ',' );
			$('#vale').val(resultado);
		});
		
		$("form").submit(function(e){
			devolucao = $('#devolucao').replace( '.', '' ).replace( ',', '.' );
			vale = $('#vale').replace( '.', '' ).replace( ',', '.' );
			
			valorInformado = devolucao + vale;
			
			console.log("valor informado: " + valorInformado);
			console.log("Saldo total: " + saldoTotal)
			
			if(valorInformado < saldoTotal){
				$('#message').text("Ainda há saldo remanescente de R$ " + (saldoTotal - valorInformado));
            	e.preventDefault();
			}else{
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
								<input style="flex-grow: 0" id="vale" type="text"
								class="form-control formatNumber w-50" name="valorVale" required
								disabled />
					</div>
					<div class="custom-control custom-radio">
						<div>
							<input type="checkbox" id="checkbox2" name="devolucao"
									value="Devolução" Class="custom-control-input" required /> <label
									class="custom-control-label" for="checkbox2">Devolução</label>
						</div>
								<input style="flex-grow: 0" id="devolucao" type="text"
								class="form-control formatNumber w-50" name="valorDevolucao"
								required disabled />
					</div>
				</div>
				<input type="hidden" name="id" value="${ conta.id }" />
				<button type="submit" class="btn btn-danger">Encerrar</button>
			</form>
		</div>
	</div>
	</jsp:body>

</tags:pageTemplate>