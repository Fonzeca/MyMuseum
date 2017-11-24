package com.a000webhostapp.mymuseum;

/**
 * Created by Alexis on 7/11/2017.
 */

public class Constantes {
	private static boolean ADMIN = false;
	public static void obtenerPermisos(){
		ADMIN = true;
	}
	public static void denegarPermisos(){
		ADMIN = false;
	}
	public static boolean getADMIN(){
		return ADMIN;
	}
}
