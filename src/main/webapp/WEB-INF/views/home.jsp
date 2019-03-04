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

	<main class="container">

	<h5 style="color: red; text-align: center" class="color-red">${ message }</h5>

	<div class="nova-conta">
		<security:authorize access="hasRole('ROLE_ADMIN')">
			<div class="links-criacao">
				<a href="${s:mvcUrl('CDC#form').build() }" class="btn btn-primary">Criar
					conta</a> <a href="${s:mvcUrl('UC#form').build() }"
					class="btn btn-primary">Cadastrar colaborador</a>
			</div>
		</security:authorize>
		<h5 class="filtrar-por">Filtrar por:</h5>
		<form class="form-inline my-2 my-lg-0">
			<div class="form-group search">

				<div class="div-form-group" >
					<div class="form-group">
						<label for="exampleSelect1">Cliente:</label> <input
							class="form-control mr-sm-2" list="clientes"
							placeholder="Informe o cliente...">
						<datalist id="clientes">
							<c:forEach items="${clientes }" var="cliente">
								<option value="${ cliente.nome }" />
							</c:forEach>
						</datalist>
					</div>

					<div class="form-group">
						<label>Colaborador: </label> <input class="form-control mr-sm-2"
							list="usuarios" placeholder="Informe o colaborador...">
						<datalist id="usuarios">
							<c:forEach items="${usuarios }" var="usuario">
								<option value="${ usuario.nome }" />
							</c:forEach>
						</datalist>
					</div>
				</div>

				<div class="div-form-group" >
					<div class="form-group">
						<label>Data de início: </label> <input class="form-control"
							placeholder="dd/MM/yyyy" />
					</div>

					<div class="form-group">
						<label>Data de encerramento: </label> <input class="form-control"
							placeholder="dd/MM/yyyy" />
					</div>
				</div>

				<button style="margin: .2em" class="btn btn-primary" type="submit">Procurar</button>
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
				<div class="card-body">
					<div class="card-body-header">
						<h4 class="card-title">${ conta.usuario.nome }</h4>
						<small>${ conta.situacao }</small>
					</div>
					<h5 class="card-title cliente">${ conta.cliente.nome }</h5>
					<div class="movimentacoes">
						<h5 class="card-title">Movimentações:</h5>
						<div style="display: flex" class="botoes-conta">
							<form action="${s:mvcUrl('MCC#listar').build() }" method="post">
								<input name="id" type="hidden" value="${ conta.id }" />
								<button class="btn btn-primary my-2 my-sm-0 mr-1" type="submit">Ver</button>
							</form>

							<c:if test="${ conta.situacao != 'ENCERRADA' }">
								<form action="${s:mvcUrl('MCC#form').build() }" method="post">
									<input name="id" type="hidden" value="${ conta.id }" />
									<button class="btn btn-primary my-2 my-sm-0 mr-1" type="submit">Adicionar</button>
								</form>
								<form action="${s:mvcUrl('MCC#editar').build() }" method="post">
									<input name="id" type="hidden" value="${ conta.id }" />
									<button class="btn btn-primary my-2 my-sm-0 mr-1" type="submit">Editar</button>
								</form>
							</c:if>


						</div>
					</div>
				</div>
				<security:authorize access="hasRole('ROLE_ADMIN')">
					<c:if test="${ conta.situacao != 'ENCERRADA' }">
						<form onsubmit="return confirm('Deseja encerrar?');"
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

</tags:pageTemplate>