package com.connectionLayer.funcCom;

import java.sql.Connection;

import org.apache.log4j.Logger;

import com.connectionLayer.DAO.PerifericoLocalDAO;
import com.connectionLayer.DTO.DispositivoLocalDTO;
import com.connectionLayer.connectors.IConnection;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress16;
import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.zigbee.ZNetTxRequest;
import com.rapplogic.xbee.api.zigbee.ZNetTxStatusResponse;

/**
 * Crea un hilo que reiteradamente envía mensajes de lectura a todos los dispositivos DomoEnd de la red que estén activos.
 * 
 * @author Lucas Alvarez Argüero
 *
 */
public class HiloSonda extends Thread {
	private final static Logger log = Logger.getLogger(HiloSonda.class);
	XBee xbee = null;
	Connection con = null;

	public HiloSonda(XBee xbee) {
		this.xbee = xbee;
		con = IConnection.getConnection();

	}
/**
 * Hilo que reiteradamente envia peticiones de lectura a todos los dispositivos activos de la red DomoSystem.
 */
	public void run() {
		int pos = 0;
		// TODO pasar a inactivos y activos
		PerifericoLocalDAO perifericoLocalDAO = new PerifericoLocalDAO();
		while (true) {

			DispositivoLocalDTO[] DispositivoLocalDTO = (DispositivoLocalDTO[]) perifericoLocalDAO
					.todosDispositivosLocalesActivos().toArray(
							new DispositivoLocalDTO[0]);

			for (int i = 0; i < DispositivoLocalDTO.length; i++) {

				int numero = perifericoLocalDAO
						.numeroPerifericos(DispositivoLocalDTO[i].getDir());
				int mensaje[] = new int[numero + 1];

				mensaje[0] = 'R';
				for (int n = 1; n < numero + 1; n++) {

					mensaje[n] = n;
				}
				XBeeAddress64 addr64 = new XBeeAddress64(
						DispositivoLocalDTO[i].getDir());
				ZNetTxRequest request = new ZNetTxRequest(addr64, mensaje);
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
							request.setDestAddr16(response.getRemoteAddress16());
						}
					} else {
						// packet failed. log error
						// it's easy to create this error by
						// unplugging/powering off your remote xbee. when
						// doing so I get: packet failed due to error:
						// ADDRESS_NOT_FOUND
						log.error("packet failed due to error: "
								+ response.getDeliveryStatus());
						perifericoLocalDAO.ponerInactivo(DispositivoLocalDTO[i]
								.getDir());

					}

				} catch (XBeeException e) {
					log.warn("request timed out");
				}
			}

			try {
				sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
