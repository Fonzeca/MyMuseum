package com.a000webhostapp.mymuseum.Modelo;

import android.graphics.Bitmap;

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
}
