package com.rapplogic.xbee.connectionLayer.DTO;

public class PerifericoLocalDTO {
	private int dir[];
	private int posicion[];
	private int valReal;
	private boolean valbool;
	private int realMax;
	private int realMin;
	private int picMax;
	private int picMin;
	private boolean booleano;
	private boolean escribible;
	private String nombreperi;
	public int[] getDir() {
		return dir;
	}
	public void setDir(int[] dir) {
		this.dir = dir;
	}
	public int[] getPosicion() {
		return posicion;
	}
	public void setPosicion(int[] posicion) {
		this.posicion = posicion;
	}
	public int getValReal() {
		return valReal;
	}
	public void setValReal(int valReal) {
		this.valReal = valReal;
	}
	public boolean isValbool() {
		return valbool;
	}
	public void setValbool(boolean valbool) {
		this.valbool = valbool;
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
