package com.a000webhostapp.mymuseum.Modelo;

import java.io.Serializable;

/**
 * Created by Alexis on 10/10/2017.
 */

public abstract class Objeto implements Guardable {
	protected String nombre, descripcion;
	protected Periodo periodo;
	protected int añoInvencion;
	
	public Objeto(String nombre, String descripcion, Periodo periodo, int añoInvencion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.periodo = periodo;
		this.añoInvencion = añoInvencion;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Periodo getPeriodo() {
		return periodo;
	}
	
	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}
	
	public int getAñoInvencion() {
		return añoInvencion;
	}
	
	public void setAñoInvencion(int añoInvencion) {
		this.añoInvencion = añoInvencion;
	}
}
