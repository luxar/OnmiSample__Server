package com.rapplogic.xbee.connectionLayer.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.rapplogic.xbee.connectionLayer.connectors.IConnection;

public class PerifericoLocalDAO {

	/**
	 * Permite saber si la dir(unica) ya esta registrada en la DB local
	 * 
	 * @param dir
	 *            array con la direcion de consulta
	 * @return Verdadero si esta registrada falso si no lo esta.
	 */
	private  boolean registrado(int dir[]) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = IConnection.getConnection();
			String sql = "";
			sql += "SELECT posicion FROM perifericos WHERE dir1=? and dir2=? and dir3=? and dir4=? and dir5=? and dir6=?";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, dir[0]);
			pstm.setInt(2, dir[1]);
			pstm.setInt(3, dir[2]);
			pstm.setInt(4, dir[3]);
			pstm.setInt(5, dir[4]);
			pstm.setInt(6, dir[5]);
			rs = pstm.executeQuery();
			if (rs.next()) {
				return true;

			} else
				return false;

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}

		// SELECT posicion FROM perifericos WHERE dir1=0 and dir2=1 and dir3=1
		// and dir4=1 and dir5=1 and dir6=1
	}
}