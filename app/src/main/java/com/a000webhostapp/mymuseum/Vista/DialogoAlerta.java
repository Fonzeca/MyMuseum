package com.a000webhostapp.mymuseum.Vista;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by Alexis on 27/9/2017.
 */

public class DialogoAlerta {
	private Activity activity;
	private AlertDialog dialogo;
	
	public DialogoAlerta(final Activity activity,final String mensaje,final String titulo){
		this.activity = activity;
		activity.runOnUiThread(new Runnable() {
			public void run() {
				dialogo = new AlertDialog.Builder(activity).create();
				dialogo.setMessage(mensaje);
				dialogo.setTitle(titulo);
				dialogo.setCancelable(false);
				dialogo.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialogInterface, int i) {
						dialogo.dismiss();
					}
				});
			}
		});
		
	}
	
	public void mostrar(){
		activity.runOnUiThread(new Runnable() {
			public void run() {
				dialogo.show();
			}
		});
	}
}
