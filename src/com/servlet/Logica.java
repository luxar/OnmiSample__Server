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
 * Servelet que se encarga de la logica de negocio.
 * La operaciones sobre la base de datos las hace sobre facade.
 * @author Lucas Alvarez Arg�ero
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
	 * Clase que atienede peticiones post.
	 * El parametro accion especificara que debe hacer.
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String accion = request.getParameter("accion");
		HttpSession session = request.getSession();
		log.info("Iniciado servlet ");
		if (accion.equals("guardar")) {
			
			int dir[]={Integer.parseInt(request.getParameter("dir1")),Integer.parseInt(request.getParameter("dir2")),Integer.parseInt(request.getParameter("dir3")),Integer.parseInt(request.getParameter("dir4")),Integer.parseInt(request.getParameter("dir5")),Integer.parseInt(request.getParameter("dir6")),Integer.parseInt(request.getParameter("dir7")),Integer.parseInt(request.getParameter("dir8"))};
			fc.perifericosPorDirecion(dir);
			PerifericoLocalDTO[] perifericoLocalDTO = (PerifericoLocalDTO[]) fc
					.perifericosPorDirecion(dir).toArray(
							new PerifericoLocalDTO[0]);
			for(int i=0;i<perifericoLocalDTO.length;i++){
				
				if(!perifericoLocalDTO[i].isEscribible()){
					break;
				}
				
				String posicionSupuesta = "pos"+Integer.toString( i+1);
				String valor = request.getParameter(posicionSupuesta);
				if(valor.equals("ON")){
					if(perifericoLocalDTO[i].isValbool()==true)
						continue;
					fc.enviarValor(i+1, dir, true);
				}else if (valor.equals("OFF")){
					if(perifericoLocalDTO[i].isValbool()==false)
						continue;
					fc.enviarValor(i+1, dir, false);
				}else{
					if(perifericoLocalDTO[i].getValReal()==Integer.parseInt(valor))
						continue;
					fc.enviarValor(i+1, dir, Integer.parseInt(valor));
					int n;
				}
				
				
				
			}
			
			
			
			
			
			// Redirecion
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