<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<c:url value="/" var="contextPath" />

<tags:pageTemplate titulo="Cadastro de movimentacao">

	<jsp:attribute name="extraScripts">
<script>
	$('input.formatNumber').on(
			'blur',
			function() {
				if (this.value == '')
					this.value = 0;

				this.value = parseFloat(this.value.replace(/,/g, ""))
						.toFixed(2).toString().replace(/\B(?=(\d{3})+(?!\d))/g,
								",");
			});

	$('#formMovimentacao').on('submit', function(e) {
		let value = $('input.formatNumber').val();

		if (value.match(/[a-z]/i)) {
			$('input.formatNumber').val("");
			$('#message').text("Informe um valor válido");
			e.preventDefault();
		} else {
			$('#message').text("");
			value = value.replace(".", "a");
			value = value.replace(/,/g, ".");
			value = value.replace("a", ",");
			$('input.formatNumber').val(value);
		}
	});
</script>
</jsp:attribute>

	<jsp:body>
	<div class="container container-login">
		<h2 class="titulo-login">Cadastro de movimentação</h2>
		<h5 id="message" class="color-red">${ message }</h5>

		<form:form id="formMovimentacao"
				action="${s:mvcUrl('MCC#gravar').build() }" method="post"
				commandName="movimentacaoConta">
			<div class="form-group">
			<div class="custom-control d-none custom-radio">
						<form:radiobutton id="customRadio1" path="tipo" value="DEBITO"
							cssClass="custom-control-input" checked="checked"
							required="required" />
						<label class="custom-control-label" for="customRadio1">Débito</label>
					</div>
			<security:authorize access="hasRole('ROLE_ADMIN')">
						<div class="custom-control custom-radio">
							<form:radiobutton id="customRadio2" path="tipo" value="DEBITO"
								cssClass="custom-control-input" checked="checked"
								required="required" />
							<label class="custom-control-label" for="customRadio2">Débito</label>
						</div>
					<div class="custom-control custom-radio">
						<form:radiobutton id="customRadio3" path="tipo" value="CREDITO"
								cssClass="custom-control-input" checked="checked"
								required="required" />
						<label class="custom-control-label" for="customRadio3">Crédito</label>
					</div>
					</security:authorize>
			</div>
			<div class="form-group">
				<label>Descrição: </label>
				<form:input cssClass="form-control" path="descricao"
						required="required" />
			</div>
			<div class="form-group">
				<label>Valor: R$</label>
				<input type="text" class="form-control formatNumber" name="valor"
						required />
			</div>
			<form:hidden path="conta.id" />
			<button type="submit" class="btn btn-primary">Cadastrar</button>
		</form:form>
	</div>

	<!-- 	dataInicio; -->
	<!-- 	dataFim; -->
</jsp:body>
</tags:pageTemplate>