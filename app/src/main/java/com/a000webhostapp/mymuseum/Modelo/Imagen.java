package com.a000webhostapp.mymuseum.Modelo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Alexis on 6/11/2017.
 */

public class Imagen implements Guardable{
	private Bitmap bitmap;
	public Imagen(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public String configGuardar() {
		return null;
	}
	
	public String configModificar() {
		return null;
	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	
	public static Imagen obtenerImagen(Uri uri, Context context){
		Imagen imagen = null;
		try {
			ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
			FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
			Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
			parcelFileDescriptor.close();
			imagen = new Imagen(bitmap);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return imagen;
	}
}
