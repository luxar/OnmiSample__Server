package com.connectionLayer.pruebas;

import java.util.Collection;

import org.apache.log4j.PropertyConfigurator;

import com.connectionLayer.Facade;
import com.connectionLayer.DAO.PerifericoLocalDAO;
import com.connectionLayer.DTO.DispositivoLocalDTO;
import com.connectionLayer.DTO.PerifericoLocalDTO;
import com.connectionLayer.util.Escalado;

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
