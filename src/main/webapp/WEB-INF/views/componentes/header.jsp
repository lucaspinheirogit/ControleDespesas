<nav class="navbar navbar-expand-sm navbar-dark bg-primary">
	<a class="navbar-brand" href="${s:mvcUrl('HC#index').build() }">Safetech</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarColor01" aria-controls="navbarColor01"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse" id="navbarColor01">
		<security:authorize access="isAuthenticated()">
			<security:authentication property="principal" var="usuario" />
			<h6>Olá, ${usuario.username }</h6>
		</security:authorize>
		<security:authorize access="isAuthenticated()">
			<a style="color: white" class="nav-link"
				href="<c:url value="/logout" />">Sair</a>
		</security:authorize>
		<security:authorize access="!isAuthenticated()">
			<a style="color: white" class="nav-link" href="${s:mvcUrl('LC#form').build() }">Login</a>
		</security:authorize>
	</div>
</nav>
