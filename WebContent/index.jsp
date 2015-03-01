<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Amazonas, Tus compras Online</title>

<!-- CSS de Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">

<!-- librerías opcionales que activan el soporte de HTML5 para IE8 -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<%@ page import="com.rapplogic.xbee.connectionLayer.recurrente.Mantenimiento"%>
<%@ page  import="org.apache.log4j.PropertyConfigurator" %>

</head>
<body>

	<%
	Mantenimiento mantenimiento = new Mantenimiento();
	PropertyConfigurator.configure("log4j.properties");
	%>


	<nav class="navbar navbar-inverse" role="navigation">
		<!-- El logotipo y el icono que despliega el menú se agrupan
       para mostrarlos mejor en los dispositivos móviles -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-ex1-collapse">
				<span class="sr-only">Desplegar navegación</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="index.jsp">AMAZONAS</a>
		</div>

		<!-- Agrupar los enlaces de navegación, los formularios y cualquier
       otro elemento que se pueda ocultar al minimizar la barra -->
		<div class="collapse navbar-collapse navbar-ex1-collapse">
			<ul class="nav navbar-nav">
				<li><a href="index.jsp?pagina=1&categoria=ropa">Ropa</a></li>
				<li><a href="index.jsp?pagina=1&categoria=electronica">electronica</a></li>
				<li><a href="index.jsp?pagina=1&categoria=ordenadores">ordenadores</a></li>
				<li><a href="index.jsp?pagina=1&categoria=hogar">hogar</a></li>


			</ul>

			<form class="navbar-form navbar-left" role="search" METHOD=GET
				action="busqueda.jsp">
				<div class="form-group">
					<input type="categoria" class="form-control" name="categoria"
						placeholder="Categoria" required>
				</div>
				<div class="form-group">
					<input type="vendedor" class="form-control" name="vendedor"
						placeholder="Vendedor">
				</div>
				<button type="submit" class="btn btn-default">Buscar</button>
			</form>

			<ul class="nav navbar-nav navbar-right">



			


				<li class="active"><a href="#">Invitado</a></li>
				
				<li><a href="pedidos.jsp">Mis pedidos</a></li>

				<li><a href="carritoCompleto.jsp">Carrito</a></li>
				<li class="active"><a> 
				</a></li>
				<li><form class="navbar-form navbar-left" role="deslogear"
						METHOD=POST action="logica">
						<input type="hidden" name="accion" value="desLoger">
						<button type="submit" class="btn btn-default">Deslogear</button>
					</form></li>



				

			</ul>
		</div>
	</nav>





	<footer>
		<hr>
		<p>&copy; Company 2014</p>
	</footer>

	<!-- Librería jQuery requerida por los plugins de JavaScript -->
	<script src="http://code.jquery.com/jquery.js"></script>

	<!-- Todos los plugins JavaScript de Bootstrap (también puedes
         incluir archivos JavaScript individuales de los únicos
         plugins que utilices) -->
	<script src="js/bootstrap.min.js"></script>
</body>
</html>

