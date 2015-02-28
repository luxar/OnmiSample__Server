package com.rapplogic.xbee.connectionLayer.connectors;

import java.util.ResourceBundle;
import org.apache.log4j.Logger;

import com.rapplogic.xbee.api.XBee;

public class XConnection {
	private static XBee xbee = null;
	private final static Logger log = Logger.getLogger(XConnection.class);

	public static XBee getConnection() {

		try {
			if (xbee == null) {
				Runtime.getRuntime().addShutdownHook(new MiShDwnHookX());
				ResourceBundle rb = ResourceBundle.getBundle("xbee");
				String puerto = rb.getString("port");
				xbee = new XBee();
				xbee.open(puerto, 9600);
				
			}
			return xbee;

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("Error al acceder a la conexion");
		}

	}
}

class MiShDwnHookX extends Thread {
	public void run() {
		try {
			XBee con = XConnection.getConnection();
			con.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

}