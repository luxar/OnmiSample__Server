package com.rapplogic.xbee.connectionLayer.funcCom;

import org.apache.log4j.Logger;

import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.zigbee.ZNetTxRequest;
import com.rapplogic.xbee.util.ByteUtils;

public class FuncEnvio {
	
	private final static Logger log = Logger.getLogger(FuncEnvio.class);
	
	public void solicitarNumseries(XBee xbee) {
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
}
