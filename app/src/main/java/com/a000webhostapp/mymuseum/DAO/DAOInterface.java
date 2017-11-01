package com.a000webhostapp.mymuseum.DAO;


import com.a000webhostapp.mymuseum.Modelo.Guardable;

public interface DAOInterface {
    boolean insertar(Guardable g);
    boolean borrar(String parametro);
    boolean modificar(Guardable g);
    void buscar(String entidad);
    void buscarDirecto(String entidad, String nombre);

}
