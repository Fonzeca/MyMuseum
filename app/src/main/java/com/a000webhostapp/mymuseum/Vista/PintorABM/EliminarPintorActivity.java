package com.a000webhostapp.mymuseum.Vista.PintorABM;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.IObserver;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.Modelo.Pintor;
import com.a000webhostapp.mymuseum.R;
import com.a000webhostapp.mymuseum.Vista.ModuloNotificacion;

public class EliminarPintorActivity extends AppCompatActivity implements IObserver {
    private Button eliminar;
    private Spinner inventorSpinner;
	
	private Pintor pintorActual;
	private Pintor[] pintores;
	
	private ModuloNotificacion notificacion;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_pintor);
	
		notificacion = new ModuloNotificacion(this);
	
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
	
		eliminar = (Button) findViewById(R.id.Save_EliminarPintor);
		eliminar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				ModuloEntidad.obtenerModulo().eliminarPintor(pintorActual.getID(),EliminarPintorActivity.this);
				onBackPressed();
			}
		});
	
		inventorSpinner = (Spinner)findViewById(R.id.spinnerPintor_EliminarPintor);
		inventorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				pintorActual = (Pintor)inventorSpinner.getSelectedItem();
			}
			public void onNothingSelected(AdapterView<?> adapterView) {
			}
		});
		buscarInfoSpinners();
		
    }
	
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
	}
	
	private void buscarInfoSpinners(){
		notificacion.mostrarLoading();
		
		ModuloEntidad.obtenerModulo().buscarPintores(this);
	}
	private void actualizarSpinners(){
		if(pintores != null){
			ArrayAdapter<Pintor> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, pintores);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			inventorSpinner.setAdapter(adapter);
		}
	}
	
	public void update(Guardable[] g,int request, String respuesta) {
		if(notificacion.isLoadingShowing()){
			switch (respuesta){
				case ControlDB.res_exito:
					if(g != null){
						switch (request) {
							case ModuloEntidad.RQS_BUSQUEDA_PINTORES_TOTAL:
								if(g[0] instanceof Pintor){
									pintores = (Pintor[]) g;
								}
								break;
						}
						actualizarSpinners();
						notificacion.loadingDismiss();
					}
					break;
				case ControlDB.res_falloConexion:
					notificacion.loadingDismiss();
					//Creamos un alertDialog en el Thread UI del activity
					notificacion.mostrarError(ControlDB.res_falloConexion);
					break;
				case ControlDB.res_tablaInventorVacio:
					notificacion.loadingDismiss();
					//Creamos un alertDialog en el Thread UI del activity
					notificacion.mostrarError(ControlDB.res_tablaInventorVacio);
					break;
			}
		}
	}
}
