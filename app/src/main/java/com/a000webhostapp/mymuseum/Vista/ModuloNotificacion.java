package com.a000webhostapp.mymuseum.Vista;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * Created by Alexis on 24/11/2017.
 */

public class ModuloNotificacion {
	private Activity activity;
	private AlertDialog alertDialog;
	private ProgressDialog loading;
	
	public ModuloNotificacion(final Activity activity){
		this.activity = activity;
		activity.runOnUiThread(new Runnable() {
			public void run() {
				loading = new ProgressDialog(activity){
					public void onBackPressed() {
						if(isShowing()){
							dismiss();
						}else{
							super.onBackPressed();
						}
					}
				};
				loading.setCancelable(false);
				
				
				
				alertDialog = new AlertDialog.Builder(activity).create();
				alertDialog.setCancelable(false);
				alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialogInterface, int i) {
						alertDialog.dismiss();
					}
				});
				
				
			}
		});
	}
	
	public void mostrarError(final String mensaje){
		activity.runOnUiThread(new Runnable() {
			public void run() {
				alertDialog.setTitle("Error");
				alertDialog.setMessage(mensaje);
				alertDialog.show();
			}
		});
	}
	public void mostrarLoading(){
		activity.runOnUiThread(new Runnable() {
			public void run() {
				loading.setMessage("Espere un momento...");
				loading.show();
			}
		});
	}
	public void mostarNotificacion(final String mensaje){
		activity.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(activity,mensaje,Toast.LENGTH_SHORT).show();
			}
		});
	}
	public boolean isAlertaShowing(){
		return alertDialog.isShowing();
	}
	public boolean isLoadingShowing(){
		return loading.isShowing();
	}
	public void alertaDismiss(){
		alertDialog.dismiss();
	}
	public void loadingDismiss(){
		loading.dismiss();
	}
}
