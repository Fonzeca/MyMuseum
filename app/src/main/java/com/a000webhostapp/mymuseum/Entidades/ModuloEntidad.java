package com.a000webhostapp.mymuseum.Entidades;

import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.IObserver;


/**
 * Created by Alexis on 19/9/2017.
 * ESTA CLASE ES PARA MANEJAR LAS ENTIDADES Y MANDARLAS A LA BD(BASE DE DATOS)
 */

public class ModuloEntidad {
    private static ModuloEntidad me;

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
        new ControlDB(null).insertar(invento);
    }

    public void buscarInventos(IObserver observer){
        //Mandamos a buscar los Inventos
        new ControlDB(observer).buscar("Invento");
    }

    public void editarInvento(){
        System.out.println("EDITANDO EL INVENTO LALALALALA");
    }


    public void crearInventor(String nombrecompleto, String lugarNacimiento, int añoNacimiento){
        Inventor inventor = new Inventor(nombrecompleto,lugarNacimiento,añoNacimiento);
        new ControlDB(null).insertar(inventor);
    }

    public void buscarInventores(IObserver observer){
        //mandamos a buscar los inventores
        new ControlDB(observer).buscar("Inventor");
    }

    public void editarInventor(){
        System.out.println("EDITANDO EL INVENTOr J3J3J3J3J3");
    }


    public void crearPeriodo(String nombre, int añoInicio, int añoFin){
        Periodo peri = new Periodo(nombre, añoInicio,añoFin);
        new ControlDB(null).insertar(peri);

    }
    public void buscarPeriodos(IObserver ob){
        //mandamos a buscar los periodos
        new ControlDB(ob).buscar("Periodo");
    }
    public void editarPeriodo(){
        System.out.println("EDITANDO EL PERIODO Xd");
    }
}
