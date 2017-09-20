package com.a000webhostapp.mymuseum.Entidades;

import com.a000webhostapp.mymuseum.Guardable;

import java.io.Serializable;

/**
 * Created by Alexis on 19/9/2017.
 */

public class Invento implements Serializable{
    private String nombre, descripcion;
    private Periodo periodo;
    private Inventor inventor;
    private int añoInvencion;
    private boolean isMaquina;

    public Invento(String nombre, String descripcion, Periodo periodo, Inventor inventor, int añoInvencion, boolean isMaquina) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.periodo = periodo;
        this.inventor = inventor;
        this.añoInvencion = añoInvencion;
        this.isMaquina = isMaquina;
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
}
