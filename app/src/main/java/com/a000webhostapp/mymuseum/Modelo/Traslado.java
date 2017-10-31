package com.a000webhostapp.mymuseum.Modelo;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Alexis on 25/10/2017.
 */

public class Traslado implements Serializable,Guardable {
	private Pintura pintura;
	private int id;
	
	private String lugarOrigen, lugarDestino, fechaTraslado;
	
	public Traslado(Pintura pintura, String lugarOrigen, String lugarDestino, String fechaTraslado, int id){
		this.pintura = pintura;
		this.lugarOrigen = lugarOrigen;
		this.lugarDestino = lugarDestino;
		this.fechaTraslado = fechaTraslado;
		this.id = id;
	}
	public Traslado(Pintura pintura, String lugarOrigen, String lugarDestino, String fechaTraslado){
		this(pintura,lugarOrigen, lugarDestino, fechaTraslado, -1);
	}
	
	
	public static Traslado obtenerTrasladoJSON(JSONObject obJSON, Pintura pintura) throws JSONException{
		int idConfig = obJSON.getInt("traslado_id");
		String origenConfig = obJSON.getString("lugar_origen");
		String destinoConfig = obJSON.getString("lugar_destino");
		String fechaConfig = obJSON.getString("fecha");
		
		Traslado nuevoTraslado = new Traslado(pintura,origenConfig,destinoConfig,fechaConfig,idConfig);
		pintura.agregarTraslado(nuevoTraslado);
		
		return nuevoTraslado;
	}
	
	public String configGuardar() {
		String accion = "accion=nuevo_traslado";
		String idPintura = "pintura_id="+pintura.getID();
		String strOrigen = "lugar_origen="+lugarOrigen;
		String strDestino = "lugar_destino=" + lugarDestino;
		String strFecha = "fecha=" + fechaTraslado;
		return accion + "&" + idPintura + "&" + strOrigen + "&" + strDestino + "&" + strFecha;
	}
	
	public String configModificar() {
		return null;
	}
	
	//GETTERS & SETTERS
	public Pintura getPintura() {
		return pintura;
	}
	
	public void setPintura(Pintura pintura) {
		this.pintura = pintura;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
