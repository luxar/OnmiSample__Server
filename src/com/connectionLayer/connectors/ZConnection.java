package com.connectionLayer.connectors;


import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.rapplogic.xbee.api.ApiId;
import com.rapplogic.xbee.api.PacketListener;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.zigbee.ZNetRxResponse;
import com.rapplogic.xbee.api.zigbee.ZNetRxBaseResponse.Option;
import com.rapplogic.xbee.util.ByteUtils;
import com.rapplogic.xbee.util.DoubleByte;

public class ZConnection {
	private static XBee xbee  = null;
	private final static Logger log = Logger.getLogger(ZConnection.class);
	public static XBee getConnection() {
		PropertyConfigurator.configure("log4j.properties");
		try {
			if (xbee == null) {
				Runtime.getRuntime().addShutdownHook(new MiShDwnHookZ());
				ResourceBundle rb = ResourceBundle.getBundle("xbee");
				String puerto = rb.getString("port");
				xbee = new XBee();
				xbee.open(puerto, 9600);
				
				
				
				xbee.addPacketListener(new PacketListener() {
				    public void processResponse(XBeeResponse response) {
				        // handle the response
				    	log.info("received response " + response.toString());

						if (response.getApiId() == ApiId.ZNET_RX_RESPONSE) {
							// we received a packet from ZNetSenderTest.java
							ZNetRxResponse rx = (ZNetRxResponse) response;

							log.info("Received RX packet, option is "
									+ rx.getOption()
									+ ", sender 64 address is "
									+ ByteUtils.toBase16(rx.getRemoteAddress64()
											.getAddress())
									+ ", remote 16-bit address is "
									+ ByteUtils.toBase16(rx.getRemoteAddress16()
											.getAddress()) + ", data is "
									+ ByteUtils.toBase16(rx.getData()));
							log.info("obteniendo datos:");
							Option opcion = rx.getOption();
							if (opcion.name().equals("BROADCAST_PACKET")) {
								log.info("mensaje de broadcast no usable");
							} else if (opcion.name().equals("PACKET_ACKNOWLEDGED")) {
								int[] direcion = rx.getRemoteAddress64().getAddress();
								int[] datos = rx.getData();
								if (datos[0] == 0x51) {
									int Numseriebits[]={datos[1],datos[2],datos[3],datos[4]};
									log.info("lectura del numero de serie: "+ByteUtils.convertMultiByteToInt(Numseriebits));
									// infotmacion de dispositivo
									
								} else if (datos[0] == 0x52) {

									log.info("Lectura de un sensor");

									log.info("Sensor numero: " + datos[1]);
									DoubleByte valor2Byte = new DoubleByte(datos[2],datos[3]);
									int valor= valor2Byte.get16BitValue();
									log.info("Valor: " + valor);

									// lectura
								} else {
									log.info("Comando no valido");
									// ni idea
								}
							}

						} else {
							log.debug("received unexpected packet "
									+ response.toString());
						}
				    }
				});
			}
			return xbee;

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("Error al acceder a la base de datos");
		}

	}
}

class MiShDwnHookZ extends Thread {
	public void run() {
		try {
			XBee con = ZConnection.getConnection();
			con.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

}