package com.a000webhostapp.mymuseum.Modelo;



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
        String accion = "accion=nuevo_pintor";
        String nom = "nombre="+ nombre;
        String año = "anio_nacimiento="+añoNacimiento;
        String lugar = "lugar_nacimiento="+lugarNacimiento;

        return accion + "&" + nom + "&" + año + "&" + lugar;
    }
    
    public String configModificar() {
        String accion = "accion=editar_registro";
        String entidad = "entidad=Pintor";
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
