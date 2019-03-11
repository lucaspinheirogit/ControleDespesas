<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>

<%@ attribute name="titulo" required="true"%>
<%@ attribute name="extraScripts" fragment="true"%>

<c:url value="/" var="contextPath" />

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<title>${titulo } - Controle de despesas</title>
<link rel="stylesheet" type="text/css" media="screen"
	href="https://bootswatch.com/4/flatly/bootstrap.min.css">
<link rel="stylesheet" type="text/css" media="screen"
	href="https://cdnjs.cloudflare.com/ajax/libs/selectize.js/0.12.6/css/selectize.css">
<link rel="stylesheet" href="${contextPath}resources/css/main.css" />
<link rel="stylesheet" href="${contextPath}resources/css/footer.css" />
<link rel="stylesheet" href="${contextPath}resources/css/header.css" />
<link rel="stylesheet" href="${contextPath}resources/css/home.css" />
<link rel="stylesheet" href="${contextPath}resources/css/login.css" />
<link rel="stylesheet" href="${contextPath}resources/css/responsive-table.css" />

<style>
	.selectize-control{
		min-width: 11em;
	}
</style>
</head>

<body>
	<div class="container-fluid">
		<%@ include file="/WEB-INF/views/componentes/header.jsp"%>
		<jsp:doBody />
	</div>
	<footer>
		<h6 style="text-align: center">Desenvolvido por Lucas Pinheiro &copy</h6>
	</footer>

	<script src="https://code.jquery.com/jquery-3.3.1.min.js"
		></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
		integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
		integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
		crossorigin="anonymous"></script>
		
		<script src="https://cdnjs.cloudflare.com/ajax/libs/selectize.js/0.12.6/js/standalone/selectize.js"></script>
		
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.10/jquery.mask.js"></script>
		
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.inputmask/3.1.60/inputmask/jquery.inputmask.js"></script>
		<script src="${contextPath}resources/js/inputmask/inputmask.js"></script>
		<script src="${contextPath}resources/js/inputmask/inputmask.extensions.js"></script>
		<script src="${contextPath}resources/js/inputmask/inputmask.numeric.extensions.js"></script>
		<script src="${contextPath}resources/js/inputmask/inputmask.date.extensions.js"></script>
		
		<script src="https://cdn.rawgit.com/plentz/jquery-maskmoney/master/dist/jquery.maskMoney.min.js"></script>
		
		
		<script>
			$('#select-cliente, #select-criar-cliente').selectize({
				create : false,
				sortField : 'text',
				placeholder: "Informe o cliente..."
			});
			
			$('#select-usuario, #usuarios').selectize({
				create : false,
				sortField : 'text',
				placeholder: "Informe o colaborador..."
			});
		</script>
		
		<jsp:invoke fragment="extraScripts" />
		

</body>
</html>