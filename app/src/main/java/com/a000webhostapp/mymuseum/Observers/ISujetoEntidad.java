package com.a000webhostapp.mymuseum.Observers;

import com.a000webhostapp.mymuseum.Controlador.Request;
import com.a000webhostapp.mymuseum.Modelo.Guardable;

/**
 * Created by Erika Romina on 20/9/2017.
 */

public interface ISujetoEntidad {
    void registrarObserver(IObserverEntidad ob, Request request);
    boolean eliminarObserver(IObserverEntidad ob);
    void notificarObserver(Request request, Guardable[] g, String respuesta);
	
}
