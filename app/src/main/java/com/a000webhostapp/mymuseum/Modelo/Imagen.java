package com.a000webhostapp.mymuseum.Modelo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Alexis on 6/11/2017.
 */

public class Imagen implements Guardable{
	private transient Bitmap bitmap;
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
	private void writeObject(ObjectOutputStream out) throws IOException{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		BitMapObjectSerealizalbe b = new BitMapObjectSerealizalbe();
		b.imageArray = stream.toByteArray();
		
		out.writeObject(b);
	}
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		BitMapObjectSerealizalbe b = (BitMapObjectSerealizalbe)in.readObject();
		bitmap = BitmapFactory.decodeByteArray(b.imageArray,0,b.imageArray.length);
	}
	class BitMapObjectSerealizalbe implements Serializable{
		public byte[] imageArray;
	}
	
}
