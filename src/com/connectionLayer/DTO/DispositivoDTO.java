package com.connectionLayer.DTO;
/**
 * permite crear un objeto para transportar información de dispositivos estándar de la base de datos externa.
 * @author Lucas Alvarez Arguero
 *
 */
public class DispositivoDTO {
	private int numserie;
	private String fabricante;
	private String nombre;
	public int getNumserie() {
		return numserie;
	}
	public void setNumserie(int numserie) {
		this.numserie = numserie;
	}
	public String getFabricante() {
		return fabricante;
	}
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
