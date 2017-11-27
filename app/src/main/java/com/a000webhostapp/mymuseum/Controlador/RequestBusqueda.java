package com.a000webhostapp.mymuseum.Controlador;

import com.a000webhostapp.mymuseum.Modelo.Inventor;
import com.a000webhostapp.mymuseum.Modelo.Periodo;
import com.a000webhostapp.mymuseum.Modelo.Pintor;

import java.io.Serializable;

/**
 * Created by Alexis on 29/10/2017.
 */

public class RequestBusqueda extends Request{
	private String nombre;
	private Periodo periodo;
	private Inventor inventor;
	private Pintor pintor;
	
	public RequestBusqueda(int id, String nombre, Periodo periodo, Inventor inventor) {
		super(id);
		this.nombre = nombre;
		this.periodo = periodo;
		this.inventor = inventor;
	}
	public RequestBusqueda(int id, String nombre, Periodo periodo, Pintor pintor) {
		super(id);
		this.nombre = nombre;
		this.periodo = periodo;
		this.pintor = pintor;
	}
	public RequestBusqueda(int id, String nombre){
		this(id,nombre,null, (Inventor) null);
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	
	public Pintor getPintor() {
		return pintor;
	}
	
	public void setPintor(Pintor pintor) {
		this.pintor = pintor;
	}
}
