package com.rapplogic.xbee.connectionLayer;

import java.util.Collection;

import com.rapplogic.xbee.connectionLayer.DAO.*;
import com.rapplogic.xbee.connectionLayer.DTO.*;
import com.rapplogic.xbee.connectionLayer.connectors.XConnection;

public class Facade {

	/**
	 * Devuelve el dto de todos los perifericos del dispositivo(modelo de base
	 * datos externa)
	 * 
	 * @param numserie
	 *            numero de serie del dispositivo
	 * @return coleccion dto con todos los perifericos del dispositivo y todas
	 *         sus opciones
	 */
	public Collection<PerifericoDTO> dispositvo(int numserie) {
		PerifericoExternoDAO perifericoExternoDAO = new PerifericoExternoDAO();
		return perifericoExternoDAO.dispositvo(numserie);
	}

	/**
	 * Permite saber si la dir(unica) ya esta registrada en la DB local
	 * 
	 * @param dir
	 *            array con la direcion de consulta
	 * @return Verdadero si esta registrada falso si no lo esta.
	 */
	public boolean registrado(int dir[]) {
		PerifericoLocalDAO perifericoLocalDAO = new PerifericoLocalDAO();
		return perifericoLocalDAO.registrado(dir);
	}

	/**
	 * AÑade a la base de datos (tanto en dispositivo como en todos sus
	 * perifericos) el perifericoDTO (internamente busca nombre del periferico
	 * en la base de datos ecxterna)
	 * 
	 * @param perifericoDTO
	 *            coleccion de los perifercos
	 * @param direccion
	 *            del dispositivo
	 */
	public void anhadirADB(Collection<PerifericoDTO> perifericoDTO, int dir[]) {
		PerifericoLocalDAO perifericoLocalDAO = new PerifericoLocalDAO();
		perifericoLocalDAO.anhadirADB(perifericoDTO, dir);
	}

	/**
	 * Dado un puerto devuelve todos los perifericos que tengan ese puerto
	 * 
	 * @param puerto
	 *            puerto oposicion de numero que se encuentra ese periferico en
	 *            el dispositivo
	 * @return colecion periferico localDTO pero solo tiene informacion de
	 *         puertos
	 */
	public Collection<PerifericoLocalDTO> perifericosPorPuerto(int puerto) {
		PerifericoLocalDAO perifericoLocalDAO = new PerifericoLocalDAO();
		return perifericoLocalDAO.perifericosPorPuerto(puerto);
	}

	/**
	 * Sube un valor a la base d datos local
	 * 
	 * @param valor
	 *            valor a subir (en el caso de booleano 0= false y true
	 *            cualquier otro numero)
	 * @param dir
	 *            [] array con la direccion del dispositivo
	 * @param pos
	 *            posicion del periferico en el dispositivo
	 */
	public void recogerDatos(int valor, int dir[], int pos) {
		PerifericoLocalDAO perifericoLocalDAO = new PerifericoLocalDAO();
		perifericoLocalDAO.recogerDatos(valor, dir, pos);
	}

	/**
	 * Envia un valor al dispositivo a traves de la red xbee ademas lo sube a la
	 * base de datos local
	 * 
	 * @param pos
	 *            posicion del periferico
	 * @param dir
	 *            array con la direccion del periferco
	 * @param valor
	 *            valor numerico
	 */
	public void enviarValor(int pos, int dir[], int valor) {
		PerifericoLocalDAO perifericoLocalDAO = new PerifericoLocalDAO();
		perifericoLocalDAO.enviarValor(pos, dir, valor);
	}

	/**
	 * Envia un valor al dispositivo a traves de la red xbee ademas lo sube a la
	 * base de datos local
	 * 
	 * @param posicion
	 *            posicion del periferico
	 * @param dir
	 *            array con la direccion del periferco
	 * @param valor
	 *            valor Booleano
	 */
	public void enviarValor(int pos, int dir[], boolean valor) {
		PerifericoLocalDAO perifericoLocalDAO = new PerifericoLocalDAO();
		perifericoLocalDAO.enviarValor(pos, dir, valor);
	}

