package com.a000webhostapp.mymuseum.Controlador;

import com.a000webhostapp.mymuseum.Modelo.Inventor;
import com.a000webhostapp.mymuseum.Modelo.Periodo;

/**
 * Created by Alexis on 29/10/2017.
 */

public class RequestBusqueda extends Request {
	private String nombre,descripcion;
	private Periodo periodo;
	private Inventor inventor;
	private int año;
	
	public RequestBusqueda(int id, String nombre, String descripcion, Periodo periodo, Inventor inventor, int año) {
		super(id);
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.periodo = periodo;
		this.inventor = inventor;
		this.año = año;
	}
	public RequestBusqueda(int id, String nombre){
		this(id,nombre,null,null,null,0);
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
	
	public Inventor getInventor() {
		return inventor;
	}
	
	public void setInventor(Inventor inventor) {
		this.inventor = inventor;
	}
	
	public int getAño() {
		return año;
	}
	
	public void setAño(int año) {
		this.año = año;
	}
}
