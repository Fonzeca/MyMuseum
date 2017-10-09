package com.a000webhostapp.mymuseum.DAO;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.a000webhostapp.mymuseum.Modelo.Invento;
import com.a000webhostapp.mymuseum.Modelo.Inventor;
import com.a000webhostapp.mymuseum.Modelo.Periodo;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.IObserver;
import com.a000webhostapp.mymuseum.ISujeto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class ControlDB extends AsyncTask<Object, String, Guardable[]> implements ISujeto,DAOInterface{
    private ArrayList<IObserver> observers;


    public ControlDB(IObserver ob){
        observers = new ArrayList<IObserver>();
        registrarObvserver(ob);
    }

    protected Guardable[] doInBackground(Object[] objects) {

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
        }

        return null;
    }

    protected void onPostExecute(Guardable[] resultados) {
        super.onPostExecute(resultados);

        if(resultados != null && resultados.length != 0){
            if(resultados[0] instanceof Inventor){
                notificarObsverver(resultados, 0);
            }else if(resultados[0] instanceof Periodo){
                notificarObsverver(resultados, 1);
            }else if(resultados[0] instanceof Invento){
                notificarObsverver(resultados, 2);
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
	
    private boolean borrarPrivado(String parametro){
		String respuesta = conectar(parametro);
		System.out.println(respuesta);
		return true;
	}
	
	private boolean insertarPrivado(Guardable g){
		String respuesta = conectar(g.configGuardar());
		System.out.println(respuesta);
		return true;
	}
	
	private boolean modificarPrivado(Guardable g){
		String respuesta = conectar(g.configModificar());
		System.out.println(respuesta);
		return true;
	}
	
    private Guardable[] buscarPrivado(String entidad){
        String respuesta = conectar("accion=obtener_datos&entidad="+entidad);
        String jsonRespuesta = respuesta.split("<!Doc")[0];
		
		Guardable[] respuestaFinal = null;
		
		if(entidad.equals("Inventor")){
			respuestaFinal = buscarInventores(jsonRespuesta);
		}else if(entidad.equals("Periodo")){
			respuestaFinal = buscarPeriodo(jsonRespuesta);
		}else if(entidad.equals("Invento")){
			respuestaFinal = buscarInvento(jsonRespuesta);
		}
        return respuestaFinal;
    }
	
    private Guardable[] buscarInventores(String jsonRespuesta){
        Inventor [] inventores = null;
        try {
            JSONObject obj = new JSONObject(jsonRespuesta);
            JSONArray json_array = obj.getJSONArray("datos");
            inventores = new Inventor[json_array.length()];

            for(int i = 0; i < json_array.length(); i++){
                JSONObject inven = json_array.getJSONObject(i);
                String nom = inven.getString("nombre");
				int idConfig = inven.getInt("inventor_id");
                int año = inven.getInt("año_nacimiento");
                String lugar = inven.getString("lugar_nacimiento");

                inventores[i] = new Inventor(nom,lugar,año, idConfig);
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

            for(int i = 0; i < json_array.length(); i++){
                JSONObject peri = json_array.getJSONObject(i);
                String nom = peri.getString("nombre");
				int idConfig = peri.getInt("periodo_id");
                int añoIncio = peri.getInt("año_inicio");
                int añoFin = peri.getInt("año_fin");

                periodos[i] = new Periodo(nom,añoIncio,añoFin, idConfig);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return periodos;
    }
    private Guardable[] buscarInvento(String jsonRespuesta){
        Invento[] inventos = null;
		System.out.println(jsonRespuesta);
		try {
            JSONObject obj = new JSONObject(jsonRespuesta);
            JSONArray json_array = obj.getJSONArray("datos");
            inventos = new Invento[json_array.length()];
			
			for(int i = 0; i < json_array.length(); i++){
                JSONObject inve = json_array.getJSONObject(i);
				int id = inve.getInt("invento_id");
                String nom = inve.getString("nombre");
                String descripcion = inve.getString("descripcion");
                JSONObject inventorJSON = inve.getJSONObject("inventor");
                Inventor inventor = new Inventor(inventorJSON.getString("nombre"),inventorJSON.getString("lugar_nacimiento"),inventorJSON.getInt("año_nacimiento"),inventorJSON.getInt("inventor_id"));
				
                JSONObject periodoJSON = inve.getJSONObject("periodo");
                Periodo periodo = new Periodo(periodoJSON.getString("nombre"),periodoJSON.getInt("año_inicio"),periodoJSON.getInt("año_fin"),periodoJSON.getInt("periodo_id"));
				
                int año = inve.getInt("año");
                boolean maquina = inve.getBoolean("es_maquina");
				
				System.out.println(nom);
				inventos[i] = new Invento(nom,descripcion,periodo,inventor,año,maquina,id);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
		return inventos;
    }

    
    
    private String conectar(String parametros){
        try {
            URL url = new URL("http://mymuseum.000webhostapp.com/index.php");
            HttpURLConnection conect= (HttpURLConnection) url.openConnection();
            conect.setRequestMethod("POST");

            //el mensaje que le mandamos a la Base de Datos
            String urlParameters = parametros;

            conect.setDoOutput(true);


            DataOutputStream wr = new DataOutputStream(conect.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = conect.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

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
            notificarObsverver(null, -1);
            cancel(true);
        }
		return "NO SE CONECTO";
    }


    //Metodos del Observer
    @Override
    public void registrarObvserver(IObserver ob) {
        observers.add(ob);
    }

    @Override
    public boolean eliminarObvserver(IObserver ob) {
        for (int i = 0; i < observers.size(); i++){
            if(observers.get(i).equals(ob)){
                observers.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public void notificarObsverver(Guardable[] g, int id) {
        for (int i = 0; i < observers.size(); i++){
            observers.get(i).update(g, id);
        }
    }
}
