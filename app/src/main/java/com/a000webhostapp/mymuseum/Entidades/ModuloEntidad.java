package com.a000webhostapp.mymuseum.Entidades;

import com.a000webhostapp.mymuseum.DAO.ControlDB;


/**
 * Created by Alexis on 19/9/2017.
 * ESTA CLASE ES PARA MANEJAR LAS ENTIDADES Y MANDARLAS A LA BD(BASE DE DATOS)
 */

public class ModuloEntidad {
    private static ModuloEntidad me;
    private Inventor[] inventoresBuscados;
    private Periodo[] periodosBuscados;


    private ModuloEntidad(){

    }
    public static ModuloEntidad obtenerModulo(){

        if(me == null){
            me = new ModuloEntidad();
        }
        return me;
    }

    public void crearInvento(String nombre, String descripcion, Periodo periodo, Inventor inventor, int añoInvencion, boolean isMaquina){
        Invento invento = new Invento(nombre,descripcion,periodo,inventor,añoInvencion,isMaquina);
        new ControlDB(this).insertar(invento);
    }

    public Invento obtenerInvento(){

        return null;
    }


    public void crearInventor(String nombrecompleto, String lugarNacimiento, int añoNacimiento){
        Inventor inventor = new Inventor(nombrecompleto,lugarNacimiento,añoNacimiento);
        new ControlDB(this).insertar(inventor);
    }

    public void buscarInventores(){
        //mandamos a buscar los inventores
        new ControlDB(this).buscar("Inventor");
    }
    public Inventor[] obtenerInventores(){
        //Tratamos de obtener los resultados de la busqueda
        //Notese que eliminamos el resultado una vez ya se lo pasamos a alguien
        Inventor[] r = inventoresBuscados;
        inventoresBuscados = null;
        return r;
    }


    public void crearPeriodo(String nombre, int añoInicio, int añoFin){
        Periodo peri = new Periodo(nombre, añoInicio,añoFin);
        new ControlDB(this).insertar(peri);

    }
    public void buscarPeriodos(){
        //mandamos a buscar los periodos
        new ControlDB(this).buscar("Periodo");
    }
    public Periodo[] obtenerPeriodos(){
        //Tratamos de obtener los resultados de la busqueda
        //Notese que eliminamos el resultado una vez ya se lo pasamos a alguien
        Periodo[] r = periodosBuscados;
        periodosBuscados = null;
        return r;
    }


    public Inventor[] getInventoresBuscados() {
        return inventoresBuscados;
    }

    public void setInventoresBuscados(Inventor[] inventoresBuscados) {
        this.inventoresBuscados = inventoresBuscados;
    }

    public Periodo[] getPeriodosBuscados() {
        return periodosBuscados;
    }

    public void setPeriodosBuscados(Periodo[] periodosBuscados) {
        this.periodosBuscados = periodosBuscados;
    }
}
