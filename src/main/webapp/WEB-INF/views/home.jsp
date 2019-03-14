<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<c:url value="/" var="contextPath" />

<tags:pageTemplate titulo="Home">

<jsp:attribute name="extraScripts">
<script>
	$(document).ready(function() {
		 $('[name=dataInicio], [name=dataFinal] ').mask("99/99/9999",{placeholder:"dd/MM/yyyy"});
	});
</script>
</jsp:attribute>


	<jsp:body>

	<main class="container">

	<h5 style="color: red; text-align: center" class="color-red">${ message }</h5>
	<h5 style="color: red; text-align: center" class="color-red">${ message2 }</h5>

	<div class="nova-conta">
		<security:authorize access="hasRole('ROLE_ADMIN')">
			<div class="links-criacao">
				<a href="${s:mvcUrl('CDC#form').build() }" class="btn btn-primary">Criar
					conta</a> <a href="${s:mvcUrl('UC#form').build() }"
						class="btn btn-primary">Cadastrar colaborador</a>
			</div>
		</security:authorize>
		<h5 class="filtrar-por">Filtrar por:</h5>
		<form id="formBusca" class="form-inline my-2 my-lg-0"
				action="${s:mvcUrl('CDC#buscar').build() }" method="get">
			<div class="form-group search">

				<div style="width: 100%" class="div-form-group">
					<div class="form-group">
						<label for="exampleSelect1">Cliente:</label> 
							<select id="select-cliente" name="cliente" class="mx-sm-2">
								<option value="" disabled selected hidden>Informe o cliente...</option>
								<c:forEach items="${clientes }" var="cliente">
									<option value="${ cliente.nome }">${ cliente.nome }</option>
								</c:forEach>
							</select>
					</div>

					<div class="form-group">
						<label>Colaborador:</label> 
							<select id="select-usuario" name="usuario" class="mx-sm-2">
								<option value="" disabled selected hidden>Informe o usuario...</option>
								<c:forEach items="${usuarios }" var="usuario">
									<option value="${ usuario.nome }">${ usuario.nome }</option>
								</c:forEach>
							</select>
					</div>

					<div class="form-group">
						<label class="mr-2">Situação: </label> <select
								class="form-control mr-sm-2 ml-0" name="situacao"
								form="formBusca">
							<option value="ATIVA">ATIVA</option>
							<option value="INATIVA">INATIVA</option>
							<option value="ENCERRADA">ENCERRADA</option>
						</select>
					</div>
				</div>

				<div class="div-form-group">
					<div class="form-group">
						<label>Data de início: </label> <input class="form-control"
								placeholder="dd/MM/yyyy" name="dataInicio"
								data-mask="00/00/0000" data-mask-selectonfocus="true" />
					</div>

					<div class="form-group">
						<label>Data de encerramento: </label> <input class="form-control"
								placeholder="dd/MM/yyyy" name="dataFinal" />
					</div>
				</div>

				<button style="margin: .2em" class="btn btn-primary" type="submit">Buscar</button>
			</div>
		</form>
	</div>

	<div class="contas">

		<c:forEach items="${ contas }" var="conta">
			<div class="card border-primary mb-3">
				<div class="card-header">
					<small class="data-inicio"> <fmt:formatDate
								value="${ conta.dataInicio.time }" pattern="dd/MM/yyyy" />
					</small> <small class="data-fim"><fmt:formatDate
								value="${ conta.dataFim.time }" pattern="dd/MM/yyyy" /></small>
				</div>
				<div class="card-body pb-0 pb-sm-1">
					<div class="card-body-header">
						<h4 class="card-title">${ conta.usuario.nome }</h4>
						<small>${ conta.situacao }</small>
					</div>
					<h5 class="card-title cliente">${ conta.cliente.nome }</h5>
					<div class="movimentacoes">
					<div style="display:flex; justify-content: space-between;">
						<h5 class="card-title">Movimentações:</h5>
						<small>
						<fmt:formatNumber value="${ saldos.get( conta.id ) }"
								minFractionDigits="2" type="currency" />
						</small>
						</div>
						<div style="display: flex" class="botoes-conta">
							<form action="${s:mvcUrl('MCC#listar').build() }" method="get">
								<input name="id" type="hidden" value="${ conta.id }" />
								<button class="btn btn-primary my-2 my-sm-0 mr-1" type="submit">Ver</button>
							</form>

							<c:if test="${ conta.situacao != 'ENCERRADA' }">
								<form action="${s:mvcUrl('MCC#form').build() }" method="post">
									<input name="id" type="hidden" value="${ conta.id }" />
									<button class="btn btn-primary my-2 my-sm-0 mr-1" type="submit">Adicionar</button>
								</form>
								<form action="${s:mvcUrl('MCC#editar').build() }" method="get">
									<input name="id" type="hidden" value="${ conta.id }" />
									<button class="btn btn-primary my-2 my-sm-0 mr-1" type="submit">Editar</button>
								</form>
							</c:if>


						</div>
					</div>
				</div>
				<security:authorize access="hasRole('ROLE_ADMIN')">
					<c:if test="${ conta.situacao != 'ENCERRADA' }">
						<form class="p-0 p-sm-1" onsubmit="return confirm('Deseja encerrar?');"
								style="text-align: center"
								action="${s:mvcUrl('CDC#encerrarForm').build() }" method="post">
							<input name="id" type="hidden" value="${ conta.id }" />
							<button class="btn btn-danger mb-2" type="submit">Encerrar
								conta</button>
						</form>
					</c:if>
				</security:authorize>
			</div>

		</c:forEach>

	</div>


	</main>
</jsp:body>

</tags:pageTemplate>