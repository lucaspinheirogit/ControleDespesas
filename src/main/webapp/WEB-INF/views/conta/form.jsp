<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<c:url value="/" var="contextPath" />

<tags:pageTemplate titulo="Cadastro de contas de despesa">

	<div class="container container-login">
		<h2 class="titulo-login">Cadastro de conta de despesa</h2>
		<h5 class="color-red">${ message }</h5>

		<form:form action="${s:mvcUrl('CDC#gravar').build() }" method="post"
			commandName="contaDespesa">
			<div class="form-group">

				<form:select path="cliente.id">
					<c:forEach items="${clientes }" var="cliente">
						<form:option value="${ cliente.id }" label="${ cliente.nome }" />
					</c:forEach>
				</form:select>

				<%-- 				<form:input path="cliente.id"></form:input> --%>
				<%-- 				<form:input path="cliente.nome"></form:input> --%>

			</div>
			<button type="submit" class="btn btn-primary">Cadastrar</button>
		</form:form>
	</div>

</tags:pageTemplate>