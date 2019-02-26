<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<c:url value="/" var="contextPath" />

<tags:pageTemplate titulo="Cadastro de movimentacao">

	<div class="container container-login">
		<h2 class="titulo-login">Cadastro de movimentação</h2>
		<h5 class="color-red">${ message }</h5>

		<form:form action="${s:mvcUrl('MCC#gravar').build() }" method="post"
			commandName="movimentacaoConta">
			<div class="form-group">
				<div class="custom-control custom-radio">
					<form:radiobutton id="customRadio1" path="tipo" value="CREDITO"
						cssClass="custom-control-input" checked="" required="required" />
					<label class="custom-control-label" for="customRadio1">Crédito</label>
				</div>
				<div class="custom-control custom-radio">
					<form:radiobutton id="customRadio2" path="tipo" value="DEBITO"
						cssClass="custom-control-input" required="required" />
					<label class="custom-control-label" for="customRadio2">Débito</label>
				</div>
			</div>
			<div class="form-group">
				<label>Descrição: </label>
				<form:input cssClass="form-control" path="descricao" required="required" />
			</div>
			<div class="form-group">
				<label>Valor: </label>
				<form:input cssClass="form-control" type="number" path="valor" required="required" />
			</div>
			<button type="submit" class="btn btn-primary">Cadastrar</button>
		</form:form>
	</div>

	<!-- 	dataInicio; -->
	<!-- 	dataFim; -->

</tags:pageTemplate>