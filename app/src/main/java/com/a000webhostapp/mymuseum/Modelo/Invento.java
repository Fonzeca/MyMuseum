package com.a000webhostapp.mymuseum.Modelo;

import com.a000webhostapp.mymuseum.DAO.ControlDB;

import org.json.JSONException;
import org.json.JSONObject;

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
        String accion = "accion=nuevo_objeto";
		String entidad = "entidad="+ ControlDB.str_obj_Invento;
        String nombreConfig= "nombre="+nombre;
        String descriConfig= "descripcion="+descripcion;
        String periConfig= "nombre_periodo="+periodo.getNombrePeriodo();
        String inventorConfig= "nombre_persona="+inventor.getNombre();
        String añoConfig= "anio="+añoInvencion;
        String isMaquinaConfig= isMaquina?"es_maquina=1":"es_maquina=0";

        return accion + "&" + entidad + "&" + nombreConfig + "&" + añoConfig + "&" + descriConfig +
                "&" + isMaquinaConfig + "&" + periConfig + "&" + inventorConfig;
    }
	
	public String configModificar() {
		String accion = "accion=editar_registro";
		String entidad = "entidad=" + ControlDB.str_objeto;
		String idConfig = "registro_id="+id;
		String nombreConfig= "nombre="+nombre;
		String descriConfig= "descripcion="+descripcion;
		String periConfig= "nombre_periodo="+periodo.getNombrePeriodo();
		String inventorConfig= "nombre_persona="+inventor.getNombre();
		String añoConfig= "anio="+añoInvencion;
		String isMaquinaConfig= isMaquina?"es_maquina=1":"es_maquina=0";
		
		return accion + "&" + entidad + "&" + idConfig + "&" + nombreConfig + "&" + añoConfig +
				"&" + descriConfig + "&" + isMaquinaConfig + "&" + periConfig + "&" + inventorConfig;
	}
	
	public static Invento obtenerInventoJSON(JSONObject obJSON) throws JSONException{
		int id = obJSON.getInt("invento_id");
		String nom = obJSON.getString("nombre");
		String descripcion = obJSON.getString("descripcion");
		
		JSONObject inventorJSON = obJSON.getJSONObject("inventor");
		Inventor inventor = Inventor.obtenerInventorJSON(inventorJSON);
		
		JSONObject periodoJSON = obJSON.getJSONObject("periodo");
		Periodo periodo = Periodo.obtenerPeriodoJSON(periodoJSON);
		
		int año = obJSON.getInt("año");
		boolean maquina = obJSON.getBoolean("es_maquina");
		
		Invento inventoFinal = new Invento(nom,descripcion,periodo,inventor,año,maquina,id);
		if(obJSON.has("cant_busquedas")){
			inventoFinal.setCantBuscado(obJSON.getInt("cant_busquedas"));
		}
		
		return inventoFinal;
		
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
