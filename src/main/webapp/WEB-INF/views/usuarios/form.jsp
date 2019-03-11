<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<c:url value="/" var="contextPath" />

<tags:pageTemplate titulo="Cadastro de colaborador">

	<div class="container container-login">
		<h2 class="titulo-login">Cadastro de colaborador</h2>
		<h5 style="color: red">${ message }</h5>

		<form:form action="${s:mvcUrl('UC#gravar').build() }" method="post"
			commandName="usuario" enctype="multipart/form-data">
			<div class="form-group">
				<label>Nome: </label>
				<form:input path="nome" cssClass="form-control" required="required" />
				<form:errors path="nome" />
			</div>
			<div class="form-group">
				<label>Login: </label>
				<form:input path="login" cssClass="form-control" required="required" />
				<form:errors path="login" />
			</div>
			<div class="form-group">
				<label>Senha: </label>
				<form:password path="senha" cssClass="form-control"
					required="required" />
				<form:errors path="senha" />
			</div>
			<form:hidden path="roles" value="ROLE_USER" />
			<button type="submit" class="btn btn-primary">Cadastrar</button>
		</form:form>

		<br />
		<br />
		<h3>Lista de colaboradores</h3>

		<table>
			<thead>
				<tr class="table-primary">
					<th>Nome</th>
					<th>Login</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ usuarios }" var="u">
					<tr>
						<td>${ u.nome }</td>
						<td>${ u.login }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>



	</div>

</tags:pageTemplate>