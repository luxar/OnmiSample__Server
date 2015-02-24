package com.connectionLayer.DTO;

public class PerifericoDTO {
	private int numserie;
	private int posicion;
	private int realMax;
	private int realMin;
	private int picMax;
	private int picMin;
	private boolean booleano;
	private boolean escribible;
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
	
}
