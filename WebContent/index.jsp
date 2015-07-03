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

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript">
function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode ==45){
        return true;
        }
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}
</script>
<script type="text/javascript">
/*! Idle Timer v1.0.1 2014-03-21 | https://github.com/thorst/jquery-idletimer | (c) 2014 Paul Irish | Licensed MIT */
!function(a){a.idleTimer=function(b,c){var d;"object"==typeof b?(d=b,b=null):"number"==typeof b&&(d={timeout:b},b=null),c=c||document,d=a.extend({idle:!1,timeout:3e4,events:"mousemove keydown wheel DOMMouseScroll mousewheel mousedown touchstart touchmove MSPointerDown MSPointerMove"},d);var e=a(c),f=e.data("idleTimerObj")||{},g=function(b){var d=a.data(c,"idleTimerObj")||{};d.idle=!d.idle,d.olddate=+new Date;var e=a.Event((d.idle?"idle":"active")+".idleTimer");a(c).trigger(e,[c,a.extend({},d),b])},h=function(b){var d=a.data(c,"idleTimerObj")||{};if(null==d.remaining){if("mousemove"===b.type){if(b.pageX===d.pageX&&b.pageY===d.pageY)return;if("undefined"==typeof b.pageX&&"undefined"==typeof b.pageY)return;var e=+new Date-d.olddate;if(200>e)return}clearTimeout(d.tId),d.idle&&g(b),d.lastActive=+new Date,d.pageX=b.pageX,d.pageY=b.pageY,d.tId=setTimeout(g,d.timeout)}},i=function(){var b=a.data(c,"idleTimerObj")||{};b.idle=b.idleBackup,b.olddate=+new Date,b.lastActive=b.olddate,b.remaining=null,clearTimeout(b.tId),b.idle||(b.tId=setTimeout(g,b.timeout))},j=function(){var b=a.data(c,"idleTimerObj")||{};null==b.remaining&&(b.remaining=b.timeout-(+new Date-b.olddate),clearTimeout(b.tId))},k=function(){var b=a.data(c,"idleTimerObj")||{};null!=b.remaining&&(b.idle||(b.tId=setTimeout(g,b.remaining)),b.remaining=null)},l=function(){var b=a.data(c,"idleTimerObj")||{};clearTimeout(b.tId),e.removeData("idleTimerObj"),e.off("._idleTimer")},m=function(){var b=a.data(c,"idleTimerObj")||{};if(b.idle)return 0;if(null!=b.remaining)return b.remaining;var d=b.timeout-(+new Date-b.lastActive);return 0>d&&(d=0),d};if(null===b&&"undefined"!=typeof f.idle)return i(),e;if(null===b);else{if(null!==b&&"undefined"==typeof f.idle)return!1;if("destroy"===b)return l(),e;if("pause"===b)return j(),e;if("resume"===b)return k(),e;if("reset"===b)return i(),e;if("getRemainingTime"===b)return m();if("getElapsedTime"===b)return+new Date-f.olddate;if("getLastActiveTime"===b)return f.lastActive;if("isIdle"===b)return f.idle}return e.on(a.trim((d.events+" ").split(" ").join("._idleTimer ")),function(a){h(a)}),f=a.extend({},{olddate:+new Date,lastActive:+new Date,idle:d.idle,idleBackup:d.idle,timeout:d.timeout,remaining:null,tId:null,pageX:null,pageY:null}),f.idle||(f.tId=setTimeout(g,f.timeout)),a.data(c,"idleTimerObj",f),e},a.fn.idleTimer=function(b){return this[0]?a.idleTimer(b,this[0]):this}}(jQuery);
$.idleTimer(7000);


$(document).bind("idle.idleTimer", function(){
 // function you want to fire when the user goes idle
	 location.reload(true);
});


$(document).bind("active.idleTimer", function(){
 // function you want to fire when the user becomes active again
	 location.reload(true);
});

</script>


<%@ page import="com.connectionLayer.recurrente.Mantenimiento"%>
<%@ page import="com.connectionLayer.Facade"%>
<%@ page import="com.connectionLayer.DTO.*"%>
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
									<input type="hidden" name="accion" value="borrar"> <input
										type="hidden" name="dir1" value="<%=dir[0]%>"> <input
										type="hidden" name="dir2" value="<%=dir[1]%>"> <input
										type="hidden" name="dir3" value="<%=dir[2]%>"> <input
										type="hidden" name="dir4" value="<%=dir[3]%>"> <input
										type="hidden" name="dir5" value="<%=dir[4]%>"> <input
										type="hidden" name="dir6" value="<%=dir[5]%>"> <input
										type="hidden" name="dir7" value="<%=dir[6]%>"> <input
										type="hidden" name="dir8" value="<%=dir[7]%>">

									<button alingt type="submit" class="btn btn-danger"
										align="right">Eliminar Dispositivo</button>
								</form>
								<br>
								<form role="form" METHOD=POST action="logica">

									<input type="hidden" name="accion" value="nombre"> <input
										type="hidden" name="dir1" value="<%=dir[0]%>"> <input
										type="hidden" name="dir2" value="<%=dir[1]%>"> <input
										type="hidden" name="dir3" value="<%=dir[2]%>"> <input
										type="hidden" name="dir4" value="<%=dir[3]%>"> <input
										type="hidden" name="dir5" value="<%=dir[4]%>"> <input
										type="hidden" name="dir6" value="<%=dir[5]%>"> <input
										type="hidden" name="dir7" value="<%=dir[6]%>"> <input
										type="hidden" name="dir8" value="<%=dir[7]%>"> <input
										id="nuevoNombre" name="nuevoNombre" class="form-control"
										value="<%=dispositivoLocalDTO[i].getNombre()%>"> <br>
									<button type="submit" class="btn btn-warning">Cambiar
										nombre</button>
								</form>

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
										<input onkeypress="return isNumber(event)" id="pos<%=perifericoLocalDTO[n].getPosicion()%>"
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

