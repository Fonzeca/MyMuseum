package com.a000webhostapp.mymuseum.DAO;


import com.a000webhostapp.mymuseum.Guardable;

import java.security.Guard;

public interface DAOInterface {
    boolean insertar(Guardable g);
    boolean borrar();
    boolean modificar(Guardable g);
    void buscar(String entidad);

}
