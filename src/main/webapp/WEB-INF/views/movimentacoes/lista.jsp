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
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<br />
		<button class="btn btn-primary">Gerar PDF</button>

	</div>

</tags:pageTemplate>