<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url value="/" var="contextPath" />

<nav class="p-0 navbar navbar-expand-sm navbar-dark bg-primary">
	<a href="${s:mvcUrl('HC#index').build() }"> <img class="m-1"
		style="filter: brightness(0) invert(1); height: 70px"
		src="${ contextPath }resources/imagens/logo_safe.png" alt="Safetech">
	</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarColor01" aria-controls="navbarColor01"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse p-2 p-sm-0" id="navbarColor01">
		<security:authorize access="isAuthenticated()">
			<security:authentication property="principal" var="usuario" />
			<h6>Olá, ${usuario.username }</h6>
			<a style="background-color: #3c546d"
				href="${s:mvcUrl('UC#alterarSenhaForm').build() }"
				class="btn btn-primary">Alterar senha</a>
			<a style="color: white" class="nav-link"
				href="<c:url value="/logout" />">Sair</a>
		</security:authorize>
		<security:authorize access="!isAuthenticated()">
			<a style="color: white" class="nav-link"
				href="${s:mvcUrl('LC#form').build() }">Login</a>
		</security:authorize>
	</div>
</nav>
