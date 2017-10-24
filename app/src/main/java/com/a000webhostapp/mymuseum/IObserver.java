package com.a000webhostapp.mymuseum;

import com.a000webhostapp.mymuseum.Modelo.Guardable;

/**
 * Created by Erika Romina on 20/9/2017.
 */

public interface IObserver {
    void update(Guardable[] g, String respuesta);
}
