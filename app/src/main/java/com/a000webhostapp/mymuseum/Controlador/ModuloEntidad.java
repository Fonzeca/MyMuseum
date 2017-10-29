package com.a000webhostapp.mymuseum.Controlador;

import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.ISujeto;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.IObserver;
import com.a000webhostapp.mymuseum.Modelo.Invento;
import com.a000webhostapp.mymuseum.Modelo.Inventor;
import com.a000webhostapp.mymuseum.Modelo.Periodo;
import com.a000webhostapp.mymuseum.Modelo.Pintor;
import com.a000webhostapp.mymuseum.Modelo.Pintura;

import java.util.ArrayList;


/**
 * Created by Alexis on 19/9/2017.
 * ESTA CLASE ES PARA MANEJAR LAS ENTIDADES Y MANDARLAS A LA BD(BASE DE DATOS)
 */

public class ModuloEntidad {
	private ArrayList<IObserver> observers;
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
		new ControlDB(observer).buscar(ControlDB.str_obj_Invento);
    }
	public void buscarInventoDirecto(IObserver observer, String nombre){
		//Mandamos a buscar los Inventos
		new ControlDB(observer).buscarDirecto(ControlDB.str_obj_Invento,nombre);
	}
    public void editarInvento(Guardable g){
        new ControlDB(null).modificar(g);
    }
	public void eliminarInvento(int id){
		String entidad = "entidad=" + ControlDB.str_objeto;
		String idBorra = "registro_id=" + id;
		new ControlDB(null).borrar("accion=eliminar_registro" + "&" + entidad + "&" + idBorra);
	}
    //---------------
	public void crearPintura(String nombre, String descripcion, Periodo periodo, Pintor pintor, int añoInvencion){
		Pintura pintura = new Pintura(nombre,descripcion,periodo,pintor,añoInvencion);
		new ControlDB(null).insertar(pintura);
	}
	public void buscarPinturas(IObserver observer){
		new ControlDB(observer).buscar(ControlDB.str_obj_Pintura);
	}
	public void buscarPinturaDirecto(IObserver observer, String nombre){
		new ControlDB(observer).buscarDirecto(ControlDB.str_obj_Pintura,nombre);
	}
	public void eliminarPintura(int id){
		String entidad = "entidad="+ControlDB.str_objeto;
		String idBorra = "registro_id=" + id;
		new ControlDB(null).borrar("accion=eliminar_registro" + "&" + entidad + "&" + idBorra);
	}
	public void editarPintura(Guardable g){
		new ControlDB(null).modificar(g);
	}
    //---------------
	public void crearPintor(String nombre, String lugarNacimiento, int añoNacimiento){
		Pintor pintor = new Pintor(nombre,lugarNacimiento,añoNacimiento);
		new ControlDB(null).insertar(pintor);
	}
	public void buscarPintores(IObserver observer){
		new ControlDB(observer).buscar(ControlDB.str_per_Pintor);
	}
	public void eliminarPintor(int id){
		String entidad = "entidad="+ControlDB.str_persona;
		String idBorra = "registro_id=" + id;
		new ControlDB(null).borrar("accion=eliminar_registro" + "&" + entidad + "&" + idBorra);
	}
	public void editarPintor(Guardable g){
		new ControlDB(null).modificar(g);
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
		String entidad = "entidad=" + ControlDB.str_persona;
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
	//---------------
	public void buscarObjetos(IObserver observer){
		new ControlDB(observer).buscar(ControlDB.str_objeto);
	}
	//---------------
	
}
