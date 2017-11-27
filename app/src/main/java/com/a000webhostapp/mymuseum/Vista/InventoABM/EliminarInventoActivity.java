package com.a000webhostapp.mymuseum.Vista.InventoABM;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.a000webhostapp.mymuseum.R;
import com.a000webhostapp.mymuseum.Vista.ModuloNotificacion;

import java.util.ArrayList;
import java.util.List;

public class EliminarInventoActivity extends AppCompatActivity implements IObserver {
	private Button eliminar;
	private Spinner inventoSpinner;
	
	private Invento inventoActual;
	private Invento[] inventos;
	
	private ModuloNotificacion notificacion;
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_invento);
        
        notificacion = new ModuloNotificacion(this);
	
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		
		eliminar = (Button) findViewById(R.id.Save_EliminarInvento);
		eliminar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				ModuloEntidad.obtenerModulo().eliminarInvento(inventoActual.getID(),EliminarInventoActivity.this);
				onBackPressed();
			}
		});
		
		inventoSpinner = (Spinner)findViewById(R.id.spinnerInvento_EliminarInvento);
		inventoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				String nombreInventoActual = (String)inventoSpinner.getSelectedItem();
				for (int index = 0; index < inventos.length; index++){
					if(inventos[index].getNombre().equals(nombreInventoActual)){
						inventoActual = inventos[index];
						return;
					}
				}
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
		ModuloEntidad.obtenerModulo().buscarInventos(this);
	}
    private void actualizarSpinners(){
		//MEJORAR!!!!!!!!!! <--- <--- <--- <--- <--- <--- <--- <--- <--- <--- <--- <--- <--- <--- <--- <--- <---
		List<String> spinnerArray =  new ArrayList<String>();
		if(inventos != null){
			//se llena el array con los inventosCargados
			for (int i = 0; i < inventos.length; i++){
				spinnerArray.add(inventos[i].getNombre());
			}
		}
	
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinnerArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		inventoSpinner.setAdapter(adapter);
	
		String nombreInventoActual = (String)inventoSpinner.getSelectedItem();
		for (int index = 0; index < inventos.length; index++){
			if(inventos[index].getNombre().equals(nombreInventoActual)){
				inventoActual = inventos[index];
				return;
			}
		}
	}
	
    public void update(Guardable[] g,int request, String respuesta) {
        if(notificacion.isLoadingShowing()){
			switch (respuesta){
				case ControlDB.res_exito:
					if(g != null){
						switch (request) {
							case ModuloEntidad.RQS_BUSQUEDA_INVENTOS_TOTAL:
								if(g[0] instanceof Invento){
									inventos = (Invento[]) g;
								}
								break;
						}
						actualizarSpinners();
						notificacion.loadingDismiss();
					}else if(request == ModuloEntidad.RQS_BAJA_INVENTO){
						notificacion.loadingDismiss();
						notificacion.mostarNotificacion("Elemento eliminado correctamente");
						runOnUiThread(new Runnable() {
							public void run() {
								onBackPressed();
							}
						});
					}
					break;
				case ControlDB.res_falloConexion:
					notificacion.loadingDismiss();
					//Creamos un alertDialog en el Thread UI del activity
					notificacion.mostrarError(ControlDB.res_falloConexion);
					break;
				case ControlDB.res_tablaInventoVacio:
					notificacion.loadingDismiss();
					//Creamos un alertDialog en el Thread UI del activity
					notificacion.mostrarError(ControlDB.res_tablaInventoVacio);
					break;
			}
		}
    }
}
