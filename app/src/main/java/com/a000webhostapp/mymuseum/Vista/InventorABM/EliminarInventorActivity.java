package com.a000webhostapp.mymuseum.Vista.InventorABM;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.Observers.IObserverEntidad;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.Modelo.Inventor;
import com.a000webhostapp.mymuseum.R;
import com.a000webhostapp.mymuseum.Vista.ModuloNotificacion;

import java.util.ArrayList;
import java.util.List;

public class EliminarInventorActivity extends AppCompatActivity implements IObserverEntidad {
    private Button eliminar;
    private Spinner inventorSpinner;
	
	private Inventor inventorActual;
	private Inventor[] inventores;
	
	private ModuloNotificacion notificacion;
	private boolean cargado;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_inventor);
		notificacion = new ModuloNotificacion(this);
	
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
	
		eliminar = (Button) findViewById(R.id.Save_EliminarInventor);
		eliminar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				ModuloEntidad.obtenerModulo().eliminarInventor(inventorActual.getID(),EliminarInventorActivity.this);
				notificacion.mostrarLoading();
				eliminar.setEnabled(false);
			}
		});
	
		inventorSpinner = (Spinner)findViewById(R.id.spinnerInventor_EliminarInventor);
		inventorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				String nombreInventoActual = (String)inventorSpinner.getSelectedItem();
				for (int index = 0; index < inventores.length; index++){
					if(inventores[index].getNombre().equals(nombreInventoActual)){
						inventorActual = inventores[index];
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
		
		ModuloEntidad.obtenerModulo().buscarInventores(this);
	}
	private void actualizarSpinners(){
		List<String> spinnerArray =  new ArrayList<String>();
		if(inventores != null){
			//se llena el array con los inventosCargados
			for (int i = 0; i < inventores.length; i++){
				spinnerArray.add(inventores[i].getNombre());
			}
		}
		
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinnerArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		inventorSpinner.setAdapter(adapter);
		
		
		String nombreInventoActual = (String)inventorSpinner.getSelectedItem();
		for (int index = 0; index < inventores.length; index++){
			if(inventores[index].getNombre().equals(nombreInventoActual)){
				inventorActual = inventores[index];
				return;
			}
		}
	}
	
	public void update(Guardable[] g,int request, String respuesta) {
		if(notificacion.isLoadingShowing()){
			switch (respuesta){
				case ControlDB.res_exito:
					if(g != null){
						switch (request){
							case ModuloEntidad.RQS_BUSQUEDA_INVENTORES_TOTAL:
								if(g[0] instanceof Inventor){
									inventores = (Inventor[]) g;
								}
								break;
						}
						actualizarSpinners();
						notificacion.loadingDismiss();
					}else if(request == ModuloEntidad.RQS_BAJA_INVENTOR){
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
				case ControlDB.res_tablaInventorVacio:
					notificacion.loadingDismiss();
					//Creamos un alertDialog en el Thread UI del activity
					notificacion.mostrarError(ControlDB.res_tablaInventorVacio);
					break;
			}
		}
	}
}
