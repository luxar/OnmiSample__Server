package com.servlet;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.rapplogic.xbee.connectionLayer.Facade;
import com.rapplogic.xbee.connectionLayer.DTO.PerifericoLocalDTO;

/**
 * Servelet que se encarga de la logica de negocio. La operaciones sobre la base
 * de datos las hace sobre facade.
 * 
 * @author Lucas Alvarez Argüero
 *
 */
public class Logica extends HttpServlet {
	private final static Logger log = Logger.getLogger(Logica.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 2657L;

	Facade fc;

	// Runs when the servlet is loaded onto the server.
	public void init() {
		fc = new Facade();

	}

	/**
	 * Clase que atienede peticiones post. El parametro accion especificara que
	 * debe hacer.
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String accion = request.getParameter("accion");
		HttpSession session = request.getSession();
		log.info("Iniciado servlet ");
		if (accion.equals("guardar")) {

			int dir[] = { Integer.parseInt(request.getParameter("dir1")),
					Integer.parseInt(request.getParameter("dir2")),
					Integer.parseInt(request.getParameter("dir3")),
					Integer.parseInt(request.getParameter("dir4")),
					Integer.parseInt(request.getParameter("dir5")),
					Integer.parseInt(request.getParameter("dir6")),
					Integer.parseInt(request.getParameter("dir7")),
					Integer.parseInt(request.getParameter("dir8")) };
			fc.perifericosPorDirecion(dir);
			PerifericoLocalDTO[] perifericoLocalDTO = (PerifericoLocalDTO[]) fc
					.perifericosPorDirecionEscribibles(dir).toArray(
							new PerifericoLocalDTO[0]);
			String[] valores = new String[perifericoLocalDTO.length];
			for (int i = 0; i < perifericoLocalDTO.length; i++) {

				String posicionSupuesta = "pos" + Integer.toString(i + 1);
				String valor = request.getParameter(posicionSupuesta);
				valores[i] = valor;

			}
			fc.enviarValores(dir, valores);

			// Redirecion
			String site = "index.jsp";
			response.setStatus(response.SC_MOVED_TEMPORARILY);
			response.setHeader("Location", site);
		} else if (accion.equals("admin")) {
			if (fc.isAdminMode()) {
				fc.setAdminMode(false);
			} else {
				fc.setAdminMode(true);
			}
			String site = "index.jsp";
			response.setStatus(response.SC_MOVED_TEMPORARILY);
			response.setHeader("Location", site);
		} else if (accion.equals("nombre")){
			int dir[] = { Integer.parseInt(request.getParameter("dir1")),
					Integer.parseInt(request.getParameter("dir2")),
					Integer.parseInt(request.getParameter("dir3")),
					Integer.parseInt(request.getParameter("dir4")),
					Integer.parseInt(request.getParameter("dir5")),
					Integer.parseInt(request.getParameter("dir6")),
					Integer.parseInt(request.getParameter("dir7")),
					Integer.parseInt(request.getParameter("dir8")) };
			String nuevoNombre= request.getParameter("nuevoNombre");
			
			fc.cambiarNombre(nuevoNombre, dir);
			fc.setAdminMode(false);
			String site = "index.jsp";
			response.setStatus(response.SC_MOVED_TEMPORARILY);
			response.setHeader("Location", site);
		}else if (accion.equals("borrar")){
			int dir[] = { Integer.parseInt(request.getParameter("dir1")),
					Integer.parseInt(request.getParameter("dir2")),
					Integer.parseInt(request.getParameter("dir3")),
					Integer.parseInt(request.getParameter("dir4")),
					Integer.parseInt(request.getParameter("dir5")),
					Integer.parseInt(request.getParameter("dir6")),
					Integer.parseInt(request.getParameter("dir7")),
					Integer.parseInt(request.getParameter("dir8")) };
			
			
			fc.borrarPeriferico(dir);
			fc.setAdminMode(false);
			String site = "index.jsp";
			response.setStatus(response.SC_MOVED_TEMPORARILY);
			response.setHeader("Location", site);
		}

	}

	// Runs when the servlet is unloaded from the server.
	public void destroy() {

	}

	// Other instance variables and methods
}