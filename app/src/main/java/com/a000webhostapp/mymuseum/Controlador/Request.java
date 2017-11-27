package com.a000webhostapp.mymuseum.Controlador;

import java.io.Serializable;

/**
 * Created by Alexis on 29/10/2017.
 */

public class Request implements Serializable {
	protected int id;
	public Request(int id){
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
