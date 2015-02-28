package com.rapplogic.xbee.examples.zigbee.propio;

import java.sql.Connection;

import org.apache.log4j.PropertyConfigurator;








import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.connectionLayer.connectors.IConnection;

import com.rapplogic.xbee.connectionLayer.recurrente.Mantenimiento;

public class prueba3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PropertyConfigurator.configure("log4j.properties");
			//XBee xbee = ZConnection.getConnection();
			//Connection con = IConnection.getConnection();
		Mantenimiento mantenimiento = new Mantenimiento();
			while(true){
				
			}
	}
	

}
