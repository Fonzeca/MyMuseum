package com.a000webhostapp.mymuseum;

import com.a000webhostapp.mymuseum.Controlador.Request;
import com.a000webhostapp.mymuseum.Modelo.Guardable;

/**
 * Created by Erika Romina on 20/9/2017.
 */

public interface ISujeto {
    void registrarObserver(IObserver ob, Request request);
    boolean eliminarObserver(IObserver ob);
    void notificarObserver(Request request, Guardable[] g, String respuesta);
	
}
