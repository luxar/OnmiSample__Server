package com.rapplogic.xbee.connectionLayer.recurrente;

import java.sql.Connection;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.rapplogic.xbee.api.ApiId;
import com.rapplogic.xbee.api.PacketListener;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.zigbee.ZNetRxResponse;
import com.rapplogic.xbee.api.zigbee.ZNetRxBaseResponse.Option;
import com.rapplogic.xbee.connectionLayer.DAO.PerifericoExternoDAO;
import com.rapplogic.xbee.connectionLayer.DAO.PerifericoLocalDAO;
import com.rapplogic.xbee.connectionLayer.DTO.PerifericoDTO;
import com.rapplogic.xbee.connectionLayer.connectors.XConnection;
import com.rapplogic.xbee.connectionLayer.funcCom.FuncEnvio;
import com.rapplogic.xbee.connectionLayer.funcCom.HiloSonda;
import com.rapplogic.xbee.util.ByteUtils;
import com.rapplogic.xbee.util.DoubleByte;

public class Mantenimiento {
	private XBee xbee = null;
	private Connection conI = null;
	private Connection conE = null;
	private final static Logger log = Logger.getLogger(Mantenimiento.class);

	public Mantenimiento() {
		xbee = XConnection.getConnection();
		// comienzo receptor
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
							int Numseriebits[] = { datos[1], datos[2],
									datos[3], datos[4] };
							log.info("lectura del numero de serie: "
									+ ByteUtils
											.convertMultiByteToInt(Numseriebits));
							int Numserie = ByteUtils
									.convertMultiByteToInt(Numseriebits);
							// infotmacion de dispositivo
							PerifericoLocalDAO perifericoLocalDAO = new PerifericoLocalDAO();
							

							boolean existe = perifericoLocalDAO.registrado(direcion);
							if (existe) {
								log.info("Existe");
							} else {
								log.info("NO Existe");
								PerifericoExternoDAO perifericoExternoDAO = new PerifericoExternoDAO();
								Collection<PerifericoDTO> perifericoDTO = perifericoExternoDAO
										.dispositvo(Numserie);
								log.info("periferico con numero de serie "
										+ Numserie + " que tiene "
										+ perifericoDTO.size() + " puertos");
								log.info("añadiendo a base interna de datos");
								perifericoLocalDAO.anhadirADB(perifericoDTO,
										direcion);
							}

						} else if (datos[0] == 0x52) {

							log.info("Lectura de un sensor");

							log.info("Sensor numero: " + datos[1]);
							DoubleByte valor2Byte = new DoubleByte(datos[2],
									datos[3]);
							int valor = valor2Byte.get16BitValue();
							log.info("Valor: " + valor);
							PerifericoLocalDAO perifericoLocalDAO = new PerifericoLocalDAO();
							perifericoLocalDAO.recogerDatos(valor, direcion, datos[1]);
							// lectura
						} else {
							log.info("Comando no valido");
							// ni idea
						}
					}

				} else if (response.getApiId() == ApiId.ZNET_IO_NODE_IDENTIFIER_RESPONSE) {

					log.info("Se ha conectado un nuevo dispositivo a la red");
					FuncEnvio funcEnvio = new FuncEnvio();
					funcEnvio.solicitarNumseries(xbee);

				} else {

					log.debug("received unexpected packet "
							+ response.toString());
				}
			}
		});
		// fin receptor
		HiloSonda hiloSonda = new HiloSonda(xbee);
		hiloSonda.start();
	}

}
