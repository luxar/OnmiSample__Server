/**
 * Copyright (c) 2008 Andrew Rapp. All rights reserved.
 *  
 * This file is part of XBee-API.
 *  
 * XBee-API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * XBee-API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with XBee-API.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.rapplogic.xbee.examples.zigbee.propio;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.rapplogic.xbee.api.ApiId;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.XBeeTimeoutException;
import com.rapplogic.xbee.api.zigbee.ZNetTxRequest;
import com.rapplogic.xbee.util.ByteUtils;

/** 
 * @author andrew
 */
public class Pruebas {

	private final static Logger log = Logger.getLogger(Pruebas.class);
	
	private Pruebas() throws XBeeException {
		
		XBee xbee = new XBee();
		
		try {
			// replace with your com port and baud rate. this is the com port of my coordinator		
			xbee.open("COM3", 9600);
			
			while (true) {
				// put some arbitrary data in the payload
				int [] bytes = new int [4]  ;
				 bytes [0] = 'R';
				 bytes [1] = 0x02;
				 bytes [2] = 0x00;
				 int[] bytes2 =ByteUtils.convertInttoMultiByte(250);
				 bytes [3] = bytes2[0];
				int[] payload =ByteUtils.convertInttoMultiByte( (ByteUtils.convertMultiByteToInt(bytes)));
				
				ZNetTxRequest request = new ZNetTxRequest(XBeeAddress64.BROADCAST, payload);
				// make it a broadcast packet
				request.setOption(ZNetTxRequest.Option.BROADCAST);

				log.info("request packet bytes (base 16) " + ByteUtils.toBase16(request.getXBeePacket().getPacket()));
				
				xbee.sendAsynchronous(request);
				// we just assume it was sent.  that's just the way it is with broadcast.  
				// no transmit status response is sent, so don't bother calling getResponse()
				try {
			        XBeeResponse response = xbee.getResponse(10000);
			        
			        if (response.getApiId() == ApiId.ZNET_TX_REQUEST) {
			        	   // since this API ID is AT_RESPONSE, we know to cast to AtCommandResponse
			        	

			        	    log.info("received response " + response);
			        	}else{
			        		log.info("no se que me ha llegado " + response);
			        	}
			        
			        
			        // we got a response!
			    } catch (XBeeTimeoutException e) {
			        // we timed out without a response
			    }
				
				try {
					// wait a bit then send another packet
					Thread.sleep(15000);
				} catch (InterruptedException e) {
				}
			}
		} finally {
			xbee.close();
		}
	}
	
	public static void main(String[] args) throws XBeeException, InterruptedException  {
		PropertyConfigurator.configure("log4j.properties");
		new Pruebas();
	}
}
