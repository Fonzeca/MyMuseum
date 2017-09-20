package com.a000webhostapp.mymuseum.Entidades;


import com.a000webhostapp.mymuseum.Guardable;

import java.io.Serializable;

public class Inventor implements Guardable, Serializable{
    private String nombreCompleto, lugarNacimiento;
    private int añoNacimiento;  //En caso de ser A.C. debera ser negativo

    public Inventor(){

    }

    public Inventor(String nombreCompleto, String lugarNacimiento, int añoNacimiento) {
        this.nombreCompleto = nombreCompleto;
        this.lugarNacimiento = lugarNacimiento;
        this.añoNacimiento = añoNacimiento;
    }


    public String configGuardar() {
        String accion = "accion=nuevo_inventor";
        String nom = "nombreCompleto="+ nombreCompleto;
        String año = "anio_nacimiento="+añoNacimiento;
        String lugar = "lugar_nacimiento="+lugarNacimiento;

        return accion + "&" + nom + "&" + año + "&" + lugar;
    }


    //GETTERS & SETTERS
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }


    public String getLugarNacimiento() {
        return lugarNacimiento;
    }

    public void setLugarNacimiento(String lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
    }

    public int getAñoNacimiento() {
        return añoNacimiento;
    }

    public void setAñoNacimiento(int añoNacimiento) {
        this.añoNacimiento = añoNacimiento;
    }
}
