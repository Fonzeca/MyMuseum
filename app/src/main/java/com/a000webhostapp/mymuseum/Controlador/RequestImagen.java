package com.a000webhostapp.mymuseum.Controlador;

import android.content.Context;
import android.net.Uri;

import com.a000webhostapp.mymuseum.DAO.ControlFTP;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Alexis on 6/11/2017.
 */

public class RequestImagen extends Request {
	private Context context;
	private String nombre, entidad;
	public RequestImagen(int id, Context context, String nombre, String entidad) {
		super(id);
		this.context = context;
		this.nombre = nombre;
		this.entidad = entidad;
	}
	public RequestImagen(int id, String nombre, String entidad) {
		this(id,null,nombre,entidad);
	}
	public InputStream getInputStreamContext(Uri uri){
		try {
			return context.getContentResolver().openInputStream(uri);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getPath() {
		return ControlFTP.pathImagenFTP + entidad + nombre + ControlFTP.extensionArchivo;
	}
}
