package com.a000webhostapp.mymuseum.Modelo;


import com.a000webhostapp.mymuseum.DAO.ControlDB;

public class Inventor extends Persona{
    private int id;
    
    public Inventor(String nombre, String lugarNacimiento, int añoNacimiento, int id) {
        super(nombre,añoNacimiento,lugarNacimiento);
        this.id = id;
    }
    
    public Inventor(String nombreCompleto, String lugarNacimiento, int añoNacimiento) {
        this(nombreCompleto, lugarNacimiento,añoNacimiento,-1);
    }


    public String configGuardar() {
        String accion = "accion=nueva_persona";
        String entidad = "entidad=" + ControlDB.str_per_Inventor;
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
    
    
    //GETTERS & SETTERS
	public int getID() {
		return id;
	}
}
