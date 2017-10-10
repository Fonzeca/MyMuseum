package com.a000webhostapp.mymuseum.Modelo;


import java.io.Serializable;

public class Inventor implements Guardable, Serializable{
    private String nombreCompleto, lugarNacimiento;
    private int añoNacimiento;  //En caso de ser A.C. debera ser negativo
    private int id;

    public Inventor(){

    }
    public Inventor(String nombreCompleto, String lugarNacimiento, int añoNacimiento, int id) {
        this.nombreCompleto = nombreCompleto;
        this.lugarNacimiento = lugarNacimiento;
        this.añoNacimiento = añoNacimiento;
        this.id = id;
    }
    
    public Inventor(String nombreCompleto, String lugarNacimiento, int añoNacimiento) {
        this(nombreCompleto, lugarNacimiento,añoNacimiento,-1);
    }


    public String configGuardar() {
        String accion = "accion=nuevo_inventor";
        String nom = "nombreCompleto="+ nombreCompleto;
        String año = "anio_nacimiento="+añoNacimiento;
        String lugar = "lugar_nacimiento="+lugarNacimiento;

        return accion + "&" + nom + "&" + año + "&" + lugar;
    }
    
    public String configModificar() {
        String accion = "accion=editar_registro";
        String entidad = "entidad=Inventor";
        String idModifica = "registro_id=" + id;
        String nom = "nombreCompleto="+ nombreCompleto;
        String año = "anio_nacimiento="+añoNacimiento;
        String lugar = "lugar_nacimiento="+lugarNacimiento;
    
        return accion + "&" + entidad + "&" + idModifica + "&" + nom + "&" + año + "&" + lugar;
    }
    
    @Override
    public String toString() {
        return nombreCompleto;
    }
    
    //GETTERS & SETTERS
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }


    public String getLugarNacimiento() {
        return lugarNacimiento;
    }

    public void setLugarNacimiento(String lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
    }
	public int getID() {
		return id;
	}
    
    public int getAñoNacimiento() {
        return añoNacimiento;
    }

    public void setAñoNacimiento(int añoNacimiento) {
        this.añoNacimiento = añoNacimiento;
    }
}
