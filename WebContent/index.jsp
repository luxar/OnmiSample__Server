<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>domoSYSTEM</title>

<!-- CSS de Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">

<!-- librerías opcionales que activan el soporte de HTML5 para IE8 -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<%@ page
	import="com.rapplogic.xbee.connectionLayer.recurrente.Mantenimiento"%>
<%@ page import="com.rapplogic.xbee.connectionLayer.Facade"%>
<%@ page import="com.rapplogic.xbee.connectionLayer.DTO.*"%>
<%@ page import="org.apache.log4j.PropertyConfigurator"%>

</head>
<body>

	<%
		Mantenimiento mantenimiento = new Mantenimiento();
		Facade facade = new Facade();
		//PropertyConfigurator.configure("log4j.properties");
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
			<a class="navbar-brand" href="index.jsp">domoSYSTEM</a>
		</div>

		<!-- Agrupar los enlaces de navegación, los formularios y cualquier
       otro elemento que se pueda ocultar al minimizar la barra -->
		<div class="collapse navbar-collapse navbar-ex1-collapse">
			<ul class="nav navbar-nav">



			</ul>



			<ul class="nav navbar-nav navbar-right">







				<li><form class="navbar-form navbar-left" role="admin"
						METHOD=POST action="logica">
						<input type="hidden" name="accion" value="admin">
						<%
							if (facade.isAdminMode()) {
						%>
						<button type="submit" class="btn btn-default">Modo Admin
							ON</button>
						<%
							} else {
						%>
						<button type="submit" class="btn btn-default">Modo Admin
							OFF</button>
						<%
							}
						%>
					</form></li>





			</ul>
		</div>
	</nav>


	<div class="row">

		<div class="col-md-9 ">





			<div class="row">




				<%
					DispositivoLocalDTO[] dispositivoLocalDTO = (DispositivoLocalDTO[]) facade
							.todosDispositivosLocales().toArray(
									new DispositivoLocalDTO[0]);
					for (int i = 0; i < dispositivoLocalDTO.length; i++) {
						PerifericoLocalDTO[] perifericoLocalDTO = (PerifericoLocalDTO[]) facade
								.perifericosPorDirecion(dispositivoLocalDTO[i].getDir())
								.toArray(new PerifericoLocalDTO[0]);
				%>
				<div class="col-sm-4 col-lg-4 col-md-4">

					<%
						boolean activo = dispositivoLocalDTO[i].isActivo();
							if (activo) {
					%>


					<div class="panel panel-info">
						<%
							} else {
						%>
						<div class="panel panel-danger">
							<%
								}
							%>
							
							<div class="panel-heading">
							<%if(facade.isAdminMode()){ %>
							<form role="form" METHOD=POST action="logica">
									<%
										int dir[] = dispositivoLocalDTO[i].getDir();
									%>
									<input type="hidden" name="accion" value="nombre"> <input
										type="hidden" name="dir1" value="<%=dir[0]%>"> <input
										type="hidden" name="dir2" value="<%=dir[1]%>"> <input
										type="hidden" name="dir3" value="<%=dir[2]%>"> <input
										type="hidden" name="dir4" value="<%=dir[3]%>"> <input
										type="hidden" name="dir5" value="<%=dir[4]%>"> <input
										type="hidden" name="dir6" value="<%=dir[5]%>"> <input
										type="hidden" name="dir7" value="<%=dir[6]%>"> <input
										type="hidden" name="dir8" value="<%=dir[7]%>">
										<input id="nuevoNombre"
											name="nuevoNombre"
											class="form-control"
											value="<%=dispositivoLocalDTO[i].getNombre()%>" >
											<button type="submit" class="btn btn-default" >Cambiar</button>
										
							
							<%}else{ %>
								<%=dispositivoLocalDTO[i].getNombre()%>
								<%} %>
							</div>
							<div class="panel-body">


								<form role="form" METHOD=POST action="logica">
									<%
										int dir[] = dispositivoLocalDTO[i].getDir();
									%>
									<input type="hidden" name="accion" value="guardar"> <input
										type="hidden" name="dir1" value="<%=dir[0]%>"> <input
										type="hidden" name="dir2" value="<%=dir[1]%>"> <input
										type="hidden" name="dir3" value="<%=dir[2]%>"> <input
										type="hidden" name="dir4" value="<%=dir[3]%>"> <input
										type="hidden" name="dir5" value="<%=dir[4]%>"> <input
										type="hidden" name="dir6" value="<%=dir[5]%>"> <input
										type="hidden" name="dir7" value="<%=dir[6]%>"> <input
										type="hidden" name="dir8" value="<%=dir[7]%>">
									<%
										for (int n = 0; n < perifericoLocalDTO.length; n++) {
												boolean boleano = perifericoLocalDTO[n].isBooleano();
												String disable = "";
												if (perifericoLocalDTO[n].isEscribible() && activo) {
													disable = "";
												} else {
													disable = "disabled";
												}
									%>
									<%
										if (!boleano) {
									%>
									<div class="form-group">
										<label for="periferico"><%=perifericoLocalDTO[n].getNombreperi()%></label>
										<input id="pos<%=perifericoLocalDTO[n].getPosicion()%>"
											name="pos<%=perifericoLocalDTO[n].getPosicion()%>"
											class="form-control"
											value="<%=perifericoLocalDTO[n].getValReal()%>" <%=disable%>>
									</div>
									<%
										} else {
									%>
									<div class="form-group">
										<label for="periferico"><%=perifericoLocalDTO[n].getNombreperi()%></label>
										<select class="form-control"
											name="pos<%=perifericoLocalDTO[n].getPosicion()%>"
											id="pos<%=perifericoLocalDTO[n].getPosicion()%>" <%=disable%>>
											<%
												if (perifericoLocalDTO[n].isValbool()) {
											%>

											<option>ON</option>
											<option>OFF</option>

											<%
												} else {
											%>
											<option>OFF</option>
											<option>ON</option>

											<%
												}
											%>

										</select>

									</div>
									<%
										}
									%>
									<%
										}
											String disable = "";
											if (activo) {
												disable = "";
											} else {
												disable = "disabled";
											}
									%>

									<button type="submit" class="btn btn-default" <%=disable%>>Guardar</button>

								</form>





							</div>

						</div>
					</div>
					<%
						}
					%>


				</div>




			</div>


		</div>




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

