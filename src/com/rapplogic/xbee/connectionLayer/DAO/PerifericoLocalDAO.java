package com.rapplogic.xbee.connectionLayer.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.connectionLayer.DTO.*;
import com.rapplogic.xbee.connectionLayer.connectors.EConnection;
import com.rapplogic.xbee.connectionLayer.connectors.IConnection;
import com.rapplogic.xbee.connectionLayer.connectors.XConnection;
import com.rapplogic.xbee.connectionLayer.funcCom.FuncEnvio;
import com.rapplogic.xbee.util.Escalado;

/**
 * Funciones que engloban la mayoria de operacionescon la base de datos interna
 * 
 * @author Lucas Alvarez Argüero
 *
 */
public class PerifericoLocalDAO {

	/**
	 * Permite saber si la dir(unica) ya esta registrada en la DB local
	 * 
	 * @param dir
	 *            array con la direcion de consulta
	 * @return Verdadero si esta registrada falso si no lo esta.
	 */
	public boolean registrado(int dir[]) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = IConnection.getConnection();
			String sql = "";
			sql += "SELECT posicion FROM perifericos WHERE dir1=? and dir2=? and dir3=? and dir4=? and dir5=? and dir6=? and dir7=? and dir8=?";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, dir[0]);
			pstm.setInt(2, dir[1]);
			pstm.setInt(3, dir[2]);
			pstm.setInt(4, dir[3]);
			pstm.setInt(5, dir[4]);
			pstm.setInt(6, dir[5]);
			pstm.setInt(7, dir[6]);
			pstm.setInt(8, dir[7]);
			rs = pstm.executeQuery();
			if (rs.next()) {
				return true;

			} else
				return false;

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}

	}

	/**
	 * AÑade a la base de datos (tanto en dispositivo como en todos sus
	 * perifericos) el perifericoDTO (internamente busca nombre del periferico
	 * en la base de datos ecxterna)
	 * 
	 * @param perifericoDTO
	 *            coleccion de los perifercos
	 * @param direccion
	 *            del dispositivo
	 */
	public void anhadirADB(Collection<PerifericoDTO> perifericoDTO, int dir[]) {
		Connection con = null;
		Connection conE = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {

			conE = EConnection.getConnection();
			PerifericoDTO[] perifericos = (PerifericoDTO[]) perifericoDTO
					.toArray(new PerifericoDTO[0]);
			String sql = "";
			String nombre = "";
			sql = "SELECT nombre FROM dispositivo WHERE numserie=?";
			pstm = conE.prepareStatement(sql);
			pstm.setInt(1, perifericos[0].getNumserie());
			rs = pstm.executeQuery();
			if (rs.next()) {
				nombre = rs.getString("nombre");

			}
			con = IConnection.getConnection();
			sql = "INSERT INTO dispositivos (dir1, dir2 , dir3 , dir4 , dir5 , dir6 ,  numserie, activo, nombre , dir7 , dir8  ) VALUES( ? , ? , ? , ? , ? , ? , ? , ?, ?, ?, ? )";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, dir[0]);
			pstm.setInt(2, dir[1]);
			pstm.setInt(3, dir[2]);
			pstm.setInt(4, dir[3]);
			pstm.setInt(5, dir[4]);
			pstm.setInt(6, dir[5]);
			pstm.setInt(7, perifericos[0].getNumserie());
			pstm.setBoolean(8, true);
			pstm.setString(9, nombre);
			pstm.setInt(10, dir[6]);
			pstm.setInt(11, dir[7]);
			pstm.executeUpdate();

			for (int i = 0; i < perifericos.length; i++) {
				sql = "INSERT INTO perifericos (dir1, dir2 , dir3 , dir4 , dir5 , dir6 , posicion , booleano , escribible , realmax, realmin, picmax , picmin, dir7 , dir8, nombreperi ) VALUES(?,?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ? )";
				pstm = con.prepareStatement(sql);
				pstm.setInt(1, dir[0]);
				pstm.setInt(2, dir[1]);
				pstm.setInt(3, dir[2]);
				pstm.setInt(4, dir[3]);
				pstm.setInt(5, dir[4]);
				pstm.setInt(6, dir[5]);
				pstm.setInt(7, perifericos[i].getPosicion());
				pstm.setBoolean(8, perifericos[i].isBooleano());
				pstm.setBoolean(9, perifericos[i].isEscribible());
				pstm.setInt(10, perifericos[i].getRealMax());
				pstm.setInt(11, perifericos[i].getRealMin());
				pstm.setInt(12, perifericos[i].getPicMax());
				pstm.setInt(13, perifericos[i].getPicMin());
				pstm.setInt(14, dir[6]);
				pstm.setInt(15, dir[7]);
				pstm.setString(16, perifericos[i].getNombreperi());
				pstm.executeUpdate();

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Dado un puerto devuelve todos los perifericos que tengan ese puerto
	 * 
	 * @param puerto
	 *            puerto oposicion de numero que se encuentra ese periferico en
	 *            el dispositivo
	 * @return colecion periferico localDTO pero solo tiene informacion de
	 *         puertos
	 */
	public Collection<PerifericoLocalDTO> perifericosPorPuerto(int puerto) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = IConnection.getConnection();
			String sql = "";
			sql += "SELECT * FROM  perifericos  t1 INNER JOIN dispositivos t2 ON (t1.dir8=t2.dir8 and t1.dir7=t2.dir7 and t1.dir6=t2.dir6 and t1.dir5=t2.dir5  and t1.dir4=t2.dir4  and t1.dir3=t2.dir3  and t1.dir2=t2.dir2  and t1.dir1=t2.dir1)   WHERE t2.activo=1 and t1.posicion=?";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, puerto);
			rs = pstm.executeQuery();
			PerifericoLocalDTO dto = null;
			Vector<PerifericoLocalDTO> ret = new Vector<PerifericoLocalDTO>();
			while (rs.next()) {
				dto = new PerifericoLocalDTO();
				int dir[] = { 0, 0, 0, 0, 0, 0, 0, 0 };
				dir[0] = rs.getInt("dir1");
				dir[1] = rs.getInt("dir2");
				dir[2] = rs.getInt("dir3");
				dir[3] = rs.getInt("dir4");
				dir[4] = rs.getInt("dir5");
				dir[5] = rs.getInt("dir6");
				dir[6] = rs.getInt("dir7");
				dir[7] = rs.getInt("dir8");
				dto.setDir(dir);

				ret.add(dto);
			}
			return ret;

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}

	};

	/**
	 * Sube un valor a la base d datos local
	 * 
	 * @param valor
	 *            valor a subir (en el caso de booleano 0= false y true
	 *            cualquier otro numero)
	 * @param dir
	 *            [] array con la direccion del dispositivo
	 * @param pos
	 *            posicion del periferico en el dispositivo
	 */
	public void recogerDatos(int valor, int dir[], int pos) {

		// TODO control de datos
		Connection con = null;
		PreparedStatement pstm = null;
		PreparedStatement pstm2 = null;
		ResultSet rs = null;
		int realMax = 0;
		int realMin = 0;
		int picMax = 0;
		int picMin = 0;
		boolean booleano = false;
		try {
			con = IConnection.getConnection();
			String sql = "";
			sql += "SELECT * FROM perifericos WHERE posicion=? AND  dir1=? AND dir2=? AND dir3=? AND dir4=? AND dir5=? AND dir6=? AND dir7=? AND dir8=?";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, pos);
			pstm.setInt(2, dir[0]);
			pstm.setInt(3, dir[1]);
			pstm.setInt(4, dir[2]);
			pstm.setInt(5, dir[3]);
			pstm.setInt(6, dir[4]);
			pstm.setInt(7, dir[5]);
			pstm.setInt(8, dir[6]);
			pstm.setInt(9, dir[7]);
			rs = pstm.executeQuery();
			if (rs.next()) {
				realMax = rs.getInt("realmax");
				realMin = rs.getInt("realmin");
				picMax = rs.getInt("picmax");
				picMin = rs.getInt("picmin");
				booleano = rs.getBoolean("booleano");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}

		if (booleano == true) {

			try {
				String sql = "";
				if (valor == 0) {

					sql += "UPDATE perifericos SET valbool=0 WHERE  posicion=? AND dir1=? AND dir2=? AND dir3=? AND dir4=? AND dir5=? AND dir6=? AND dir7=? AND dir8=?";

				} else {

					sql += "UPDATE perifericos SET valbool=1 WHERE  posicion=? AND dir1=? AND dir2=? AND dir3=? AND dir4=? AND dir5=? AND dir6=? AND dir7=? AND dir8=? ";

				}
				pstm = con.prepareStatement(sql);
				pstm.setInt(1, pos);
				pstm.setInt(2, dir[0]);
				pstm.setInt(3, dir[1]);
				pstm.setInt(4, dir[2]);
				pstm.setInt(5, dir[3]);
				pstm.setInt(6, dir[4]);
				pstm.setInt(7, dir[5]);
				pstm.setInt(8, dir[6]);
				pstm.setInt(9, dir[7]);
				pstm.executeUpdate();

			} catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}

		} else {
			try {

				String sql = "";
				sql += "UPDATE perifericos SET valreal=? WHERE  dir1=? AND dir2=? AND dir3=? AND dir4=? AND dir5=? AND dir6=? AND dir7=? AND dir8=? AND posicion=?";
				pstm2 = con.prepareStatement(sql);
				int valreal = Escalado.esc(picMax, picMin, realMax, realMin,
						valor);
				pstm2.setInt(1, valreal);
				pstm2.setInt(2, dir[0]);
				pstm2.setInt(3, dir[1]);
				pstm2.setInt(4, dir[2]);
				pstm2.setInt(5, dir[3]);
				pstm2.setInt(6, dir[4]);
				pstm2.setInt(7, dir[5]);
				pstm2.setInt(8, dir[6]);
				pstm2.setInt(9, dir[7]);
				pstm2.setInt(10, pos);
				pstm2.executeUpdate();

			} catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}

		}
	}

	/**
	 * Envia un valor al dispositivo a traves de la red xbee ademas lo sube a la
	 * base de datos local
	 * 
	 * @param pos
	 *            posicion del periferico
	 * @param dir
	 *            array con la direccion del periferco
	 * @param valor
	 *            valor numerico
	 */
	public void enviarValor(int pos, int dir[], int valor) {
		// TODO añadir control para no meter un bool en un int y si es
		// escribible
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int realMax = 0;
		int realMin = 0;
		int picMax = 0;
		int picMin = 0;
		boolean booleano = false;
		boolean escribible = false;
		try {
			con = IConnection.getConnection();
			String sql = "";
			sql += "SELECT * FROM perifericos WHERE posicion=? AND  dir1=? AND dir2=? AND dir3=? AND dir4=? AND dir5=? AND dir6=? AND dir7=? AND dir8=?";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, pos);
			pstm.setInt(2, dir[0]);
			pstm.setInt(3, dir[1]);
			pstm.setInt(4, dir[2]);
			pstm.setInt(5, dir[3]);
			pstm.setInt(6, dir[4]);
			pstm.setInt(7, dir[5]);
			pstm.setInt(8, dir[6]);
			pstm.setInt(9, dir[7]);
			rs = pstm.executeQuery();
			if (rs.next()) {
				realMax = rs.getInt("realmax");
				realMin = rs.getInt("realmin");
				picMax = rs.getInt("picmax");
				picMin = rs.getInt("picmin");
				booleano = rs.getBoolean("booleano");
				escribible = rs.getBoolean("escribible");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}

		try {

			String sql = "";
			sql += "UPDATE perifericos SET valreal=? WHERE  dir1=? AND dir2=? AND dir3=? AND dir4=? AND dir5=? AND dir6=? AND dir7=? AND dir8=? AND posicion=?";
			pstm = con.prepareStatement(sql);

			pstm.setInt(1, valor);
			pstm.setInt(2, dir[0]);
			pstm.setInt(3, dir[1]);
			pstm.setInt(4, dir[2]);
			pstm.setInt(5, dir[3]);
			pstm.setInt(6, dir[4]);
			pstm.setInt(7, dir[5]);
			pstm.setInt(8, dir[6]);
			pstm.setInt(9, dir[7]);
			pstm.setInt(10, pos);
			pstm.executeUpdate();

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		int valPic = Escalado.esc(realMax, realMin, picMax, picMin, valor);
		XBee xbee = XConnection.getConnection();
		FuncEnvio.enviarEscritura(xbee, valPic, dir, pos);

	}

	/**
	 * Envia un valor al dispositivo a traves de la red xbee ademas lo sube a la
	 * base de datos local
	 * 
	 * @param posicion
	 *            posicion del periferico
	 * @param dir
	 *            array con la direccion del periferco
	 * @param valor
	 *            valor Booleano
	 */
	public void enviarValor(int pos, int dir[], boolean valor) {
		// TODO añadir control para no meter un bool en un int
		// TODO añadir control para no meter un bool en un int y si es
		// escribible
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int realMax = 0;
		int realMin = 0;
		int picMax = 0;
		int picMin = 0;
		boolean booleano = false;
		boolean escribible = false;
		try {
			con = IConnection.getConnection();
			String sql = "";
			sql += "SELECT * FROM perifericos WHERE posicion=? AND  dir1=? AND dir2=? AND dir3=? AND dir4=? AND dir5=? AND dir6=? AND dir7=? AND dir8=?";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, pos);
			pstm.setInt(2, dir[0]);
			pstm.setInt(3, dir[1]);
			pstm.setInt(4, dir[2]);
			pstm.setInt(5, dir[3]);
			pstm.setInt(6, dir[4]);
			pstm.setInt(7, dir[5]);
			pstm.setInt(8, dir[6]);
			pstm.setInt(9, dir[7]);
			rs = pstm.executeQuery();
			if (rs.next()) {
				realMax = rs.getInt("realmax");
				realMin = rs.getInt("realmin");
				picMax = rs.getInt("picmax");
				picMin = rs.getInt("picmin");
				booleano = rs.getBoolean("booleano");
				escribible = rs.getBoolean("escribible");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		try {
			String sql = "";
			if (valor == false) {

				sql += "UPDATE perifericos SET valbool=0 WHERE  posicion=? AND dir1=? AND dir2=? AND dir3=? AND dir4=? AND dir5=? AND dir6=? AND dir7=? AND dir8=?";

			} else {

				sql += "UPDATE perifericos SET valbool=1 WHERE  posicion=? AND dir1=? AND dir2=? AND dir3=? AND dir4=? AND dir5=? AND dir6=? AND dir7=? AND dir8=? ";

			}
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, pos);
			pstm.setInt(2, dir[0]);
			pstm.setInt(3, dir[1]);
			pstm.setInt(4, dir[2]);
			pstm.setInt(5, dir[3]);
			pstm.setInt(6, dir[4]);
			pstm.setInt(7, dir[5]);
			pstm.setInt(8, dir[6]);
			pstm.setInt(9, dir[7]);
			pstm.executeUpdate();

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		XBee xbee = XConnection.getConnection();
		if (valor == false) {

			FuncEnvio.enviarEscritura(xbee, 0, dir, pos);

		} else {
			FuncEnvio.enviarEscritura(xbee, 1, dir, pos);

		}

	}

	/**
	 * Establece como inactivo el dispositivo
	 * 
	 * @param dir
	 *            direccion del dispositivo
	 */
	public void ponerInactivo(int dir[]) {

		Connection con = null;
		PreparedStatement pstm = null;
		con = IConnection.getConnection();
		String sql = "";
		sql += "UPDATE dispositivos SET activo=0 WHERE   dir1=? AND dir2=? AND dir3=? AND dir4=? AND dir5=? AND dir6=? AND dir7=? AND dir8=?";
		try {
			pstm = con.prepareStatement(sql);

			pstm.setInt(1, dir[0]);
			pstm.setInt(2, dir[1]);
			pstm.setInt(3, dir[2]);
			pstm.setInt(4, dir[3]);
			pstm.setInt(5, dir[4]);
			pstm.setInt(6, dir[5]);
			pstm.setInt(7, dir[6]);
			pstm.setInt(8, dir[7]);
			pstm.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}

	}

	/**
	 * Establece como activo el dispositivo
	 * 
	 * @param dir
	 *            direccion del dispositivo
	 */
	public void ponerActivo(int dir[]) {

		Connection con = null;
		PreparedStatement pstm = null;
		String sql = "";
		sql += "UPDATE dispositivos SET activo=1 WHERE   dir1=? AND dir2=? AND dir3=? AND dir4=? AND dir5=? AND dir6=? AND dir7=? AND dir8=?";
		con = IConnection.getConnection();
		try {
			pstm = con.prepareStatement(sql);

			pstm.setInt(1, dir[0]);
			pstm.setInt(2, dir[1]);
			pstm.setInt(3, dir[2]);
			pstm.setInt(4, dir[3]);
			pstm.setInt(5, dir[4]);
			pstm.setInt(6, dir[5]);
			pstm.setInt(7, dir[6]);
			pstm.setInt(8, dir[7]);
			pstm.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * Dada una direcion devuelve la coleccion de perifericos
	 * 
	 * @param dir
	 *            direccion solicitada (8 campos)
	 * @return coleccion de perifericos
	 */
	public Collection<PerifericoLocalDTO> perifericosPorDirecion(int dir[]) {

		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			con = IConnection.getConnection();
			String sql = "";
			sql += "SELECT * FROM perifericos WHERE  dir1=? AND dir2=? AND dir3=? AND dir4=? AND dir5=? AND dir6=? AND dir7=? AND dir8=?";
			pstm = con.prepareStatement(sql);

			pstm.setInt(1, dir[0]);
			pstm.setInt(2, dir[1]);
			pstm.setInt(3, dir[2]);
			pstm.setInt(4, dir[3]);
			pstm.setInt(5, dir[4]);
			pstm.setInt(6, dir[5]);
			pstm.setInt(7, dir[6]);
			pstm.setInt(8, dir[7]);
			rs = pstm.executeQuery();

			PerifericoLocalDTO dto = null;
			Vector<PerifericoLocalDTO> ret = new Vector<PerifericoLocalDTO>();
			while (rs.next()) {
				dto = new PerifericoLocalDTO();
				dto.setDir(dir);
				dto.setPosicion(rs.getInt("posicion"));
				dto.setBooleano(rs.getBoolean("booleano"));
				dto.setEscribible(rs.getBoolean("escribible"));
				dto.setPicMax(rs.getInt("picmax"));
				dto.setPicMin(rs.getInt("picmin"));
				dto.setRealMax(rs.getInt("realmax"));
				dto.setRealMin(rs.getInt("realmin"));
				dto.setNombreperi(rs.getString("nombreperi"));
				dto.setValbool(rs.getBoolean("valbool"));
				dto.setValReal(rs.getInt("valreal"));
				dto.setNombreperi(rs.getString("nombreperi"));
				ret.add(dto);
			}
			return ret;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
/**
 * Devuelve colecion de todos los dispositivos que hay en la red
 * @return
 */
	public Collection<DispositivoLocalDTO> todosDispositivosLocales() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			con = IConnection.getConnection();
			String sql = "";
			sql += "SELECT * FROM dispositivos";
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();

			DispositivoLocalDTO dto = null;
			Vector<DispositivoLocalDTO> ret = new Vector<DispositivoLocalDTO>();
			while (rs.next()) {
				dto = new DispositivoLocalDTO();
				int dir[]={rs.getInt("dir1"),rs.getInt("dir2"),rs.getInt("dir3"),rs.getInt("dir4"),rs.getInt("dir5"),rs.getInt("dir6"),rs.getInt("dir7"),rs.getInt("dir8")};
				dto.setDir(dir);
				dto.setNombre(rs.getString("nombre"));
				dto.setActivo(rs.getBoolean("activo"));
				dto.setNumserie(rs.getInt("numserie"));
				ret.add(dto);
			}
			return ret;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
}