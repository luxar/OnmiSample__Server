package com.servlet;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Servelet que se encarga de la logica de negocio.
 * La operaciones sobre la base de datos las hace sobre facade.
 * @author Lucas Alvarez Argüero
 *
 */
public class Logica extends HttpServlet {
	private final static Logger log = Logger.getLogger(Logica.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 2657L;
	

	// Runs when the servlet is loaded onto the server.
	public void init() {

		
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
		

	}

	// Runs when the servlet is unloaded from the server.
	public void destroy() {

	}

	// Other instance variables and methods
}