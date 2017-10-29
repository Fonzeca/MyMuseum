package com.a000webhostapp.mymuseum.Modelo;


import com.a000webhostapp.mymuseum.DAO.ControlDB;

import org.json.JSONException;
import org.json.JSONObject;

public class Pintor extends Persona{
    private int id;
    
    public Pintor(String nombre, String lugarNacimiento, int añoNacimiento, int id) {
        super(nombre,añoNacimiento,lugarNacimiento);
        this.id = id;
    }
    
    public Pintor(String nombreCompleto, String lugarNacimiento, int añoNacimiento) {
        this(nombreCompleto, lugarNacimiento,añoNacimiento,-1);
    }


    public String configGuardar() {
        String accion = "accion=nueva_persona";
        String entidad = "entidad=" + ControlDB.str_per_Pintor;
        String nom = "nombre="+ nombre;
        String año = "anio_nacimiento="+añoNacimiento;
        String lugar = "lugar_nacimiento="+lugarNacimiento;

        return accion + "&" + entidad + "&" + nom + "&" + año + "&" + lugar;
    }
    
    public String configModificar() {
        String accion = "accion=editar_registro";
        String entidad = "entidad=" + ControlDB.str_persona;
        String idModifica = "registro_id=" + id;
        String nom = "nombre="+ nombre;
        String año = "anio_nacimiento="+añoNacimiento;
        String lugar = "lugar_nacimiento="+lugarNacimiento;
    
        return accion + "&" + entidad + "&" + idModifica + "&" + nom + "&" + año + "&" + lugar;
    }
    
    public static Pintor obtenerPintorJSON(JSONObject obJSON)throws JSONException{
		
		String nom = obJSON.getString("nombre");
		int idConfig = obJSON.getInt("persona_id");
		int año = obJSON.getInt("año_nacimiento");
		String lugar = obJSON.getString("lugar_nacimiento");
		
		return new Pintor(nom,lugar,año, idConfig);
	}
    
    
    //GETTERS & SETTERS
	public int getID() {
		return id;
	}
}
