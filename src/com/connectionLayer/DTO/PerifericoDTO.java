package com.connectionLayer.DTO;
/**
 * permite crear un objeto para transportar información de los puertos de un dispositivo estándar de la base de datos externa.
 * @author Lucas Alvarez Arguero
 *
 */
public class PerifericoDTO {
	private int numserie;
	private int posicion;
	private int realMax;
	private int realMin;
	private int picMax;
	private int picMin;
	private boolean booleano;
	private boolean escribible;
	private String nombreperi;
	public int getNumserie() {
		return numserie;
	}
	public void setNumserie(int numserie) {
		this.numserie = numserie;
	}
	public int getPosicion() {
		return posicion;
	}
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
	public int getRealMax() {
		return realMax;
	}
	public void setRealMax(int realMax) {
		this.realMax = realMax;
	}
	public int getRealMin() {
		return realMin;
	}
	public void setRealMin(int realMin) {
		this.realMin = realMin;
	}
	public int getPicMax() {
		return picMax;
	}
	public void setPicMax(int picMax) {
		this.picMax = picMax;
	}
	public int getPicMin() {
		return picMin;
	}
	public void setPicMin(int picMin) {
		this.picMin = picMin;
	}
	public boolean isBooleano() {
		return booleano;
	}
	public void setBooleano(boolean booleano) {
		this.booleano = booleano;
	}
	public boolean isEscribible() {
		return escribible;
	}
	public void setEscribible(boolean escribible) {
		this.escribible = escribible;
	}
	public String getNombreperi() {
		return nombreperi;
	}
	public void setNombreperi(String nombreperi) {
		this.nombreperi = nombreperi;
	}
	
}
