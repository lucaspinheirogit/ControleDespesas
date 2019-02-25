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
		<h3 class="titulo-login mb-5">Cadastro de conta de despesa</h3>
		<h5 class="color-red">${ message }</h5>

		<form:form cssClass="p-2" action="${s:mvcUrl('CDC#gravar').build() }" method="post"
			commandName="contaDespesa">
			<div class="form-group">
				<label>Cliente: </label>
				<form:select cssClass="form-control m-0" path="cliente.id">
					<c:forEach items="${clientes }" var="cliente">
						<form:option value="${ cliente.id }" label="${ cliente.nome }" />
					</c:forEach>
				</form:select>
			</div>
			<div class="form-group">
				<label>Colaborador: </label>
				<form:select cssClass="form-control m-0" path="usuario.id">
					<c:forEach items="${usuarios }" var="usuario">
						<form:option value="${ usuario.id }" label="${ usuario.nome }" />
					</c:forEach>
				</form:select>
			</div>
			<div class="form-group">
				<label>Data de início: </label>
				<form:input cssClass="form-control" path="dataInicio" placeholder="dd/MM/yyyy" />
			</div>
			<div class="form-group">
				<label>Data de fim: </label>
				<form:input cssClass="form-control" path="dataFim" placeholder="dd/MM/yyyy" />
			</div>
			<button type="submit" class="btn btn-primary">Cadastrar</button>
		</form:form>
	</div>

	<!-- 	dataInicio; -->
	<!-- 	dataFim; -->

</tags:pageTemplate>