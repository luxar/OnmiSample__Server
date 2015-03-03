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
/**
 * clase principal que comienza todas las funciones de mantenimiento de la infrastuctura xbee
 * (envio de peticiones establecimiento de un hilo receptor etc)
 * @author Lucas Alvarez Argüero
 *
 */
public class Mantenimiento {
	private XBee xbee = null;
	private final static Logger log = Logger.getLogger(Mantenimiento.class);

	public Mantenimiento() {
		xbee = XConnection.getConnection();
		// comienzo receptor
		
	}

}
