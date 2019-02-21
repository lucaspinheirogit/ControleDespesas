<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tags:pageTemplate titulo="Usuarios">

	<section style="padding: 1em" id="index-section"
		class="container middle">

		<h2>${message }</h2>

		<a href="${s:mvcUrl('UC#form').build() }" rel="nofollow">Novo
			usuário</a>
			
		<br/>
		
		<h2>Lista de usuários</h2>

		<table class="table">
			<thead>
				<tr>
					<th scope="col">Nome</th>
					<th scope="col">Email</th>
					<th scope="col">Roles</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${usuarios}" var="usuario">
					<tr>
						<th>${usuario.nome}</th>
						<th>${usuario.email}</th>
						<th>${usuario.roles }</th>
						<th style="text-align: center">
						<form action="${s:mvcUrl('UC#alterarRoles').build() }" method="POST" >
								<input type="hidden" name="email" value="${usuario.email }">
<%-- 									<form:hidden path="email"/> --%>
								<button type="submit" ><img src="http://www.108talkradio.com/wp-content/uploads/2018/04/1232/lets-take-more-than-seven-seconds-with-corey-champagne.png" ></button>
							</form>
						</th>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</section>

</tags:pageTemplate>