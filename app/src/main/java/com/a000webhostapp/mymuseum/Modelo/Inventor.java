package com.a000webhostapp.mymuseum.Modelo;



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
        String accion = "accion=nuevo_inventor";
        String nom = "nombre="+ nombre;
        String año = "anio_nacimiento="+añoNacimiento;
        String lugar = "lugar_nacimiento="+lugarNacimiento;

        return accion + "&" + nom + "&" + año + "&" + lugar;
    }
    
    public String configModificar() {
        String accion = "accion=editar_registro";
        String entidad = "entidad=Inventor";
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
