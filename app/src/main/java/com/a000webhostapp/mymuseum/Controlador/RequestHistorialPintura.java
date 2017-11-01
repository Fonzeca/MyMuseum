package com.a000webhostapp.mymuseum.Controlador;

import com.a000webhostapp.mymuseum.Modelo.Pintura;

/**
 * Created by Alexis on 1/11/2017.
 */

public class RequestHistorialPintura extends Request {
	private Pintura p;
	
	public RequestHistorialPintura(int id, Pintura p) {
		super(id);
		this.p = p;
	}
	
	public Pintura getPintura() {
		return p;
	}
}
