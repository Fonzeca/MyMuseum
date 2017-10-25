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
import com.a000webhostapp.mymuseum.Vista.DialogoAlerta;

import java.util.ArrayList;
import java.util.List;

public class EliminarInventoActivity extends AppCompatActivity implements IObserver {
	private Button eliminar;
	private Spinner inventoSpinner;
	
	private Invento inventoActual;
	private Invento[] inventos;
	
	private ProgressDialog loading;
	private boolean cargado;
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_invento);
	
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		
		eliminar = (Button) findViewById(R.id.Save_EliminarInvento);
		eliminar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				ModuloEntidad.obtenerModulo().eliminarInvento(inventoActual.getID());
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
		loading = new ProgressDialog(this){
			public void onBackPressed() {
				if(isShowing()){
					dismiss();
				}else{
					super.onBackPressed();
				}
			}
		};
		loading.setMessage("Espere un momento...");
		loading.setCancelable(false);
		loading.show();
		
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
	
    public void update(Guardable[] g, String respuesta) {
        if(loading.isShowing()){
			if(g != null){
				if(g[0] instanceof Invento){
					inventos = (Invento[]) g;
					cargado = true;
					actualizarSpinners();
					loading.dismiss();
				}
			}
			if(respuesta != null && !respuesta.equals("")){
				switch (respuesta){
					case ControlDB.res_falloConexion:
						loading.dismiss();
						//Creamos un alertDialog en el Thread UI del activity
						new DialogoAlerta(this, ControlDB.res_falloConexion, "Error").mostrar();
						break;
					case ControlDB.res_tablaInventoVacio:
						loading.dismiss();
						//Creamos un alertDialog en el Thread UI del activity
						new DialogoAlerta(this, ControlDB.res_tablaInventoVacio, "Error").mostrar();
						break;
			
				}
			}
		}
    }
}
