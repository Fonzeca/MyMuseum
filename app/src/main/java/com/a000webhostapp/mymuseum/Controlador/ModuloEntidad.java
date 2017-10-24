package com.a000webhostapp.mymuseum.Controlador;

import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.IObserver;
import com.a000webhostapp.mymuseum.Modelo.Invento;
import com.a000webhostapp.mymuseum.Modelo.Inventor;
import com.a000webhostapp.mymuseum.Modelo.Periodo;


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
    
    //---------------
    public void crearInvento(String nombre, String descripcion, Periodo periodo, Inventor inventor, int añoInvencion, boolean isMaquina){
        Invento invento = new Invento(nombre,descripcion,periodo,inventor,añoInvencion,isMaquina);
        new ControlDB(null).insertar(invento);
    }
    public void buscarInventos(IObserver observer){
        //Mandamos a buscar los Inventos
		new ControlDB(observer).buscar("Invento");
    }
    public void editarInvento(Guardable g){
        new ControlDB(null).modificar(g);
    }
	public void eliminarInvento(int id){
		String entidad = "entidad=Invento";
		String idBorra = "registro_id=" + id;
		new ControlDB(null).borrar("accion=eliminar_registro" + "&" + entidad + "&" + idBorra);
	}
    
    //---------------
    public void crearInventor(String nombrecompleto, String lugarNacimiento, int añoNacimiento){
        Inventor inventor = new Inventor(nombrecompleto,lugarNacimiento,añoNacimiento);
        new ControlDB(null).insertar(inventor);
    }
    public void buscarInventores(IObserver observer){
        //mandamos a buscar los inventores
        new ControlDB(observer).buscar("Inventor");
    }
    public void editarInventor(Guardable g){
        new ControlDB(null).modificar(g);
    }
	public void eliminarInventor(int id){
		String entidad = "entidad=Inventor";
		String idBorra = "registro_id=" + id;
		new ControlDB(null).borrar("accion=eliminar_registro" + "&" + entidad + "&" + idBorra);
	}

    //---------------
    public void crearPeriodo(String nombre, int añoInicio, int añoFin){
        Periodo peri = new Periodo(nombre, añoInicio,añoFin);
        new ControlDB(null).insertar(peri);

    }
    public void buscarPeriodos(IObserver ob){
        //mandamos a buscar los periodos
        new ControlDB(ob).buscar("Periodo");
    }
    public void editarPeriodo(Guardable g){
        new ControlDB(null).modificar(g);
    }
    public void eliminarPeriodo(int id){
		String entidad = "entidad=Periodo";
		String idBorra = "registro_id=" + id;
		new ControlDB(null).borrar("accion=eliminar_registro" + "&" + entidad + "&" + idBorra);
	}
}
