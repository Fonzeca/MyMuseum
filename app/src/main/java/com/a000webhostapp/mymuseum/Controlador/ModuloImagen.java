package com.a000webhostapp.mymuseum.Controlador;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.a000webhostapp.mymuseum.DAO.ControlFTP;
import com.a000webhostapp.mymuseum.IObserver;
import com.a000webhostapp.mymuseum.ISujeto;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.Modelo.Imagen;

import java.util.ArrayList;

/**
 * Created by Alexis on 5/11/2017.
 */

public class ModuloImagen implements ISujeto {
	private ArrayList<IObserver> observers;
	private ArrayList<Request> requests;
	private static ModuloImagen mi;
	
	public static final int RQS_BUSQUEDA_IMAGEN_UNICA = 1000;
	
	public static final int RQS_INSERTAR_IMAGEN = 1100;
	
	private ModuloImagen(){
		observers = new ArrayList<IObserver>();
		requests = new ArrayList<Request>();
	}
	
	public static ModuloImagen obtenerModulo(){
		if(mi == null){
			mi = new ModuloImagen();
		}
		return mi;
	}
	//----------------------
	public void buscarImagen(String nombre, String entidad, IObserver observer){
		RequestImagen ri = new RequestImagen(RQS_BUSQUEDA_IMAGEN_UNICA,nombre,entidad);
		registrarObserver(observer,ri);
		new ControlFTP(ri).buscar();
	}
	//----------------------
	public void insertarImagen(String nombre, String entidad,Context context,Uri uri, IObserver observer){
		RequestImagen ri = new RequestImagen(RQS_INSERTAR_IMAGEN, context,nombre,entidad);
		registrarObserver(observer,ri);
		new ControlFTP(ri).insertar(uri);
	}
	//----------------------
	
	//Metodos Observer
	public void registrarObserver(IObserver ob, Request request) {
		observers.add(ob);
		requests.add(request);
	}
	
	public boolean eliminarObserver(IObserver ob) {
		requests.remove(observers.indexOf(ob));
		observers.remove(ob);
		return true;
	}
	
	public void notificarObserver(Request request, Guardable[] g, String respuesta) {
		for(int i = 0; i < requests.size(); i++){
			if(request.equals(requests.get(i))){
				observers.get(i).update(g,request.getId(),respuesta);
			}
		}
	}
	
	public void resultadoControlFTP(Request request, String respuesta, Bitmap g){
		switch (respuesta){
			case "Exito":
				switch (request.getId()){
					case RQS_BUSQUEDA_IMAGEN_UNICA:
						//La cagaste
						Imagen im = null;
						Guardable[] gs = new Guardable[1];
						if(g != null){
							im = new Imagen(g);
							gs[0] = im;
						}
						notificarObserver(request,gs,respuesta);
						break;
				}
				break;
		}
	}
	
	
	
	/*protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prueba);
		
		iv = (ImageView)findViewById(R.id.imageViewPRUEBA);
		
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		startActivityForResult(Intent.createChooser(intent,"Elegir imagen"),5);
		
		
		r = new AsyncTask() {
			protected Object doInBackground(Object[] objects) {
				if (objects[0] instanceof Uri) {
					Uri uri = (Uri) objects[0];
					insertarImagen(uri);
				}
				return null;
			}
		};
		
		
	}
	private void insertarImagen(Uri uri){
		boolean conecto = false;
		try{
			ftp = new FTPClient();
			ftp.connect("files.000webhost.com",21);
			if(FTPReply.isPositiveCompletion(ftp.getReplyCode())){
				conecto = ftp.login("mymuseum", "39623299Aa");
				ftp.setFileType(FTP.BINARY_FILE_TYPE);
				ftp.enterLocalPassiveMode();
			}
			Log.v("Conectar",""+conecto);
			
			//InputStream is = getContentResolver().openInputStream(uri);
			//boolean a = ftp.storeFile("/public_html/images/hola.jpeg", is);
			//is.close();
			
			//Log.v("Guardar",""+a);
			
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			
			boolean a = ftp.retrieveFile("/public_html/images/hola.jpeg",os);
			os.close();
			
			Log.v("Obtener",""+a);
			
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Bitmap a = BitmapFactory.decodeByteArray(os.toByteArray(),0,os.toByteArray().length);
					iv.setImageBitmap(a);
				}
			});
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				ftp.logout();
				ftp.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 5){
			r.execute(data.getData());
		}
	}*/
	
}
