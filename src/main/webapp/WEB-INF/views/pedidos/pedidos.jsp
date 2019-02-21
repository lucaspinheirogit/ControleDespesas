<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tags:pageTemplate titulo="Pedidos">

	<section style="padding: 1em" id="index-section" class="container middle">



		<table class="table">
			<thead>
				<tr>
					<th scope="col" >ID</th>
					<th scope="col" >Valor</th>
					<th scope="col" >Data Pedido</th>
					<th scope="col" >Títulos</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${pedidos }" var="pedido">
					<tr>

						<th>${pedido.id }</th>
						<th>${pedido.valor }</th>
						<th><fmt:formatDate value="${pedido.data.time }"
								pattern="dd/MM/yyyy" /></th>
						<th><c:forEach items="${pedido.produtos }" var="produto">
							${produto.titulo },
							</c:forEach></th>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</section>

</tags:pageTemplate>