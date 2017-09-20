package com.a000webhostapp.mymuseum.Entidades;

import android.support.v7.app.AppCompatActivity;

import com.a000webhostapp.mymuseum.DAO.ControlDB;

/**
 * Created by Alexis on 19/9/2017.
 * ESTA CLASE ES PARA MANEJAR LAS ENTIDADES Y MANDARLAS A LA BD(BASE DE DATOS)
 */

public class ModuloEntidad {
    private static ModuloEntidad me;
    private Inventor[] inventoresBuscados;
    private AppCompatActivity ac;


    private ModuloEntidad(){

    }
    public static ModuloEntidad obtenerModulo(){

        if(me == null){
            me = new ModuloEntidad();
        }
        return me;
    }

    public void crearInvento(){

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


    public void crearPeriodo(){

    }
    public Periodo obtenerPeriodo(){

        return null;
    }


    public Inventor[] getInventoresBuscados() {
        return inventoresBuscados;
    }

    public void setInventoresBuscados(Inventor[] inventoresBuscados) {
        this.inventoresBuscados = inventoresBuscados;
    }



}
