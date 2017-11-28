package com.a000webhostapp.mymuseum.Observers;

import com.a000webhostapp.mymuseum.Modelo.Guardable;

/**
 * Created by Erika Romina on 20/9/2017.
 */

public interface IObserverEntidad {
    void update(Guardable[] g,int request, String respuesta);
}
