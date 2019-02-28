<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<c:url value="/" var="contextPath" />

<tags:pageTemplate titulo="Lista de clientes">

	<div class="container">
		<h2 style="text-align: center;">Lista de movimentações</h2>
		<h5>Colaborador: ${ movimentacoes[0].conta.usuario.nome }</h5>
		<h5>Cliente: ${ movimentacoes[0].conta.cliente.nome }</h5>
		<h5 style="color: red">${ message }</h5>

		<table>
			<thead>
				<tr class="table-primary">
					<th>Responsável</th>
					<th>Descrição</th>
					<th>Valor</th>
					<th>Tipo</th>
					<th>Conciliada</th>
					<th>Criada por</th>
					<security:authorize access="hasRole('ROLE_ADMIN')">
						<th>Conciliar?</th>
					</security:authorize>
					<th>Remover?</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ movimentacoes }" var="m">
					<tr>
						<td>Empresa</td>
						<td>${ m.descricao }</td>
						<td>${ m.valor }</td>
						<td>${ m.tipo }</td>
						<td>${ m.conciliada }</td>
						<td>${ m.criadoPor.nome }</td>
						<security:authorize access="hasRole('ROLE_ADMIN')">
							<td class="td-concilia">
								<form class="p-0" style="text-align: center"
									action="${s:mvcUrl('MCC#conciliar').build() }" method="post">
									<input name="id" type="hidden" value="${ m.id }" /> <input
										name="tipo" type="hidden" value="SIM" /> <input name="conta"
										type="hidden" value="${ movimentacoes[0].conta.id }" />
									<button type="submit">
										<img src="${ contextPath }resources/imagens/checked.svg"
											alt="checked">
									</button>
								</form>
								<form class="p-0" style="text-align: center"
									action="${s:mvcUrl('MCC#conciliar').build() }" method="post">
									<input name="id" type="hidden" value="${ m.id }" /> <input
										name="tipo" type="hidden" value="NAO" /> <input name="conta"
										type="hidden" value="${ movimentacoes[0].conta.id }" />
									<button type="submit">
										<img src="${ contextPath }resources/imagens/cancel.svg"
											alt="checked">
									</button>
								</form>
							</td>
						</security:authorize>

						<c:choose>
							<c:when
								test="${ movimentacoes[0].conta.usuario.nome == m.criadoPor.nome && m.conciliada != 'SIM' }">
								<td class="td-remover">
									<form class="p-0" style="text-align: center"
										action="${s:mvcUrl('MCC#remover').build() }" method="post">
										<input name="id" type="hidden" value="${ m.id }" /> <input
											name="conta" type="hidden"
											value="${ movimentacoes[0].conta.id }" />
										<button class="btn btn-danger" type="submit">Remover</button>
									</form>
								</td>
							</c:when>
							<c:otherwise>
								<td></td>
							</c:otherwise>
						</c:choose>



					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

</tags:pageTemplate>