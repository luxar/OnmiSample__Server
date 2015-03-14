package com.rapplogic.xbee.connectionLayer.funcCom;

import org.apache.log4j.Logger;

import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress16;
import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.zigbee.ZNetTxRequest;
import com.rapplogic.xbee.api.zigbee.ZNetTxStatusResponse;
import com.rapplogic.xbee.util.ByteUtils;
import com.rapplogic.xbee.util.DoubleByte;
/**
 * Clase que contiene funciones de envio de datos por xbee
 * @author Lucas Alvarez Argüero
 *
 */
public class FuncEnvio {
	
	private final static Logger log = Logger.getLogger(FuncEnvio.class);
	/**
	 * Funcion que envia un mensaje en broadcast preguntando a todos su numero de serie.
	 * @param xbee conexion xbee para usar
	 */
	public static void solicitarNumseries(XBee xbee) {
		try {
		int[] payload = ByteUtils.stringToIntArray("Q");
		
		ZNetTxRequest request = new ZNetTxRequest(XBeeAddress64.BROADCAST, payload);
		// make it a broadcast packet
		request.setOption(ZNetTxRequest.Option.BROADCAST);

		log.info("Se envia paquete de pregunta en broadcast a todos.");
		
		
			xbee.sendAsynchronous(request);
		} catch (XBeeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// we just assume it was sent.  that's just the way it is with broadcast.  
		// no transmit status response is sent, so don't bother calling getResponse()
	}
	
	/**
	 * Envia una escritura de un unico puerto 
	 * @param xbee conexion XBEE
	 * @param valor Valor que se envia (en caso de booleano 0 para false y cualquier otro valor TRUE)
	 * @param dir direccion de destion
	 * @param pos posicion que se desea escribir
	 */
	public static void enviarEscritura (XBee xbee, int valor , int dir[], int pos) {
		
		
		
		
		XBeeAddress64 addr64 = new XBeeAddress64(dir);
		DoubleByte valor2Byte = new DoubleByte( valor);
		int[] payload = new int[] { 'W', pos, valor2Byte.getMsb(), valor2Byte.getLsb() };
		ZNetTxRequest request = new ZNetTxRequest(addr64, payload);
		try {
			ZNetTxStatusResponse response = (ZNetTxStatusResponse) xbee
					.sendSynchronous(request, 5000);
			// update frame id for next request
			request.setFrameId(xbee.getNextFrameId());

			log.info("received response " + response);
			
			

			if (response.getDeliveryStatus() == ZNetTxStatusResponse.DeliveryStatus.SUCCESS) {
				// the packet was successfully delivered
				if (response.getRemoteAddress16().equals(
						XBeeAddress16.ZNET_BROADCAST)) {
					// specify 16-bit address for faster routing?..
					// really only need to do this when it changes
					request.setDestAddr16(response
							.getRemoteAddress16());
				}
			} else {
				// packet failed. log error
				// it's easy to create this error by
				// unplugging/powering off your remote xbee. when
				// doing so I get: packet failed due to error:
				// ADDRESS_NOT_FOUND
				log.error("packet failed due to error: "
						+ response.getDeliveryStatus());
			}

			
		} catch (XBeeException e) {
			log.warn("request timed out");
		}
		
		
		// we just assume it was sent.  that's just the way it is with broadcast.  
		// no transmit status response is sent, so don't bother calling getResponse()
	}
	/**
	 * Funcion que envia la trama payload a un xbee
	 * @param xbee conexion XBEE
	 * @param dir direccion de destino
	 * @param payload Trama completa
	 */
public static void enviarTrama (XBee xbee, int dir[], int payload[]) {
		
		
		
		
		XBeeAddress64 addr64 = new XBeeAddress64(dir);
		
		ZNetTxRequest request = new ZNetTxRequest(addr64, payload);
		try {
			ZNetTxStatusResponse response = (ZNetTxStatusResponse) xbee
					.sendSynchronous(request, 5000);
			// update frame id for next request
			request.setFrameId(xbee.getNextFrameId());

			log.info("received response " + response);
			
			

			if (response.getDeliveryStatus() == ZNetTxStatusResponse.DeliveryStatus.SUCCESS) {
				// the packet was successfully delivered
				if (response.getRemoteAddress16().equals(
						XBeeAddress16.ZNET_BROADCAST)) {
					// specify 16-bit address for faster routing?..
					// really only need to do this when it changes
					request.setDestAddr16(response
							.getRemoteAddress16());
				}
			} else {
				// packet failed. log error
				// it's easy to create this error by
				// unplugging/powering off your remote xbee. when
				// doing so I get: packet failed due to error:
				// ADDRESS_NOT_FOUND
				log.error("packet failed due to error: "
						+ response.getDeliveryStatus());
			}

			
		} catch (XBeeException e) {
			log.warn("request timed out");
		}
		
		
		// we just assume it was sent.  that's just the way it is with broadcast.  
		// no transmit status response is sent, so don't bother calling getResponse()
	}
	
	
}
