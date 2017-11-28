package com.a000webhostapp.mymuseum.Controlador;

import com.a000webhostapp.mymuseum.Modelo.Pintura;

/**
 * Created by Alexis on 1/11/2017.
 */

public class RequestHistorialPintura extends Request {
	private Pintura pintura;
	
	public RequestHistorialPintura(int id, Pintura p) {
		super(id);
		this.pintura = p;
	}
	
	public Pintura getPintura() {
		return pintura;
	}
}
