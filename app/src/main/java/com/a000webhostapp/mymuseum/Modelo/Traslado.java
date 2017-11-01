package com.a000webhostapp.mymuseum.Modelo;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Alexis on 25/10/2017.
 */

public class Traslado implements Guardable {
	private String nombrePintura;
	private int idPintura;
	private int id;
	
	
	private String lugarOrigen, lugarDestino, fechaTraslado;
	
	public Traslado(String nombrePintura,int idPintura, String lugarOrigen, String lugarDestino, String fechaTraslado, int id){
		this.nombrePintura = nombrePintura;
		this.idPintura = idPintura;
		this.lugarOrigen = lugarOrigen;
		this.lugarDestino = lugarDestino;
		this.fechaTraslado = fechaTraslado;
		this.id = id;
	}
	public Traslado(String nombrePintura,int idPintura, String lugarOrigen, String lugarDestino, String fechaTraslado){
		this(nombrePintura,idPintura,lugarOrigen, lugarDestino, fechaTraslado, -1);
	}
	
	
	public String configGuardar() {
		String accion = "accion=nuevo_traslado";
		String strIdPintura = "pintura_id="+idPintura;
		String strOrigen = "lugar_origen="+lugarOrigen;
		String strDestino = "lugar_destino=" + lugarDestino;
		
		//Truncar fecha
		String[] partesFecha = fechaTraslado.split("/");
		String fechaTruncada = partesFecha[2] + "-" + partesFecha[1] + "-" +partesFecha[0];
		//Fin truncar fecha
		
		String strFecha = "fecha=" + fechaTruncada;
		return accion + "&" + strIdPintura + "&" + strOrigen + "&" + strDestino + "&" + strFecha;
	}
	
	public String configModificar() {
		return null;
	}
	
	public static Traslado obtenerTrasladoJSON(JSONObject obJSON) throws JSONException{
		int idConfig = obJSON.getInt("traslado_id");
		String origenConfig = obJSON.getString("lugar_origen");
		String destinoConfig = obJSON.getString("lugar_destino");
		String fechaConfig = obJSON.getString("fecha");
		
		//Truncar fecha
		String[] partesFechaConfig = fechaConfig.split("-");
		String fechaTruncada = partesFechaConfig[2] + "/" + partesFechaConfig[1] + "/" +partesFechaConfig[0];
		//Fun truncar fecha
		
		String nombrePinturaCOndig = obJSON.getString("nombre_pintura");
		
		Traslado nuevoTraslado = new Traslado(nombrePinturaCOndig,-1,origenConfig,destinoConfig,fechaTruncada,idConfig);
		
		return nuevoTraslado;
	}
	
	//GETTERS & SETTERS
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNombrePintura() {
		return nombrePintura;
	}
	
	public String getLugarOrigen() {
		return lugarOrigen;
	}
	
	public void setLugarOrigen(String lugarOrigen) {
		this.lugarOrigen = lugarOrigen;
	}
	
	public String getLugarDestino() {
		return lugarDestino;
	}
	
	public void setLugarDestino(String lugarDestino) {
		this.lugarDestino = lugarDestino;
	}
	
	public String getFechaTraslado() {
		return fechaTraslado;
	}
	
	public void setFechaTraslado(String fechaTraslado) {
		this.fechaTraslado = fechaTraslado;
	}
}
