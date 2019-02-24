<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<c:url value="/" var="contextPath" />

<tags:pageTemplate titulo="Lista de clientes">

	<div class="container">
		<h2>Lista de movimentações</h2>


		<%-- 		<c:forEach items="${ clientes }" var="cliente"> --%>
		<%-- 			<h5>${ cliente.id } - ${ cliente.nome }</h5> --%>
		<%-- 		</c:forEach> --%>

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
				<tr>
					<td>Empresa</td>
					<td>hospedagem</td>
					<td>750.00</td>
					<td>Credito</td>
					<td>Sim</td>
					<td>Safetech</td>
				</tr>
				<tr>
					<td>Daniel Rolloff</td>
					<td>Tênis</td>
					<td>150.00</td>
					<td>Debito</td>
					<td>Nao</td>
					<td>Daniel rolloff</td>
				</tr>
				<tr>
					<td>Empresa</td>
					<td>hospedagem</td>
					<td>750.00</td>
					<td>Credito</td>
					<td>Sim</td>
					<td>Safetech</td>
				</tr>
			</tbody>
		</table>

	</div>

</tags:pageTemplate>