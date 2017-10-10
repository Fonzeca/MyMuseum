package com.a000webhostapp.mymuseum.Modelo;

/**
 * Created by Erika Romina on 9/10/2017.
 */

public abstract class Persona {
    protected String nombreCompleto;
    protected int añoNacimiento;

    public String toString() {
        return nombreCompleto;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public int getAñoNacimiento() {
        return añoNacimiento;
    }

    public void setAñoNacimiento(int añoNacimiento) {
        this.añoNacimiento = añoNacimiento;
    }

}
