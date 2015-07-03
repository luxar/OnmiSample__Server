package com.connectionLayer.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;

import com.connectionLayer.DTO.PerifericoDTO;
import com.connectionLayer.connectors.EConnection;
/**
 * engloba las funciones que tienen como fin tratar con la base de datos  de dispositivos DomoEnd externa.
 * @author Lucas Alvarez Argüero
 *
 */
public class PerifericoExternoDAO {
	
	
	
	
	/**
	 * Devuelve el dto de todos los perifericos del dispositivo(modelo de base datos externa)
	 * @param numserie numero de serie del dispositivo
	 * @return coleccion dto con todos los perifericos del dispositivo y todas sus opciones
	 */
	public Collection<PerifericoDTO> dispositvo(int numserie) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = EConnection.getConnection();
			String sql = "";
			sql += "SELECT * FROM periferico WHERE numserie=?";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, numserie);
			rs = pstm.executeQuery();
			PerifericoDTO dto = null;
			Vector<PerifericoDTO> ret = new Vector<PerifericoDTO>();
			while (rs.next()) {
				dto=new PerifericoDTO();
				dto.setNumserie(rs.getInt("numserie"));
				dto.setPosicion(rs.getInt("posicion"));
				dto.setBooleano(rs.getBoolean("booleano"));
				dto.setEscribible(rs.getBoolean("escribible"));
				dto.setPicMax(rs.getInt("picmax"));
				dto.setPicMin(rs.getInt("picmin"));
				dto.setRealMax(rs.getInt("realmax"));
				dto.setRealMin(rs.getInt("realmin"));
				dto.setNombreperi(rs.getString("nombreperi"));
				ret.add(dto);
			}
			return ret;

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		

		
	}

}
