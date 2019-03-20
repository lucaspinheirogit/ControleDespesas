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

<tags:pageTemplate titulo="Lista de clientes">

	<div class="container p-2">
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
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ movimentacoes }" var="m">
					<tr>
						<td><c:choose>
								<c:when test="${ m.responsavel == null }">
									--------
							</c:when>
								<c:otherwise>
								${ m.responsavel }
							</c:otherwise>
							</c:choose></td>
						<td>${ m.descricao }</td>
						<td><fmt:formatNumber value="${m.valor}"
								minFractionDigits="2" type="currency" /></td>
						<td>${ m.tipo }</td>
						<td>${ m.conciliada }</td>
						<td>${ m.criadoPor.nome }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<br />
		<h5 class="mb-0">
			Saldo:
			<fmt:formatNumber value="${ saldo }" minFractionDigits="2"
				type="currency" />
		</h5>
		<br />

		<security:authorize access="hasRole('ROLE_ADMIN')">
			<form class="p-0" style="text-align: center"
				action="${s:mvcUrl('CDC#gerarRelatorio').build() }" method="post">
				<input name="conta" type="hidden"
					value="${ movimentacoes[0].conta.id }" />
				<button class="btn btn-primary mb-5" type="submit">Gerar
					PDF</button>
			</form>
		</security:authorize>

	</div>

</tags:pageTemplate>