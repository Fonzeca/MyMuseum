package com.a000webhostapp.mymuseum.Controlador;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.DAO.ControlFTP;
import com.a000webhostapp.mymuseum.Observers.IObserverEntidad;
import com.a000webhostapp.mymuseum.Observers.ISujetoEntidad;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.Modelo.Imagen;

import java.util.ArrayList;

/**
 * Created by Alexis on 5/11/2017.
 */

public class ModuloImagen implements ISujetoEntidad {
	private ArrayList<IObserverEntidad> observers;
	private ArrayList<Request> requests;
	private static ModuloImagen mi;
	
	public static final int RQS_BUSQUEDA_IMAGEN_UNICA = 1000;
	
	public static final int RQS_INSERTAR_IMAGEN = 1100;
	public static final int RQS_CAMBIAR_NOMBRE_IMAGEN = 1101;
	
	private ModuloImagen(){
		observers = new ArrayList<IObserverEntidad>();
		requests = new ArrayList<Request>();
	}
	
	public static ModuloImagen obtenerModulo(){
		if(mi == null){
			mi = new ModuloImagen();
		}
		return mi;
	}
	public void buscarImagen(String nombre, String entidad, IObserverEntidad observer){
		RequestImagen ri = new RequestImagen(RQS_BUSQUEDA_IMAGEN_UNICA,nombre,entidad);
		registrarObserver(observer,ri);
		new ControlFTP(ri).buscar();
	}
	public void insertarImagen(String nombre, String entidad,Context context,Uri uri, IObserverEntidad observer){
		RequestImagen ri = new RequestImagen(RQS_INSERTAR_IMAGEN, context,nombre,entidad);
		registrarObserver(observer,ri);
		new ControlFTP(ri).insertar(uri);
	}
	public void cambiarNombre(String nombreAnterior, String nombreActual){
		Request request = new Request(RQS_CAMBIAR_NOMBRE_IMAGEN);
		registrarObserver(null, request);
		new ControlFTP(null).cambiarNombre(nombreAnterior,nombreActual);
	}
	public void registrarObserver(IObserverEntidad ob, Request request) {
		observers.add(ob);
		requests.add(request);
	}
	public boolean eliminarObserver(IObserverEntidad ob) {
		requests.remove(observers.indexOf(ob));
		observers.remove(ob);
		return true;
	}
	public void notificarObserver(Request request, Guardable[] g, String respuesta) {
		for(int i = 0; i < requests.size(); i++){
			if(request.equals(requests.get(i))){
				observers.get(i).update(g,request.getId(),respuesta);
			}
		}
	}
	public void resultadoControlFTP(Request request, String respuesta, Bitmap g){
		switch (respuesta){
			case "Exito":
				switch (request.getId()){
					case RQS_BUSQUEDA_IMAGEN_UNICA:
						//La cagaste
						Imagen im = null;
						Guardable[] gs = new Guardable[1];
						if(g != null){
							im = new Imagen(g);
							gs[0] = im;
						}
						notificarObserver(request,gs,respuesta);
						break;
				}
				break;
		}
	}
}
