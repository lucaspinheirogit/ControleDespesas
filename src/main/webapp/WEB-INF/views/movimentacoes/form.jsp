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

	<jsp:body>
	<div class="container container-login">
		<h2 class="titulo-login">Cadastro de movimentação</h2>
		<h5 class="color-red">${ message }</h5>

		<form:form action="${s:mvcUrl('MCC#gravar').build() }" method="post"
				commandName="movimentacaoConta">
			<div class="form-group">
				<security:authorize access="hasRole('ROLE_ADMIN')">
					<div class="custom-control custom-radio">
						<form:radiobutton id="customRadio1" path="tipo" value="CREDITO"
								cssClass="custom-control-input" checked="checked"
								required="required" />
						<label class="custom-control-label" for="customRadio1">Crédito</label>
					</div>
					</security:authorize>
						<div class="custom-control custom-radio">
							<form:radiobutton id="customRadio2" path="tipo" value="DEBITO"
							cssClass="custom-control-input" checked="checked"
							required="required" />
							<label class="custom-control-label" for="customRadio2">Débito</label>
						</div>
			</div>
			<div class="form-group">
				<label>Descrição: </label>
				<form:input cssClass="form-control" path="descricao"
						required="required" />
			</div>
			<div class="form-group">
				<label>Valor: </label>
				<form:input cssClass="form-control" type="number"
						step="0.01" value="0" path="valor" required="required" />
			</div>
			<form:hidden path="conta.id" />
			<button type="submit" class="btn btn-primary">Cadastrar</button>
		</form:form>
	</div>

	<!-- 	dataInicio; -->
	<!-- 	dataFim; -->
</jsp:body>
</tags:pageTemplate>