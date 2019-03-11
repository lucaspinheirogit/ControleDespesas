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

	<div class="container container-login">
	<form>
      <input type="text" id="money2" /><br>
   </form>
		<h2 class="titulo-login">Alterar senha</h2>
		<h5 class="color-red">${ message }</h5>

		<form:form action="${s:mvcUrl('UCC#alterarSenha').build() }" method="post"
				commandName="usuario">
			<div class="form-group">
				<label>Senha: </label>
				<form:input cssClass="form-control" type="text"
					 path="senha" required="required" />
			</div>
			<div class="form-group">
				<label>Repita a senha: </label>
				<form:input cssClass="form-control" type="text"
					 path="senhaRepetida" required="required" />
			</div>
			<form:hidden path="usuario.id" />
			<button type="submit" class="btn btn-primary">Cadastrar</button>
		</form:form>
	</div>

</tags:pageTemplate>