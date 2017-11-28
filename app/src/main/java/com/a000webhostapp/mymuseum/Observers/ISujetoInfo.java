package com.a000webhostapp.mymuseum.Observers;

import com.a000webhostapp.mymuseum.Controlador.Request;
import com.a000webhostapp.mymuseum.Modelo.Guardable;

/**
 * Created by Alexis on 27/11/2017.
 */

public interface ISujetoInfo {
	void registrarObserver(IObserverInfo ob, Request request);
	boolean eliminarObserver(IObserverInfo ob);
	void notificarObserver(Request request, String[] data, String respuesta);
}
