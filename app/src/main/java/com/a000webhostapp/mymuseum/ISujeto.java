package com.a000webhostapp.mymuseum;

import com.a000webhostapp.mymuseum.Modelo.Guardable;

/**
 * Created by Erika Romina on 20/9/2017.
 */

public interface ISujeto {
    void registrarObvserver(IObserver ob);
    boolean eliminarObvserver(IObserver ob);
    void notificarObsverver(Guardable[] g, int id);
	
	
	
}
