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
		<h5 class="color-red">${ message }</h5>

		<div class="jumbotron">
			<h1 class="display-3">Encerrar conta?</h1>
			<p class="lead">O saldo da conta é: ${ saldo } ,
				informe como gostaria de encerrar a conta: </p>
			<hr class="my-4">
			<form action="${s:mvcUrl('CDC#encerrar').build() }" method="post">
				<div class="form-group">
					<div class="custom-control custom-radio">
						<input type="radio" id="customRadio1" name="opcao" value="Vale"
							Class="custom-control-input" required /> <label
							class="custom-control-label" for="customRadio1">Vale</label>
					</div>
					<div class="custom-control custom-radio">
						<input type="radio" id="customRadio2" name="opcao"
							value="Devolução" Class="custom-control-input" required /> <label
							class="custom-control-label" for="customRadio2">Devolução</label>
					</div>
				</div>
				<input type="hidden" name="id" value="${ conta.id }" />
				<input type="hidden" name="saldo" value="${ saldo }" />
				<button type="submit" class="btn btn-danger">Encerrar</button>
			</form>
		</div>
	</div>
</tags:pageTemplate>