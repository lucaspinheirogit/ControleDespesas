<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<c:url value="/" var="contextPath" />

<tags:pageTemplate titulo="Lista de usuarios">

	<div class="container">
		<h2>Lista de Usuários</h2>


		<c:forEach items="${ usuarios }" var="u">
			<h5>${ u.id } - ${ u.nome } - ${ u.login } - ${ u.roles } - ${ u.situacao }</h5>
		</c:forEach>

	</div>

</tags:pageTemplate>