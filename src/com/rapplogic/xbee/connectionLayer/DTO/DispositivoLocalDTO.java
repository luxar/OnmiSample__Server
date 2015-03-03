package com.rapplogic.xbee.connectionLayer.DTO;

public class DispositivoLocalDTO {
	private int dir[];
	private int numserie;
	private String nombre;
	private boolean activo;

	public int[] getDir() {
		return dir;
	}

	public void setDir(int[] dir) {
		this.dir = dir;
	}

	public int getNumserie() {
		return numserie;
	}

	public void setNumserie(int numserie) {
		this.numserie = numserie;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}
