package com.a000webhostapp.mymuseum.Entidades;

import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.IObserver;


/**
 * Created by Alexis on 19/9/2017.
 * ESTA CLASE ES PARA MANEJAR LAS ENTIDADES Y MANDARLAS A LA BD(BASE DE DATOS)
 */

public class ModuloEntidad {
    private static ModuloEntidad me;

    public ModuloEntidad(){

    }
    public static ModuloEntidad obtenerModulo(){

        if(me == null){
            me = new ModuloEntidad();
        }
        return me;
    }

    public void crearInvento(String nombre, String descripcion, Periodo periodo, Inventor inventor, int añoInvencion, boolean isMaquina){
        Invento invento = new Invento(nombre,descripcion,periodo,inventor,añoInvencion,isMaquina);
        new ControlDB(this, null).insertar(invento);
    }

    public void buscarInventos(IObserver observer){
        new ControlDB(this, observer).buscar("Invento");
    }


    public void crearInventor(String nombrecompleto, String lugarNacimiento, int añoNacimiento){
        Inventor inventor = new Inventor(nombrecompleto,lugarNacimiento,añoNacimiento);
        new ControlDB(this, null).insertar(inventor);
    }

    public void buscarInventores(IObserver observer){
        //mandamos a buscar los inventores
        new ControlDB(this,observer).buscar("Inventor");
    }


    public void crearPeriodo(String nombre, int añoInicio, int añoFin){
        Periodo peri = new Periodo(nombre, añoInicio,añoFin);
        new ControlDB(this, null).insertar(peri);

    }
    public void buscarPeriodos(IObserver ob){
        //mandamos a buscar los periodos
        new ControlDB(this, ob).buscar("Periodo");
    }
}
