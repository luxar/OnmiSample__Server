package com.connectionLayer.util;

/**
 * Clase con funciones de escalado para diferentes resoluciones
 * 
 * @author Lucas Alvarez Arg�ero
 *
 */
public class Escalado {
	/**
	 * Devuelve una variable en un rango de definici�n diferente respecto a
	 * otros rangos, esto se usa para adaptar una medida real, ala que necesita
	 * un pic, como por ejemplo un 0~100% a una medida del pic de 0~255 por ser
	 * de 8 bits
	 * 
	 * @param maxIn
	 *            maximo escala entrada
	 * @param minIn
	 *            minimo escala entrada
	 * @param maxOut
	 *            maximo escala salida
	 * @param minOut
	 *            minimo escala salida
	 * @param val
	 *            variable a escalar
	 * @return variable escalada
	 */
	public static int esc(int maxIn, int minIn, int maxOut, int minOut, int val) {

		int out = (Math.abs(maxOut - minOut) * (val - minIn) / Math.abs(maxIn
				- minIn))
				+ minOut;

		return out;

	}

}
