package com.rapplogic.xbee.connectionLayer.connectors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.rapplogic.xbee.api.ApiId;
import com.rapplogic.xbee.api.PacketListener;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.zigbee.ZNetRxResponse;
import com.rapplogic.xbee.api.zigbee.ZNetTxRequest;
import com.rapplogic.xbee.api.zigbee.ZNetRxBaseResponse.Option;
import com.rapplogic.xbee.connectionLayer.DAO.PerifericoLocalDAO;
import com.rapplogic.xbee.connectionLayer.DTO.DispositivoDTO;
import com.rapplogic.xbee.connectionLayer.DTO.PerifericoDTO;
import com.rapplogic.xbee.util.ByteUtils;
import com.rapplogic.xbee.util.DoubleByte;

public class ZConnection {
	private static XBee xbee = null;
	private final static Logger log = Logger.getLogger(ZConnection.class);

	private static boolean registrado(int dir[]) {
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
	
	private static  Collection<PerifericoDTO> dispositvo(int numserie) {
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
				ret.add(dto);
			}
			return ret;

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		

		// SELECT posicion FROM perifericos WHERE dir1=0 and dir2=1 and dir3=1
		// and dir4=1 and dir5=1 and dir6=1
	}
	private static void solicitarNumseries() {
		try {
		int[] payload = ByteUtils.stringToIntArray("Q");
		
		ZNetTxRequest request = new ZNetTxRequest(XBeeAddress64.BROADCAST, payload);
		// make it a broadcast packet
		request.setOption(ZNetTxRequest.Option.BROADCAST);

		log.info("Se envia paquete de pregunta en broadcast a todos.");
		
		
			xbee.sendAsynchronous(request);
		} catch (XBeeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// we just assume it was sent.  that's just the way it is with broadcast.  
		// no transmit status response is sent, so don't bother calling getResponse()
	}
	
	private static void anhadirADB(Collection<PerifericoDTO> perifericoDTO,int dir[]){
		Connection con = null;
		Connection conE = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			
			conE = EConnection.getConnection();
			PerifericoDTO[] perifericos = (PerifericoDTO[]) perifericoDTO.toArray(new PerifericoDTO[0]);
			String sql = "";
			String nombre= "";
			sql = "SELECT nombre FROM dispositivo WHERE numserie=?";
			pstm = conE.prepareStatement(sql);
			pstm.setInt(1,perifericos[0].getNumserie());
			rs = pstm.executeQuery();
			if (rs.next()) {
				nombre=rs.getString("nombre");
			
			}
			con = IConnection.getConnection();
			sql = "INSERT INTO dispositivos (dir1, dir2 , dir3 , dir4 , dir5 , dir6 ,  numserie, activo, nombre  ) VALUES( ? , ? , ? , ? , ? , ? , ? , ?, ? )";
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
			pstm.executeUpdate();
			
		for(int i=0;i<perifericos.length;i++){
		sql = "INSERT INTO perifericos (dir1, dir2 , dir3 , dir4 , dir5 , dir6 , posicion , booleano , escribible , realmax, realmin, picmax , picmin ) VALUES(?,?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
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
		pstm.setInt(10,perifericos[i].getRealMax());
		pstm.setInt(11,perifericos[i].getRealMin());
		pstm.setInt(12,perifericos[i].getPicMax());		
		pstm.setInt(13,perifericos[i].getPicMin());

		pstm.executeUpdate();
		
		}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	
	public static XBee getConnection() {

		try {
			if (xbee == null) {
				Runtime.getRuntime().addShutdownHook(new MiShDwnHookZ());
				ResourceBundle rb = ResourceBundle.getBundle("xbee");
				String puerto = rb.getString("port");
				xbee = new XBee();
				xbee.open(puerto, 9600);

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
									+ ByteUtils.toBase16(rx
											.getRemoteAddress64().getAddress())
									+ ", remote 16-bit address is "
									+ ByteUtils.toBase16(rx
											.getRemoteAddress16().getAddress())
									+ ", data is "
									+ ByteUtils.toBase16(rx.getData()));
							log.info("obteniendo datos:");
							Option opcion = rx.getOption();
							if (opcion.name().equals("BROADCAST_PACKET")) {
								log.info("mensaje de broadcast no usable");
							} else if (opcion.name().equals(
									"PACKET_ACKNOWLEDGED")) {
								int[] direcion = rx.getRemoteAddress64()
										.getAddress();
								int[] datos = rx.getData();
								
								if (datos[0] == 0x51) {
									int Numseriebits[] = { datos[1], datos[2],
											datos[3], datos[4] };
									log.info("lectura del numero de serie: "
											+ ByteUtils
													.convertMultiByteToInt(Numseriebits));
									int Numserie=ByteUtils.convertMultiByteToInt(Numseriebits);
									// infotmacion de dispositivo
									PerifericoLocalDAO perifericoLocalDAO = new PerifericoLocalDAO();
									int dir[] = rx.getRemoteAddress64().getAddress();
									boolean existe = registrado(dir);
									if (existe) {
										log.info("Existe");
									} else {
										log.info("NO Existe");
										Collection<PerifericoDTO> perifericoDTO=dispositvo( Numserie);
										log.info("periferico con numero de serie "+Numserie+" que tiene "+perifericoDTO.size()+" puertos");
										log.info("añadiendo a base interna de datos");
										anhadirADB(perifericoDTO,dir);
									}
									
									
								} else if (datos[0] == 0x52) {

									log.info("Lectura de un sensor");

									log.info("Sensor numero: " + datos[1]);
									DoubleByte valor2Byte = new DoubleByte(
											datos[2], datos[3]);
									int valor = valor2Byte.get16BitValue();
									log.info("Valor: " + valor);

									// lectura
								} else {
									log.info("Comando no valido");
									// ni idea
								}
							}

						} else if (response.getApiId() == ApiId.ZNET_IO_NODE_IDENTIFIER_RESPONSE) {
							
							log.info("Se ha conectado un nuevo dispositivo a la red");
							solicitarNumseries();

							
						} else {

							log.debug("received unexpected packet "
									+ response.toString());
						}
					}
				});
			}
			return xbee;

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("Error al acceder a la base de datos");
		}

	}
}

class MiShDwnHookZ extends Thread {
	public void run() {
		try {
			XBee con = ZConnection.getConnection();
			con.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

}