	/**
	 * Establece como inactivo el dispositivo
	 * 
	 * @param dir
	 *            direccion del dispositivo
	 */
	public void ponerInactivo(int dir[]) {
		PerifericoLocalDAO perifericoLocalDAO = new PerifericoLocalDAO();
		perifericoLocalDAO.ponerInactivo(dir);
	}

	/**
	 * Establece como activo el dispositivo
	 * 
	 * @param dir
	 *            direccion del dispositivo
	 */
	public void ponerActivo(int dir[]) {

		PerifericoLocalDAO perifericoLocalDAO = new PerifericoLocalDAO();
		perifericoLocalDAO.ponerActivo(dir);
	}

	/**
	 * Dada una direcion devuelve la coleccion de perifericos
	 * 
	 * @param dir
	 *            direccion solicitada (8 campos)
	 * @return coleccion de perifericos
	 */
	public Collection<PerifericoLocalDTO> perifericosPorDirecion(int dir[]) {
		PerifericoLocalDAO perifericoLocalDAO = new PerifericoLocalDAO();
		return perifericoLocalDAO.perifericosPorDirecion(dir);
	}

	/**
	 * Dada una direcion devuelve la coleccion de perifericos escribibles
	 * 
	 * @param dir
	 *            direccion solicitada (8 campos)
	 * @return coleccion de perifericos
	 */
	public Collection<PerifericoLocalDTO> perifericosPorDirecionEscribibles(
			int dir[]) {
		PerifericoLocalDAO perifericoLocalDAO = new PerifericoLocalDAO();
		return perifericoLocalDAO.perifericosPorDirecionEscribibles(dir);
	}

	/**
	 * Devuelve colecion de todos los dispositivos que hay en la red
	 * 
	 * @return
	 */
	public Collection<DispositivoLocalDTO> todosDispositivosLocales() {
		PerifericoLocalDAO perifericoLocalDAO = new PerifericoLocalDAO();
		return perifericoLocalDAO.todosDispositivosLocales();
	}

	/**
	 * Devuelve colecion de todos los dispositivos que hay en la red activos
	 * 
	 * @return
	 */
	public Collection<DispositivoLocalDTO> todosDispositivosLocalesActivos() {
		PerifericoLocalDAO perifericoLocalDAO = new PerifericoLocalDAO();
		return perifericoLocalDAO.todosDispositivosLocalesActivos();
	}

	/**
	 * Envia un valor al dispositivo a traves de la red xbee ademas lo sube a la
	 * base de datos local
	 * 
	 * @param posicion
	 *            posicion del periferico
	 * @param dir
	 *            array con la direccion del periferco
	 * @param valor
	 *            valor Booleano
	 */
	public void enviarValores(int dir[], String[] valor) {
		PerifericoLocalDAO perifericoLocalDAO = new PerifericoLocalDAO();
		perifericoLocalDAO.enviarValores(dir, valor);
	}

	/**
	 * Devuelve si se esta en modo admin
	 * 
	 * @return true si esta en modo admin.
	 */
	public boolean isAdminMode() {
		return XConnection.isAdminMode();
	}

	/**
	 * Establece el modo administrador (permite que se añadan nuevos
	 * dispositivos a la red cuando esta a true)
	 * 
	 * @param adminMode
	 *            booleano con el modo que se desea
	 */
	public void setAdminMode(boolean adminMode) {
		XConnection.setAdminMode(adminMode);
	}

	/**
	 * Devuelve true si ese nombre ya existe en la base de datos de dispositivos
	 * 
	 * @param nombre
	 *            nombre que se quiere comporbar
	 * @return
	 */
	public boolean existeNombre(String nombre) {
		PerifericoLocalDAO perifericoLocalDAO = new PerifericoLocalDAO();
		return perifericoLocalDAO.existeNombre(nombre);
	}

	/**
	 * Permite cambiar el nombre a un dispositivo en la base de datos local
	 * 
	 * @param nombre
	 *            nuevo nombre
	 * @param dir
	 *            diercion del dispositivo
	 */
	public void cambiarNombre(String nombre, int[] dir) {

		PerifericoLocalDAO perifericoLocalDAO = new PerifericoLocalDAO();
		perifericoLocalDAO.cambiarNombre(nombre, dir);
	}
}