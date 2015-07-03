package com.connectionLayer.recurrente;

import org.apache.log4j.Logger;

import com.connectionLayer.connectors.XConnection;
import com.rapplogic.xbee.api.XBee;
/**
 * clase principal que comienza todas las funciones de mantenimiento de la infrastuctura xbee
 * (envio de peticiones establecimiento de un hilo receptor etc)
 * @author Lucas Alvarez Argüero
 *
 */
public class Mantenimiento {
	private XBee xbee = null;
	@SuppressWarnings("unused")
	private final static Logger log = Logger.getLogger(Mantenimiento.class);

	public Mantenimiento() {
		xbee = XConnection.getConnection();
		// comienzo receptor
		
	}

}
