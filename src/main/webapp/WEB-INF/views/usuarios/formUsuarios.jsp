<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tags:pageTemplate
	titulo="Cadastro de usuarios">

	<section style="padding: 1em" id="index-section"
		class="container middle">

		<h1>Cadastro de Usuários</h1>
		
		<h2 class="color-red">${message }</h2>
		
		<form:form action="${s:mvcUrl('UC#gravar').build() }" method="post"
			commandName="usuario">
			<div class="form-group">
				<label>Nome:</label>
				<form:input path="nome" cssClass="form-control" />
				<form:errors path="nome" cssClass="color-red" />
			</div>
			<div class="form-group">
				<label>Email:</label>
				<form:input path="email" cssClass="form-control" />
				<form:errors path="email" cssClass="color-red" />
			</div>
			<div class="form-group">
				<label>Senha:</label>
				<form:password path="senha" cssClass="form-control" />
				<form:errors path="senha" cssClass="color-red" />
			</div>
			<div class="form-group">
				<label>Senha repetida:</label>
				<form:password path="senhaRepetida" cssClass="form-control" />
				<form:errors path="senhaRepetida" cssClass="color-red" />
			</div>
			<button type="submit" class="btn btn-primary">Cadastrar</button>
		</form:form>

	</section>

</tags:pageTemplate>
