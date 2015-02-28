package com.rapplogic.xbee.util;
/**
 * Clase con funciones de escalado para diferentes resoluciones
 * @author Lucas Alvarez Argüero
 *
 */
public class Escalado {
	/**
	 * Devuelve una variable en un rango de definicion diferente
	 * @param maxIn maximo escala entrada
	 * @param minIn minimo escala entrada
	 * @param maxOut maximo escala salida
	 * @param minOut minimo escala salida
	 * @param val variable a escalar
	 * @return variable escalada
	 */
	public static int esc(int maxIn, int minIn, int maxOut,int minOut, int val){
		
		int out=(Math.abs(maxOut-minOut)*(val-minIn)/Math.abs(maxIn-minIn))+minOut;
		
		
		
		return out;
		
	
	
	
	}
	
}
