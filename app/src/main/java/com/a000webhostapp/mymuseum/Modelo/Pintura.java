package com.a000webhostapp.mymuseum.Modelo;

import com.a000webhostapp.mymuseum.DAO.ControlDB;

import java.util.ArrayList;

/**
 * Created by Alexis on 19/9/2017.
 */

public class Pintura extends Objeto{
    private Pintor pintor;
	private ArrayList<Traslado> traslados;
    private final int id;
    

    public Pintura(String nombre, String descripcion, Periodo periodo, Pintor pintor, int añoInvencion, int id) {
		super(nombre,descripcion,periodo,añoInvencion);
        this.pintor = pintor;
        this.id = id;
		traslados = new ArrayList<>();
}
    public Pintura(String nombre, String descripcion, Periodo periodo, Pintor pintor, int añoInvencion) {
		this(nombre,descripcion,periodo, pintor,añoInvencion,-1);
    }

    public String configGuardar() {
        String accion = "accion=nuevo_objeto";
		String entidad = "entidad="+ ControlDB.str_obj_Pintura;
        String nombreConfig= "nombre="+nombre;
		String añoConfig= "anio="+añoInvencion;
		String descriConfig= "descripcion="+descripcion;
		String periConfig= "nombre_periodo="+periodo.getNombrePeriodo();
		String pintorConfig= "nombre_persona="+ pintor.getNombre();

        return accion + "&" + entidad + "&" + nombreConfig + "&" + añoConfig + "&" + descriConfig +
				"&" + periConfig + "&" + pintorConfig;
    }
	
	public String configModificar() {
		String accion = "accion=editar_registro";
		String entidad = "entidad="+ControlDB.str_objeto;
		String idConfig = "registro_id="+id;
		String nombreConfig= "nombre="+nombre;
		String descriConfig= "descripcion="+descripcion;
		String periConfig= "nombre_periodo="+periodo.getNombrePeriodo();
		String pintorConfig= "nombre_inventor="+ pintor.getNombre();
		String añoConfig= "anio="+añoInvencion;
		
		return accion + "&" + entidad + "&" + idConfig + "&" + nombreConfig + "&" + añoConfig +
				"&" + descriConfig + "&" +  periConfig + "&" + pintorConfig;
	}
	
	
	//GETTER & SETTER

    public Pintor getPintor() {
        return pintor;
    }

    public void setPintor(Pintor pintor) {
        this.pintor = pintor;
    }
    
	public int getID() {
		return id;
	}
	public void agregarTraslado(Traslado t){
		traslados.add(t);
	}
	public void borrarTraslado(int id){
		for(Traslado t : traslados){
			if(t.getId() == id){
				traslados.remove(t);
			}
		}
	}
}
