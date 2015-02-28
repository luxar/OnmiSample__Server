package com.rapplogic.xbee.examples.zigbee.propio;

import org.apache.log4j.PropertyConfigurator;

import com.rapplogic.xbee.connectionLayer.DAO.PerifericoLocalDAO;
import com.rapplogic.xbee.util.Escalado;

public class prueba4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PropertyConfigurator.configure("log4j.properties");

		PerifericoLocalDAO perifericoLocalDAO = new PerifericoLocalDAO();
		int dir[]={0,19,162,0,64,50,228,35};
		perifericoLocalDAO.enviarValor(6, dir, 100);
		
	}

}
