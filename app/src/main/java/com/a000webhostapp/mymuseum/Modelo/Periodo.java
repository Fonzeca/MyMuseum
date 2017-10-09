package com.a000webhostapp.mymuseum.Modelo;

import java.io.Serializable;

/**
 * Created by Alexis on 19/9/2017.
 */

public class Periodo implements Guardable, Serializable {
    private String nombrePeriodo;

    private int añoInicio, añoFin;
    private int id;

    public Periodo(){
        
    }

    public Periodo(String nombrePeriodo, int añoInicio, int añoFin, int id) {
        this.nombrePeriodo = nombrePeriodo;
        this.añoInicio = añoInicio;
        this.añoFin = añoFin;
        this. id = id;
    }
    public Periodo(String nombrePeriodo, int añoInicio, int añoFin) {
        this(nombrePeriodo, añoInicio,añoFin, -1);
    }


    public String configGuardar() {
        String accion = "accion=nuevo_periodo";
        String nombre = "nombre="+nombrePeriodo;
        String añoIn = "anio_inicio="+añoInicio;
        String añoFi = "anio_fin="+añoFin;
        return accion + "&" + nombre + "&" + añoIn + "&" + añoFi;
    }
    
    public String configModificar() {
        String accion = "accion=editar_registro";
        String entidad = "entidad=Periodo";
        String idModifica = "registro_id="+id;
        String nombre = "nombre="+nombrePeriodo;
        String añoIn = "anio_inicio="+añoInicio;
        String añoFi = "anio_fin="+añoFin;
        
        return accion + "&" + entidad + "&" + idModifica + "&" + nombre + "&" + añoIn + "&" + añoFi;
    }
    
    @Override
    public String toString() {
        return nombrePeriodo;
    }
    
    //GETTER & SETTER
    public String getNombrePeriodo() {
        return nombrePeriodo;
    }

    public void setNombrePeriodo(String nombrePeriodo) {
        this.nombrePeriodo = nombrePeriodo;
    }

    public int getAñoInicio() {
        return añoInicio;
    }

    public void setAñoInicio(int añoInicio) {
        this.añoInicio = añoInicio;
    }
	
	public int getID() {
		return id;
	}
    
    public int getAñoFin() {
        return añoFin;
    }

    public void setAñoFin(int añoFin) {
        this.añoFin = añoFin;
    }
}
