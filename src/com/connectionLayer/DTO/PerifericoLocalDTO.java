package com.connectionLayer.DTO;

public class PerifericoLocalDTO {
	private int dir[];
	private int posicion[];
	private int valReal;
	private boolean valbool;
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

	
}
