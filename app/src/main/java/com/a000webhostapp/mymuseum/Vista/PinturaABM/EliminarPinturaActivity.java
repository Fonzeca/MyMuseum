package com.a000webhostapp.mymuseum.Vista.PinturaABM;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.IObserver;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.Modelo.Invento;
import com.a000webhostapp.mymuseum.Modelo.Pintura;
import com.a000webhostapp.mymuseum.R;
import com.a000webhostapp.mymuseum.Vista.ModuloNotificacion;

import java.util.ArrayList;
import java.util.List;

public class EliminarPinturaActivity extends AppCompatActivity implements IObserver {
	private Button eliminar;
	private Spinner pinturaSpinner;
	
	private Pintura pinturaActual;
	private Pintura[] pinturas;
	
	private ModuloNotificacion notificacion;
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_pintura);
	
		notificacion = new ModuloNotificacion(this);
	
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		
		eliminar = (Button) findViewById(R.id.Save_EliminarPintura);
		eliminar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				ModuloEntidad.obtenerModulo().eliminarPintura(pinturaActual.getID(),EliminarPinturaActivity.this);
				onBackPressed();
			}
		});
		
		pinturaSpinner = (Spinner)findViewById(R.id.spinnerPintura_EliminarPintura);
		pinturaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				pinturaActual = (Pintura) pinturaSpinner.getSelectedItem();
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
		ModuloEntidad.obtenerModulo().buscarPinturas(this);
	}
    private void actualizarSpinners(){
		if(pinturas != null){
			//se llena el array con los inventosCargados
			ArrayAdapter<Pintura> adapter = new ArrayAdapter<Pintura>(this, R.layout.support_simple_spinner_dropdown_item, pinturas);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			pinturaSpinner.setAdapter(adapter);
			
		}
	}
	
    public void update(Guardable[] g,int request, String respuesta) {
        if(notificacion.isLoadingShowing()){
			switch (respuesta){
				case ControlDB.res_exito:
					if(g != null){
						switch (request) {
							case ModuloEntidad.RQS_BUSQUEDA_PINTURAS_TOTAL:
								if(g[0] instanceof Pintura){
									pinturas = (Pintura[]) g;
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
				case ControlDB.res_tablaPinturasVacio:
					notificacion.loadingDismiss();
					//Creamos un alertDialog en el Thread UI del activity
					notificacion.mostrarError(ControlDB.res_tablaPinturasVacio);
					break;
			}
			
		}
    }
}
