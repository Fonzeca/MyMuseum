package com.a000webhostapp.mymuseum.Modelo;

import java.io.Serializable;

/**
 * Created by Alexis on 19/9/2017.
 */

public class Invento extends Objeto{
    private Inventor inventor;
    private boolean isMaquina;
    private final int id;
    

    public Invento(String nombre, String descripcion, Periodo periodo, Inventor inventor, int añoInvencion, boolean isMaquina, int id) {
		super(nombre,descripcion,periodo,añoInvencion);
        this.inventor = inventor;
        this.isMaquina = isMaquina;
        this.id = id;
    }
    public Invento(String nombre, String descripcion, Periodo periodo, Inventor inventor, int añoInvencion, boolean isMaquina) {
		this(nombre,descripcion,periodo,inventor,añoInvencion,isMaquina,-1);
    }

    public String configGuardar() {
        String accion = "accion=nuevo_invento";
        String nombreConfig= "nombre="+nombre;
        String descriConfig= "descripcion="+descripcion;
        String periConfig= "nombre_periodo="+periodo.getNombrePeriodo();
        String inventorConfig= "nombre_inventor="+inventor.getNombre();
        String añoConfig= "anio="+añoInvencion;
        String isMaquinaConfig= isMaquina?"es_maquina=1":"es_maquina=0";

        return accion + "&" + nombreConfig + "&" + añoConfig + "&" + descriConfig +
                "&" + isMaquinaConfig + "&" + periConfig + "&" + inventorConfig;
    }
	
	public String configModificar() {
		String accion = "accion=editar_registro";
		String entidad = "entidad=Invento";
		String idConfig = "registro_id="+id;
		String nombreConfig= "nombre="+nombre;
		String descriConfig= "descripcion="+descripcion;
		String periConfig= "nombre_periodo="+periodo.getNombrePeriodo();
		String inventorConfig= "nombre_inventor="+inventor.getNombre();
		String añoConfig= "anio="+añoInvencion;
		String isMaquinaConfig= isMaquina?"es_maquina=1":"es_maquina=0";
		
		return accion + "&" + entidad + "&" + idConfig + "&" + nombreConfig + "&" + añoConfig +
				"&" + descriConfig + "&" + isMaquinaConfig + "&" + periConfig + "&" + inventorConfig;
	}
	
	
	//GETTER & SETTER

    public Inventor getInventor() {
        return inventor;
    }

    public void setInventor(Inventor inventor) {
        this.inventor = inventor;
    }

    public boolean isMaquina() {
        return isMaquina;
    }

    public void setMaquina(boolean maquina) {
        isMaquina = maquina;
    }
    
	public int getID() {
		return id;
	}
}
