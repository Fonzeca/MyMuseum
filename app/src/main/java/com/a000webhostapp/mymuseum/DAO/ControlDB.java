package com.a000webhostapp.mymuseum.DAO;

import android.os.AsyncTask;
import android.util.Log;

import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.Controlador.ModuloInformacion;
import com.a000webhostapp.mymuseum.Controlador.Request;
import com.a000webhostapp.mymuseum.Controlador.RequestHistorialPintura;
import com.a000webhostapp.mymuseum.Modelo.Invento;
import com.a000webhostapp.mymuseum.Modelo.Inventor;
import com.a000webhostapp.mymuseum.Modelo.Objeto;
import com.a000webhostapp.mymuseum.Modelo.Periodo;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.Modelo.Persona;
import com.a000webhostapp.mymuseum.Modelo.Pintor;
import com.a000webhostapp.mymuseum.Modelo.Pintura;
import com.a000webhostapp.mymuseum.Modelo.Traslado;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ControlDB extends AsyncTask<Object, String, Object[]> implements DAOInterface{
	private Request request;
	
	public static final String str_obj_Invento = "Invento";
	public static final String str_obj_Pintura = "Pintura";
	public static final String[] objetos = {str_obj_Invento, str_obj_Pintura};
	public static final String str_objeto = "Objeto";
	public static final String str_traslado = "Traslado";
	
	public static final String str_per_Inventor = "Inventor";
	public static final String str_per_Pintor = "Pintor";
	public static final String[] personas = {str_per_Inventor, str_per_Pintor};
	public static final String str_persona = "Persona";
	
	public static final String str_periodo = "Periodo";
	
	public static final String res_falloConexion = "No se pudo conectar";
	public static final String res_exito = "Exito";
	public static final String res_tablaInventoVacio = "No hay Inventos en la base de datos";
	public static final String res_tablaPeriodoVacio = "No hay Periodos en la base de datos";
	public static final String res_tablaInventorVacio = "No hay Inventores en la base de datos";
	public static final String res_tablaPintoresVacio = "No hay Pintores en la base de datos";
	public static final String res_tablaPinturasVacio = "No hay Pinturas en la base de datos";
	public static final String res_tablaTrasladoUnicoVacio = "No hay Traslados asociado a la Pintura en la base de datos";
	public static final String res_busquedaFallida = "Busqueda fallida";
	
	
	public ControlDB(Request request){
		this.request = request;
	}

	protected Object[] doInBackground(Object[] objects) {

		switch((int)objects[0]){
			case 0:
				insertarPrivado((Guardable)objects[1]);
				break;
			case 1:
				borrarPrivado((String)objects[1]);
				break;
			case 2:
				modificarPrivado((Guardable)objects[1]);
				break;
			case 3:
				return buscarPrivado((String)objects[1]);
			case 4:
				return buscarPrivadoDirecto((String)objects[1], (String)objects[2]);
			case 5:
				return buscarTrasladoPrivadoID();
			case 6:
				return buscarTrasladoPrivado();
			case 7:
				return obtenerInfoCantidadPrivado((String)objects[1]);
			case 8:
				sumarBusquedaPrivado((int)objects[1]);
				break;
			case 9:
				return obtenerTopBusquedasPrivado((String)objects[1], (int)objects[2]);
			case 10:
				return obtenerTotalBusquedasPrivado();
			case 11:
				return obtenerInventorDestacadoPrivado();
		}

		return null;
	}

	protected void onPostExecute(Object[] resultados) {
		super.onPostExecute(resultados);
		if(resultados != null){
			if(resultados instanceof Guardable[]){
				if(resultados.length != 0){
					notificarModuloEntidad((Guardable[]) resultados, res_exito);
				}
			}else if(resultados instanceof String[]){
				if(resultados.length != 0){
					notificarModuloInfo((String[]) resultados, res_exito);
				}
			}
		}
		
	}

	public boolean insertar(Guardable g) {
		execute(0,g);
		return false;
	}

	public boolean borrar(String parametro) {
		execute(1, parametro);
		return false;
	}

	public boolean modificar(Guardable g) {
		execute(2,g);
		return false;
	}

	public void buscar(String entidad){
		execute(3, entidad);
	}
	
	public void buscarDirecto(String entidad, String nombre){
		execute(4, entidad, nombre);
	}
	
	public void buscarTrasladosIDPintura(){
		execute(5);
	}
	
	public void buscarTraslados(){
		execute(6);
	}
	
	public void obtenerInfoCantidad(String entidad){
		execute(7,entidad);
	}
	public void sumarBusqueda(int id){
		execute(8,id);
	}
	public void obtenerTopBusqueda(String entidad, int cantidad){
		execute(9,entidad,cantidad);
	}
	public void obtenerTotalBusquedas(){
		execute(10);
	}
	public void obtenerInventorDestacado(){
		execute(11);
	}
	//----------------
	private boolean borrarPrivado(String parametro){
		String respuesta = conectar(parametro);
		if(respuesta.equals(res_exito)){
			notificarModuloEntidad(null,res_exito);
		}
		Log.v("Response DB", respuesta);
		return true;
	}
	
	private boolean insertarPrivado(Guardable g){
		String respuesta = conectar(g.configGuardar());
		if(respuesta.equals(res_exito)){
			notificarModuloEntidad(null,res_exito);
		}
		Log.v("Response DB", respuesta);
		return true;
	}
	
	private boolean modificarPrivado(Guardable g){
		String respuesta = conectar(g.configModificar());
		if(respuesta.equals(res_exito)){
			notificarModuloEntidad(null,res_exito);
		}
		
		Log.v("Response DB", respuesta);
		return true;
	}
	
	private Guardable[] buscarPrivado(String entidad){
		String respuesta = conectar("accion=obtener_datos&entidad="+entidad);
		String jsonRespuesta = respuesta.split("<!Doc")[0];
		Log.v("Response DB", jsonRespuesta);
		
		Guardable[] respuestaFinal = null;
		
		if(entidad.equals(str_per_Inventor)){
			respuestaFinal = buscarInventores(jsonRespuesta);
		}else if(entidad.equals(str_periodo)){
			respuestaFinal = buscarPeriodo(jsonRespuesta);
		}else if(entidad.equals(str_obj_Invento)){
			respuestaFinal = buscarInvento(jsonRespuesta);
		}else if(entidad.equals(str_per_Pintor)){
			respuestaFinal = buscarPintores(jsonRespuesta);
		}else if(entidad.equals(str_obj_Pintura)){
			respuestaFinal = buscarPintura(jsonRespuesta);
		}else if(entidad.equals(str_objeto)){
			respuestaFinal = buscarObjetos(jsonRespuesta);
		}
		return respuestaFinal;
	}
	private Guardable[] buscarPrivadoDirecto(String entidad, String nombre){
		String respuesta = conectar("accion=obtener_objeto&nombre=" + nombre + "&entidad="+entidad);
		String jsonRespuesta = respuesta.split("<!Doc")[0];
		Log.v("Response DB", jsonRespuesta);
		
		Guardable[] respuestaFinal = null;
		
		if(entidad.equals(str_obj_Invento)){
			respuestaFinal = buscarInvento(jsonRespuesta);
		}else if(entidad.equals(str_obj_Pintura)){
			respuestaFinal = buscarPintura(jsonRespuesta);
		}
		if(respuestaFinal == null || respuestaFinal.length == 0 || respuestaFinal[0] == null){
			notificarModuloEntidad(null,res_busquedaFallida);
			cancel(true);
			return null;
		}
		
		return respuestaFinal;
	}
	private Guardable[] buscarTrasladoPrivadoID(){
		Pintura p = null;
		if(request instanceof RequestHistorialPintura){
			p = ((RequestHistorialPintura)request).getPintura();
		}else{
			return null;
		}
		
		String respuesta = conectar("accion=obtener_historial_pintura&pintura_id=" + p.getID());
		Log.v("Response DB", respuesta);
		return buscarTraslados(respuesta);
	}
	private Guardable[] buscarTrasladoPrivado(){
		String respuesta = conectar("accion=obtener_historial");
		Log.v("Response DB", respuesta);
		return buscarTraslados(respuesta);
	}
	private String[] obtenerInfoCantidadPrivado(String entidad){
		String respuesta = conectar("accion=obtener_total&entidad="+entidad);
		Log.v("Response DB", respuesta);
		String[] respuestaFinal = new String[1];
		respuestaFinal[0] = respuesta;
		return respuestaFinal;
	}
	private boolean sumarBusquedaPrivado(int id){
		String respuesta = conectar("accion=nueva_busqueda&objeto_id="+id);
		Log.v("Response DB", respuesta);
		return true;
	}
	private String[] obtenerTopBusquedasPrivado(String entidad, int cantidad){
		String respuesta = conectar("accion=obtener_mas_buscado&entidad="+entidad + "&cantidad="+cantidad);
		Objeto[] respuestaObjeto = null;
		switch (entidad){
			case str_obj_Invento:
				respuestaObjeto = (Objeto[]) buscarInvento(respuesta);
				break;
			case str_obj_Pintura:
				respuestaObjeto = (Objeto[]) buscarPintura(respuesta);
				break;
		}
		if(respuestaObjeto == null){
			return null;
		}else{
			String[] respuestaFinal = new String[respuestaObjeto.length];
			for(int i = 0; i < respuestaFinal.length; i++){
				respuestaFinal[i] = respuestaObjeto[i].getNombre() + "=" + respuestaObjeto[i].getCantBuscado();
				Log.v("Respuesta TOP", respuestaFinal[i]);
			}
			return respuestaFinal;
		}
		
	}
	private String[] obtenerTotalBusquedasPrivado(){
		String respuesta = conectar("accion=obtener_total_de_busquedas");
		Log.v("Response DB", respuesta);
		String[] respuestaFinal = new String[1];
		respuestaFinal[0] = respuesta;
		return respuestaFinal;
	}
	private String[] obtenerInventorDestacadoPrivado(){
		String respuesta = conectar("accion=obtener_inventor_destacado");
		Log.v("Response DB", respuesta);
		Persona[] inventorSolo = (Persona[]) buscarInventores(respuesta);
		if(inventorSolo == null){
			return null;
		}else{
			String[] respuestaFinal = new String[1];
			respuestaFinal[0] = inventorSolo[0].getNombre();
			return respuestaFinal;
		}
	}
	//----------------
	
	private Guardable[] buscarInventores(String jsonRespuesta){
		Inventor [] inventores = null;
		try {
			JSONObject obj = new JSONObject(jsonRespuesta);
			JSONArray json_array = obj.getJSONArray("datos");
			inventores = new Inventor[json_array.length()];
			
			if(inventores.length == 0){
				notificarModuloEntidad(null, res_tablaInventorVacio);
				cancel(true);
				return null;
			}

			for(int i = 0; i < json_array.length(); i++){
				JSONObject inven = json_array.getJSONObject(i);
				
				inventores[i] = Inventor.obtenerInventorJSON(inven);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return inventores;
	}
	private Guardable[] buscarPeriodo(String jsonRespuesta){
		Periodo [] periodos = null;
		try {
			JSONObject obj = new JSONObject(jsonRespuesta);
			JSONArray json_array = obj.getJSONArray("datos");
			periodos = new Periodo[json_array.length()];
	
			if(periodos.length == 0){
				notificarModuloEntidad(null, res_tablaPeriodoVacio);
				cancel(true);
				return null;
			}
			
			for(int i = 0; i < json_array.length(); i++){
				JSONObject peri = json_array.getJSONObject(i);
				
				periodos[i] = Periodo.obtenerPeriodoJSON(peri);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return periodos;
	}
	private Guardable[] buscarInvento(String jsonRespuesta){
		Invento[] inventos = null;
		
		try {
			JSONObject obj = new JSONObject(jsonRespuesta);
			JSONArray json_array = obj.getJSONArray("datos");
			inventos = new Invento[json_array.length()];
			
			if(inventos.length == 0){
				notificarModuloEntidad(inventos, res_tablaInventoVacio);
				cancel(true);
				return null;
			}
			
			for(int i = 0; i < json_array.length(); i++){
				JSONObject inve = json_array.getJSONObject(i);
				
				inventos[i] = Invento.obtenerInventoJSON(inve);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return inventos;
	}
	private Guardable[] buscarPintores(String jsonRespuesta){
		Pintor[] pintores = null;
		try {
			JSONObject obj = new JSONObject(jsonRespuesta);
			JSONArray json_array = obj.getJSONArray("datos");
			pintores = new Pintor[json_array.length()];
			
			if(pintores.length == 0){
				notificarModuloEntidad(null, res_tablaPintoresVacio);
				cancel(true);
				return null;
			}
			
			for(int i = 0; i < json_array.length(); i++){
				JSONObject pintorr = json_array.getJSONObject(i);
				
				pintores[i] = Pintor.obtenerPintorJSON(pintorr);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return pintores;
	}
	private Guardable[] buscarPintura(String jsonRespuesta){
		Pintura[] pinturas = null;
		
		try {
			JSONObject obj = new JSONObject(jsonRespuesta);
			JSONArray json_array = obj.getJSONArray("datos");
			pinturas = new Pintura[json_array.length()];
			
			if(pinturas.length == 0){
				notificarModuloEntidad(null, res_tablaPinturasVacio);
				cancel(true);
				return null;
			}
			
			for(int i = 0; i < json_array.length(); i++){
				JSONObject pinturaa = json_array.getJSONObject(i);
				
				pinturas[i] = Pintura.obtenerPinturaJSON(pinturaa);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return pinturas;
	}
	private Guardable[] buscarObjetos(String jsonRespuesta){
		Guardable[] inventos, pinturas;
		inventos = buscarPrivado(ControlDB.str_obj_Invento);
		pinturas = buscarPrivado(ControlDB.str_obj_Pintura);
		Guardable[] objetos = new Objeto[inventos.length + pinturas.length];
		for (int i = 0; i < inventos.length; i++){
			objetos [i] = inventos[i];
		}
		for (int i = 0; i < pinturas.length; i++){
			objetos[i + inventos.length] = pinturas[i];
		}
		return objetos;
	}
	private Guardable[] buscarTraslados(String jsonRespuesta){
		Traslado[] traslados = null;
		
		try {
			JSONObject obj = new JSONObject(jsonRespuesta);
			JSONArray json_array = obj.getJSONArray("datos");
			traslados = new Traslado[json_array.length()];
			
			if(traslados.length == 0){
				notificarModuloEntidad(null, res_tablaTrasladoUnicoVacio);
				cancel(true);
				return null;
			}
			
			for(int i = 0; i < json_array.length(); i++){
				JSONObject trasladoJSON = json_array.getJSONObject(i);
				traslados[i] = Traslado.obtenerTrasladoJSON(trasladoJSON);
			}
			
		}catch (JSONException e){
			e.printStackTrace();
		}
		return traslados;
	}
	//----------------
	
	private String conectar(String parametros){
		try {
			URL url = new URL("http://mymuseum.000webhostapp.com/index.php");
			
			Log.v("System.out","\nSending 'POST' id to URL : " + url);
			
			HttpURLConnection conect= (HttpURLConnection) url.openConnection();
			conect.setRequestProperty("Accept-Charset", "UTF-8");
			conect.setRequestMethod("POST");

			//el mensaje que le mandamos a la Base de Datos
			String urlParameters = parametros;

			conect.setDoOutput(true);


			OutputStream wr = conect.getOutputStream();
			wr.write(urlParameters.getBytes("UTF-8"));
			
			Log.v("System.out","Post parameters : " + urlParameters);
			
			wr.flush();
			wr.close();

			int responseCode = conect.getResponseCode();
			
			Log.v("System.out","Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(conect.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			return response.toString();
		} catch (java.io.IOException e) {
			e.printStackTrace();
			notificarModuloEntidad(null, res_falloConexion);
			cancel(true);
		}
		notificarModuloEntidad(null, res_falloConexion);
		cancel(true);
		return "NO SE CONECTO";
	}


	//Metodos del Observer
	public void notificarModuloEntidad(Guardable[] g, String respuesta){
		ModuloEntidad.obtenerModulo().resultadoControlDB(request,respuesta,g);
	}
	public void notificarModuloInfo(String[] data, String respuesta){
		ModuloInformacion.obtenerModulo().resultadoControlDB(request,respuesta,data);
	}
}
