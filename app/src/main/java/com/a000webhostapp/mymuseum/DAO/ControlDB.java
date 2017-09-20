package com.a000webhostapp.mymuseum.DAO;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.a000webhostapp.mymuseum.Entidades.Invento;
import com.a000webhostapp.mymuseum.Entidades.Inventor;
import com.a000webhostapp.mymuseum.Entidades.ModuloEntidad;
import com.a000webhostapp.mymuseum.Entidades.Periodo;
import com.a000webhostapp.mymuseum.Guardable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class ControlDB extends AsyncTask implements DAOInterface{
    private ModuloEntidad me;
    private ProgressDialog pdia;

    public ControlDB(ModuloEntidad me){
        this.me = me;
    }

    protected Guardable[] doInBackground(Object[] objects) {

        switch((int)objects[0]){
            case 0:
                insertarPrivado((Guardable)objects[1]);
                break;
            case 1:
                //borrar
                break;
            case 2:
                //modificar
                break;
            case 3:
                return buscarPrivado((String)objects[1]);
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected void onPostExecute(Object result) {
        super.onPostExecute(result);

        Guardable[] resultados = (Guardable[]) result;
        if(resultados[0] != null){
            if(resultados[0] instanceof Inventor){
                me.setInventoresBuscados((Inventor[]) resultados);
                System.out.println("PASO  " +  ((Inventor[])resultados)[0].configGuardar());
            }else if(resultados[0] instanceof Periodo){

            }else if(resultados[0] instanceof Invento){

            }
        }
    }

    public boolean insertar(Guardable g) {
        execute(0,g);

        return false;
    }

    public boolean borrar() {
        execute(1);
        return false;
    }

    public boolean modificar() {

        return false;
    }

    public void buscar(String entidad){
        execute(3, entidad);
    }


    private Guardable[] buscarPrivado(String entidad){
        String respuesta = conectar("accion=obtener_datos&entidad="+entidad);
        String jsonRespuesta = respuesta.split("<!Doc")[0];
        Inventor [] inventores = null;
        try {
            JSONObject obj = new JSONObject(jsonRespuesta);
            JSONArray json_array = obj.getJSONArray("datos");
            inventores = new Inventor[json_array.length()];

            for(int i = 0; i < json_array.length(); i++){
                JSONObject inven = json_array.getJSONObject(i);
                String nom = inven.getString("nombre");
                int año = inven.getInt("año_nacimiento");
                String lugar = inven.getString("lugar_nacimiento");

                inventores[i] = new Inventor(nom,lugar,año);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return inventores;
    }

    private boolean insertarPrivado(Guardable g){
        String respuesta = conectar(g.configGuardar());
        System.out.println(respuesta);
        return false;
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
        }
        return "";
    }
}
