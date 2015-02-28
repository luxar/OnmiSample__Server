package com.rapplogic.xbee.connectionLayer.funcCom;

import java.sql.Connection;

import org.apache.log4j.Logger;

import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress16;
import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.zigbee.ZNetTxRequest;
import com.rapplogic.xbee.api.zigbee.ZNetTxStatusResponse;
import com.rapplogic.xbee.connectionLayer.DAO.PerifericoLocalDAO;
import com.rapplogic.xbee.connectionLayer.DTO.PerifericoLocalDTO;
import com.rapplogic.xbee.connectionLayer.connectors.IConnection;
import com.rapplogic.xbee.util.ByteUtils;

public class HiloSonda extends Thread {
	private final static Logger log = Logger.getLogger(HiloSonda.class);
	XBee xbee = null;
	Connection con = null;

	public HiloSonda(XBee xbee) {
		this.xbee = xbee;
		con = IConnection.getConnection();

	}

	public void run() {
		int pos = 0;
		//TODO pasar a inactivos y activos
		PerifericoLocalDAO perifericoLocalDAO = new PerifericoLocalDAO();
		while (true) {
			pos++;

			PerifericoLocalDTO[] perifericoLocalDTO = (PerifericoLocalDTO[]) perifericoLocalDAO
					.perifericosPorPuerto(pos).toArray(
							new PerifericoLocalDTO[0]);
			if (perifericoLocalDTO.length == 0) {
				pos = 0;
			} else {
				for (int i = 0; i < perifericoLocalDTO.length; i++) {
					XBeeAddress64 addr64 = new XBeeAddress64(
							perifericoLocalDTO[i].getDir());
					int[] payload = new int[] { 'R', pos, 0, 0 };
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
				}

			}

			try {
				sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
