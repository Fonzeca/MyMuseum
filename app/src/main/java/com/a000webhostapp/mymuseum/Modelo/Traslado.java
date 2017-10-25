package com.a000webhostapp.mymuseum.Modelo;

import java.io.Serializable;

/**
 * Created by Alexis on 25/10/2017.
 */

public class Traslado implements Serializable {
	private Pintura pintura;
	private int id;
	
	private String lugarOrigen, lugarDestino, fechaTraslado;
	
	public Traslado(Pintura pintura, String lugarOrigen, String lugarDestino, String fechaTraslado, int id){
		this.pintura = pintura;
		this.lugarOrigen = lugarOrigen;
		this.lugarDestino = lugarDestino;
		this.fechaTraslado = fechaTraslado;
		this.id = id;
	}
	public Traslado(Pintura pintura, String lugarOrigen, String lugarDestino, String fechaTraslado){
		this(pintura,lugarOrigen, lugarDestino, fechaTraslado, -1);
	}
	
	
	//GETTERS & SETTERS
	public Pintura getPintura() {
		return pintura;
	}
	
	public void setPintura(Pintura pintura) {
		this.pintura = pintura;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getLugarOrigen() {
		return lugarOrigen;
	}
	
	public void setLugarOrigen(String lugarOrigen) {
		this.lugarOrigen = lugarOrigen;
	}
	
	public String getLugarDestino() {
		return lugarDestino;
	}
	
	public void setLugarDestino(String lugarDestino) {
		this.lugarDestino = lugarDestino;
	}
	
	public String getFechaTraslado() {
		return fechaTraslado;
	}
	
	public void setFechaTraslado(String fechaTraslado) {
		this.fechaTraslado = fechaTraslado;
	}
}
