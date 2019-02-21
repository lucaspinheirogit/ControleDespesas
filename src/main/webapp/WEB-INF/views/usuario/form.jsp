<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Login</title>
<c:url value="/resources/css" var="cssPath" />
<link rel="stylesheet" href="${cssPath}/bootstrap.min.css" />
<link rel="stylesheet" href="${cssPath}/main.css" />
<link rel="stylesheet" href="${cssPath}/login.css" />
</head>
<body>

	<div class="container">
		<h2>Cadastro de usuários</h2>

		<form:form action="${s:mvcUrl('UC#gravar').build() }" method="post"
			commandName="usuario" enctype="multipart/form-data">
			<div class="form-group">
				<label>Nome</label>
				<form:input path="nome" cssClass="form-control" />
				<form:errors path="nome" />
			</div>
			<div class="form-group">
				<label>Login</label>
				<form:input path="login" cssClass="form-control" />
				<form:errors path="login" />
			</div>
			<div class="form-group">
				<label>Senha</label>
				<form:input path="senha" cssClass="form-control" />
				<form:errors path="senha" />
			</div>
			<button type="submit" class="btn btn-primary">Cadastrar</button>
		</form:form>
	</div>

</body>
</html>