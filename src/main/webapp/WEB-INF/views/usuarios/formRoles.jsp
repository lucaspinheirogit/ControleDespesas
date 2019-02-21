<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tags:pageTemplate
	titulo="Livros de Java, Android, iPhone, Ruby, PHP e muito mais ....">

	<section style="padding: 1em" id="index-section"
		class="container middle">

		<h1>Cadastro de permissões para ${usuario.nome }</h1>

		<h2 class="color-red">${message }</h2>

		<form:form action="${s:mvcUrl('UC#gravarRoles').build() }" method="post" commandName="usuario">
			
				Permissões:
			
				<form:checkbox path="roles" value="ROLE_ADMIN" />
				<c:out value="ROLE_ADMIN" />
				
				<form:checkbox path="roles" value="ROLE_USER" />
				<c:out value="ROLE_USER" />
				
				<form:checkbox path="roles" value="ROLE_COMERCIAL" />
				<c:out value="ROLE_COMERCIAL" />
				
				<form:hidden path="email"/>

			<br/>
			<button type="submit" class="btn btn-primary">Atualizar</button>
		</form:form>

	</section>

</tags:pageTemplate>
