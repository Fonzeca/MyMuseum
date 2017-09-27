package com.a000webhostapp.mymuseum.Entidades;

import com.a000webhostapp.mymuseum.Guardable;

import java.io.Serializable;

/**
 * Created by Alexis on 19/9/2017.
 */

public class Periodo implements Guardable, Serializable {
    private String nombrePeriodo;

    private int añoInicio, añoFin;

    public Periodo(){

    }

    public Periodo(String nombrePeriodo, int añoInicio, int añoFin) {
        this.nombrePeriodo = nombrePeriodo;
        this.añoInicio = añoInicio;
        this.añoFin = añoFin;
    }


    public String configGuardar() {
        String accion = "accion=nuevo_periodo";
        String nombre = "nombre="+nombrePeriodo;
        String añoIn = "anio_inicio="+añoInicio;
        String añoFi = "anio_fin="+añoFin;
        return accion + "&" + nombre + "&" + añoIn + "&" + añoFi;
    }
    
    public String configModificar() {
        return null;
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

    public int getAñoFin() {
        return añoFin;
    }

    public void setAñoFin(int añoFin) {
        this.añoFin = añoFin;
    }
}
