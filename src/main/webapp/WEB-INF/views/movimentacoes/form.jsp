<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<c:url value="/" var="contextPath" />

<tags:pageTemplate titulo="Cadastro de contas de despesa">

	<div class="container container-login">
		<h2 class="titulo-login">Cadastro de conta de despesa</h2>
		<h5 class="color-red">${ message }</h5>

		<form:form action="${s:mvcUrl('MCC#gravar').build() }" method="post"
			commandName="movimentacaoConta">
			<div class="form-group">
				<label>Tipo: </label>
				<form:checkbox path="tipo" value="CREDITO"/>Crédito
				<form:checkbox path="tipo" value="DEBITO"/>Débito
			</div>
			<button type="submit" class="btn btn-primary">Cadastrar</button>
		</form:form>
	</div>

	<!-- 	dataInicio; -->
	<!-- 	dataFim; -->

</tags:pageTemplate>