package com.a000webhostapp.mymuseum.DAO;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.a000webhostapp.mymuseum.Controlador.ModuloImagen;
import com.a000webhostapp.mymuseum.Controlador.Request;
import com.a000webhostapp.mymuseum.Controlador.RequestImagen;
import com.a000webhostapp.mymuseum.Modelo.Guardable;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Alexis on 6/11/2017.
 */

public class ControlFTP extends AsyncTask<Uri, String, Bitmap>{
	public static String pathImagenFTP = "/public_html/images/MyMuseum/";
	public static String extensionArchivo = ".png";
	
	private Context context;
	private FTPClient ftp;
	private RequestImagen request;
	public ControlFTP(RequestImagen request) {
		this.request = request;
	}
	
	public void buscar(){
		execute();
	}
	public void insertar(Uri uri){
		execute(uri);
	}
	
	protected Bitmap doInBackground(Uri... uris) {
		conectar();
		Bitmap bitmap = null;
		Uri uri = null;
		if(uris.length > 0 && uris[0] != null){
			uri = uris[0];
		}
		switch (request.getId()){
			case ModuloImagen.RQS_BUSQUEDA_IMAGEN_UNICA:
				bitmap = buscarImagen();
				break;
			case ModuloImagen.RQS_INSERTAR_IMAGEN:
				insertarImagen(request.getInputStreamContext(uri));
				break;
		}
		
		desconectar();
		return bitmap;
	}
	private boolean insertarImagen(InputStream is){
		boolean guardo = false;
		try {
			String path = request.getPath();
			Log.v("Ubicacion", path + "");
			guardo = ftp.storeFile(path, is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Log.v("Guardar",""+guardo);
		return guardo;
	}
	private Bitmap buscarImagen(){
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		boolean busco = false;
		Bitmap bitmap = null;
		try {
			String path = request.getPath();
			Log.v("Ubicacion", path + "");
			busco = ftp.retrieveFile(path,os);
			if(busco){
				os.close();
				byte[] bytes = os.toByteArray();
				bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.v("Obtener",""+busco);
		return bitmap;
	}
	private boolean conectar(){
		boolean conecto = false;
		try {
			ftp = new FTPClient();
			ftp.connect("files.000webhost.com",21);
			if(FTPReply.isPositiveCompletion(ftp.getReplyCode())){
				conecto = ftp.login("mymuseum", "39623299Aa");
				ftp.setFileType(FTP.BINARY_FILE_TYPE);
				ftp.enterLocalPassiveMode();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return conecto;
	}
	private void desconectar(){
		if(ftp != null && ftp.isConnected()){
			try {
				ftp.logout();
				ftp.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected void onPostExecute(Bitmap bitmap) {
		super.onPostExecute(bitmap);
		notificarModulo(bitmap,"Exito");
	}
	
	private void notificarModulo(Bitmap g, String respuesta){
		ModuloImagen.obtenerModulo().resultadoControlFTP(request,respuesta,g);
	}
}
