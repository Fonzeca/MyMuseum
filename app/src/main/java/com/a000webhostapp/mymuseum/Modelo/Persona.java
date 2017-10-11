package com.a000webhostapp.mymuseum.Modelo;

import java.io.Serializable;

/**
 * Created by Erika Romina on 9/10/2017.
 */

public abstract class Persona implements Guardable, Serializable {
    protected String nombre;
    protected int añoNacimiento;
	protected String lugarNacimiento;
	
	public Persona(String nombre, int añoNacimiento, String lugarNacimiento) {
		this.nombre = nombre;
		this.añoNacimiento = añoNacimiento;
		this.lugarNacimiento = lugarNacimiento;
	}
	
	public String toString() {
        return nombre;
    }
	
    public String getNombre() {
        return nombre;
    }
	
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
	
    public int getAñoNacimiento() {
        return añoNacimiento;
    }
	
    public void setAñoNacimiento(int añoNacimiento) {
        this.añoNacimiento = añoNacimiento;
    }
	
	public String getLugarNacimiento() {
		return lugarNacimiento;
	}
	
	public void setLugarNacimiento(String lugarNacimiento) {
		this.lugarNacimiento = lugarNacimiento;
	}

}
