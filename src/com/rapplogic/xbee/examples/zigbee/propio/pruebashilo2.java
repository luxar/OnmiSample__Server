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

import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.rapplogic.xbee.api.ApiId;
import com.rapplogic.xbee.api.PacketListener;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress16;
import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.XBeeTimeoutException;
import com.rapplogic.xbee.api.zigbee.ZNetRxResponse;
import com.rapplogic.xbee.api.zigbee.ZNetTxRequest;
import com.rapplogic.xbee.api.zigbee.ZNetTxStatusResponse;
import com.rapplogic.xbee.api.zigbee.ZNetRxBaseResponse.Option;
import com.rapplogic.xbee.util.ByteUtils;
import com.rapplogic.xbee.util.DoubleByte;

/** 
 * @author andrew
 */
public class pruebashilo2 {

	private final static Logger log = Logger.getLogger(pruebashilo2.class);
	
	private pruebashilo2() throws XBeeException {
		
		XBee xbee = new XBee();
		
		try {
			xbee.open("COM3", 9600);
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
			
			XBeeAddress64 addr64 = new XBeeAddress64(0, 0x13, 0xa2, 0, 0x40, 0x32, 0xe4, 0x23);
			while(true){
				Scanner scanner = new Scanner(System.in);
				System.out.println("ingrese numreo de sensor que quiere leer");
				int numero= scanner.nextInt();
				int[] payload = new int[] { 'R', numero, 0, 0};
				ZNetTxRequest request = new ZNetTxRequest(addr64, payload);
				try {
					ZNetTxStatusResponse response = (ZNetTxStatusResponse) xbee.sendSynchronous(request, 10000);
					// update frame id for next request
					request.setFrameId(xbee.getNextFrameId());
					
					log.info("received response " + response);
					long start = System.currentTimeMillis();
					//log.debug("status response bytes:" + ByteUtils.toBase16(response.getPacketBytes()));

					if (response.getDeliveryStatus() == ZNetTxStatusResponse.DeliveryStatus.SUCCESS) {
						// the packet was successfully delivered
						if (response.getRemoteAddress16().equals(XBeeAddress16.ZNET_BROADCAST)) {
							// specify 16-bit address for faster routing?.. really only need to do this when it changes
							request.setDestAddr16(response.getRemoteAddress16());
						}							
					} else {
						// packet failed.  log error
						// it's easy to create this error by unplugging/powering off your remote xbee.  when doing so I get: packet failed due to error: ADDRESS_NOT_FOUND  
						log.error("packet failed due to error: " + response.getDeliveryStatus());
					}
					
					// I get the following message: Response in 75, Delivery status is SUCCESS, 16-bit address is 0x08 0xe5, retry count is 0, discovery status is SUCCESS 
					log.info("Response in " + (System.currentTimeMillis() - start) + ", Delivery status is " + response.getDeliveryStatus() + ", 16-bit address is " + ByteUtils.toBase16(response.getRemoteAddress16().getAddress()) + ", retry count is " +  response.getRetryCount() + ", discovery status is " + response.getDeliveryStatus());					
				} catch (XBeeTimeoutException e) {
					log.warn("request timed out");
				}
			}
			
			
		} finally {
			xbee.close();
		}
	}
	
	private void While(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) throws XBeeException, InterruptedException  {
		PropertyConfigurator.configure("log4j.properties");
		new pruebashilo2();
	}
}
