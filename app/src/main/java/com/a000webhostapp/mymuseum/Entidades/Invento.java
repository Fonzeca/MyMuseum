package com.a000webhostapp.mymuseum.Entidades;

import com.a000webhostapp.mymuseum.Guardable;

import java.io.Serializable;
import java.security.Guard;

/**
 * Created by Alexis on 19/9/2017.
 */

public class Invento implements Guardable, Serializable{
    private String nombre, descripcion;
    private Periodo periodo;
    private Inventor inventor;
    private int añoInvencion;
    private boolean isMaquina;
    private final int id;
    

    public Invento(String nombre, String descripcion, Periodo periodo, Inventor inventor, int añoInvencion, boolean isMaquina, int id) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.periodo = periodo;
        this.inventor = inventor;
        this.añoInvencion = añoInvencion;
        this.isMaquina = isMaquina;
        this.id = id;
    }
    public Invento(String nombre, String descripcion, Periodo periodo, Inventor inventor, int añoInvencion, boolean isMaquina) {
		this(nombre,descripcion,periodo,inventor,añoInvencion,isMaquina,-1);
    }

    public String configGuardar() {
        String accion = "accion=nuevo_invento";
        String nombreConfig= "nombre="+nombre;
        String descriConfig= "descripcion="+descripcion;
        String periConfig= "nombre_periodo="+periodo.getNombrePeriodo();
        String inventorConfig= "nombre_inventor="+inventor.getNombreCompleto();
        String añoConfig= "anio="+añoInvencion;
        String isMaquinaConfig= isMaquina?"es_maquina=1":"es_maquina=0";

        return accion + "&" + nombreConfig + "&" + añoConfig + "&" + descriConfig +
                "&" + isMaquinaConfig + "&" + periConfig + "&" + inventorConfig;
    }
	
	public String configModificar() {
		String accion = "accion=editar_registro";
		String entidad = "entidad=Invento";
		String idConfig = "registro_id="+id;
		String nombreConfig= "nombre="+nombre;
		String descriConfig= "descripcion="+descripcion;
		String periConfig= "nombre_periodo="+periodo.getNombrePeriodo();
		String inventorConfig= "nombre_inventor="+inventor.getNombreCompleto();
		String añoConfig= "anio="+añoInvencion;
		String isMaquinaConfig= isMaquina?"es_maquina=1":"es_maquina=0";
		
		return accion + "&" + entidad + "&" + idConfig + "&" + nombreConfig + "&" + añoConfig +
				"&" + descriConfig + "&" + isMaquinaConfig + "&" + periConfig + "&" + inventorConfig;
	}
	
	//GETTER & SETTER
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

    public int getAñoInvencion() {
        return añoInvencion;
    }

    public void setAñoInvencion(int añoInvencion) {
        this.añoInvencion = añoInvencion;
    }

    public boolean isMaquina() {
        return isMaquina;
    }

    public void setMaquina(boolean maquina) {
        isMaquina = maquina;
    }
    
	public int getID() {
		return id;
	}
}
