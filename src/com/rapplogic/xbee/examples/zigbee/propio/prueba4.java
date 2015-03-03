package com.rapplogic.xbee.examples.zigbee.propio;

import java.util.Collection;

import org.apache.log4j.PropertyConfigurator;

import com.rapplogic.xbee.connectionLayer.Facade;
import com.rapplogic.xbee.connectionLayer.DAO.PerifericoLocalDAO;
import com.rapplogic.xbee.connectionLayer.DTO.DispositivoLocalDTO;
import com.rapplogic.xbee.connectionLayer.DTO.PerifericoLocalDTO;
import com.rapplogic.xbee.util.Escalado;

public class prueba4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PropertyConfigurator.configure("log4j.properties");

		Facade facade = new Facade();
		int dir[]={0,19,162,0,64,50,228,35};
		
		Collection<DispositivoLocalDTO> dispositivoLocalDTO= facade.todosDispositivosLocales();
		Collection<PerifericoLocalDTO> perifericoLocalDTO = facade.perifericosPorDirecion(dir);
		int i =1;
	}

}
