<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<c:url value="/" var="contextPath" />

<tags:pageTemplate titulo="Home">

	<main class="container">

	<h5 style="color: red; text-align: center" class="color-red">${ message }</h5>

	<div class="nova-conta">
		<button class="btn btn-primary">Criar nova conta +</button>
		<form class="form-inline my-2 my-lg-0">
			<div class="form-group filtrar">
				<label for="exampleSelect1">Filtrar por:</label> <select
					class="form-control" id="exampleSelect1">
					<option>Colaborador</option>
					<option>Cliente</option>
				</select>
			</div>
			<div class="form-group search">
				<input class="form-control mr-sm-2" type="text"
					placeholder="Informe o nome...">
				<button class="btn btn-primary" type="submit">Procurar</button>
			</div>
		</form>
	</div>

	<div class="contas">

		<div class="card border-primary mb-3">
			<div class="card-header">
				<small class="data-inicio">02-04-2018</small> <small
					class="data-fim">15-08-2019</small>
			</div>
			<div class="card-body">
				<div class="card-body-header">
					<h4 class="card-title">Daniel Rolloff Rolloff rambo</h4>
					<small>Ativa</small>
				</div>
				<h5 class="card-title cliente">KLIN</h5>
				<div class="movimentacoes">
					<h5 class="card-title">Movimentações:</h5>
					<button class="btn btn-primary my-2 my-sm-0" type="submit">Ver</button>
					<button class="btn btn-primary my-2 my-sm-0" type="submit">Adicionar</button>
					<button class="btn btn-primary my-2 my-sm-0" type="submit">Editar</button>
				</div>
			</div>
		</div>

		<div class="card border-primary mb-3">
			<div class="card-header">
				<small class="data-inicio">02-04-2018</small> <small
					class="data-fim">15-08-2019</small>
			</div>
			<div class="card-body">
				<div class="card-body-header">
					<h4 class="card-title">Daniel Rolloff</h4>
					<small>Ativa</small>
				</div>
				<h5 class="card-title cliente">KLIN</h5>
				<div class="movimentacoes">
					<h5 class="card-title">Movimentações:</h5>
					<button class="btn btn-primary my-2 my-sm-0" type="submit">Ver</button>
					<button class="btn btn-primary my-2 my-sm-0" type="submit">Adicionar</button>
					<button class="btn btn-primary my-2 my-sm-0" type="submit">Editar</button>
				</div>
			</div>
		</div>

		<div class="card border-primary mb-3">
			<div class="card-header">
				<small class="data-inicio">02-04-2018</small> <small
					class="data-fim">15-08-2019</small>
			</div>
			<div class="card-body">
				<div class="card-body-header">
					<h4 class="card-title">Daniel Rolloff</h4>
					<small>Ativa</small>
				</div>
				<h5 class="card-title cliente">KLIN</h5>
				<div class="movimentacoes">
					<h5 class="card-title">Movimentações:</h5>
					<button class="btn btn-primary my-2 my-sm-0" type="submit">Ver</button>
					<button class="btn btn-primary my-2 my-sm-0" type="submit">Adicionar</button>
					<button class="btn btn-primary my-2 my-sm-0" type="submit">Editar</button>
				</div>
			</div>
		</div>

		<div class="card border-primary mb-3">
			<div class="card-header">
				<small class="data-inicio">02-04-2018</small> <small
					class="data-fim">15-08-2019</small>
			</div>
			<div class="card-body">
				<div class="card-body-header">
					<h4 class="card-title">Daniel Rolloff</h4>
					<small>Ativa</small>
				</div>
				<h5 class="card-title cliente">KLIN</h5>
				<div class="movimentacoes">
					<h5 class="card-title">Movimentações:</h5>
					<button class="btn btn-primary my-2 my-sm-0" type="submit">Ver</button>
					<button class="btn btn-primary my-2 my-sm-0" type="submit">Adicionar</button>
					<button class="btn btn-primary my-2 my-sm-0" type="submit">Editar</button>
				</div>
			</div>
		</div>

	</div>


	</main>

</tags:pageTemplate>