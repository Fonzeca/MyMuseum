package com.a000webhostapp.mymuseum.Vista.PintorABM;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.Vista.ModuloNotificacion;
import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.Observers.IObserverEntidad;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.R;

/**
 * Created by Alexis on 10/10/2017.
 */

public class NuevoPintorActivity extends AppCompatActivity implements IObserverEntidad {
	private EditText nomYApe, año,lugarNacimiento;
	private CheckBox AC;
	private Button btnGuardar;
	private ModuloNotificacion notificacion;
	
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nuevo_pintor);
		
		notificacion = new ModuloNotificacion(this);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		
		nomYApe = (EditText) findViewById(R.id.nomyapePintor_NuevoPintor);
		
		año = (EditText) findViewById(R.id.añoNacimiento_NuevoPintor);
		lugarNacimiento = (EditText) findViewById(R.id.lugarNacimiento_NuevoPintor);
		
		AC = (CheckBox) findViewById(R.id.checkboxAC_NuevoPintor);
		
		btnGuardar = (Button) findViewById(R.id.Save_NuevoPintor);
		btnGuardar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				guardarInformacion();
			}
		});
		
	}
	public void guardarInformacion(){
		ModuloEntidad me = ModuloEntidad.obtenerModulo();
		String nombreyapellido = nomYApe.getText().toString();
		String lugar = lugarNacimiento.getText().toString();
		int añoPintor;
		if(!nombreyapellido.equals("") && !lugar.equals("") && !año.getText().toString().equals("")){
			if(AC.isChecked()){
				añoPintor = Integer.parseInt("-" + año.getText().toString());
			}else{
				añoPintor = Integer.parseInt(año.getText().toString());
			}
			me.crearPintor(nombreyapellido,lugar,añoPintor,this);
			notificacion.mostrarLoading();
			btnGuardar.setEnabled(false);
		}
	}
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
	}
	public void update(Guardable[] g, int request, String respuesta) {
		if(notificacion.isLoadingShowing()){
			switch (respuesta){
				case ControlDB.res_exito:
					if(g != null){
					
					}else if(request == ModuloEntidad.RQS_ALTA_PINTOR){
						notificacion.loadingDismiss();
						notificacion.mostarNotificacion("Se cargo exitosamente");
						runOnUiThread(new Runnable() {
							public void run() {
								onBackPressed();
							}
						});
					}
					break;
				default:
					notificacion.loadingDismiss();
					notificacion.mostrarError(respuesta);
					break;
			}
		}
	}
}
