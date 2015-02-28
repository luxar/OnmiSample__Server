package com.rapplogic.xbee.util;

public class Escalado {
	public static int esc(int maxIn, int minIn, int maxOut,int minOut, int val){
		
		int out=(Math.abs(maxOut-minOut)*(val-minIn)/Math.abs(maxIn-minIn))+minOut;
		
		
		
		return out;
		
	
	
	
	}
	
}
