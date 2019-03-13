<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="pt-BR" />

<c:url value="/" var="contextPath" />

<style>
@media only screen and (max-width: 760px) , ( min-device-width : 768px)
	and (max-device-width: 1024px) {
	td:nth-of-type(7):before {
		content: "Remover?" !important;
	}
	td form {
		margin: 0;
	}
	.td-concilia {
		padding: 6px
	}
}
</style>

<security:authorize access="hasRole('ROLE_ADMIN')">
	<style>
@media only screen and (max-width: 760px) , ( min-device-width : 768px)
	and (max-device-width: 1024px) {
	td:nth-of-type(7):before {
		content: "Conciliar?" !important;
	}
}
</style>
</security:authorize>

<tags:pageTemplate titulo="Lista de clientes">

	<div class="container p-2">
		<h2 style="text-align: center;">Editar movimentações</h2>
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
						<td>
							<form class="m-0 p-0" style="text-align: center"
								action="${s:mvcUrl('MCC#alterarResponsavel').build() }"
								method="post">
								<input name="id" type="hidden" value="${ m.id }" /> <select
									onchange="this.form.submit()" class="m-0 form-control"
									name="responsavel">
									<option value="${ m.responsavel }">${ m.responsavel }</option>

									<security:authorize access="hasRole('ROLE_ADMIN')">
										<c:forEach var="i" begin="0" end="2">
											<c:if test="${ m.responsavel != responsaveis[i] }">
												<option value="${ responsaveis[i] }">${ responsaveis[i] }</option>
											</c:if>
										</c:forEach>
									</security:authorize>

								</select> <input name="conta" type="hidden"
									value="${ movimentacoes[0].conta.id }" />
							</form>
						</td>
						<td>${ m.descricao }</td>
						<td><fmt:formatNumber value="${m.valor}"
								minFractionDigits="2" type="currency" /></td>
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
										onsubmit="return confirm('Deseja remover?');"
										action="${s:mvcUrl('MCC#remover').build() }" method="post">
										<input name="id" type="hidden" value="${ m.id }" /> <input
											name="conta" type="hidden"
											value="${ movimentacoes[0].conta.id }" />
										<button class="btn btn-danger" type="submit">Remover</button>
									</form>
								</td>
							</c:when>
							<c:otherwise>
								<td class="td-remover"><security:authorize
										access="hasRole('ROLE_ADMIN')">
										<form class="p-0" style="text-align: center"
											onsubmit="return confirm('Deseja remover?');"
											action="${s:mvcUrl('MCC#remover').build() }" method="post">
											<input name="id" type="hidden" value="${ m.id }" /> <input
												name="conta" type="hidden"
												value="${ movimentacoes[0].conta.id }" />
											<button class="btn btn-danger" type="submit">Remover</button>
										</form>
									</security:authorize> <span style="opacity: 0.01">-</span></td>
							</c:otherwise>
						</c:choose>

					</tr>
				</c:forEach>
			</tbody>
		</table>
		<br />
		<h5 class="mb-5">
			Saldo:
			<fmt:formatNumber value="${ saldo }" minFractionDigits="2"
				type="currency" />
		</h5>
	</div>

</tags:pageTemplate>