package com.a000webhostapp.mymuseum.Vista;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.IObserver;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.Modelo.Invento;
import com.a000webhostapp.mymuseum.Modelo.Inventor;
import com.a000webhostapp.mymuseum.R;

import java.util.ArrayList;
import java.util.List;

public class EliminarInventorActivity extends AppCompatActivity implements IObserver {
    private Button eliminar;
    private Spinner inventorSpinner;
	
	private Inventor inventorActual;
	private Inventor[] inventores;
	
	private ProgressDialog loading;
	private boolean buscando, cargado;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_inventor);
	
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
	
		eliminar = (Button) findViewById(R.id.Save_EliminarInventor);
		eliminar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				ModuloEntidad.obtenerModulo().eliminarInventor(inventorActual.getID());
				onBackPressed();
			}
		});
	
		inventorSpinner = (Spinner)findViewById(R.id.spinnerInventor_EliminarInventor);
		inventorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				String nombreInventoActual = (String)inventorSpinner.getSelectedItem();
				for (int index = 0; index < inventores.length; index++){
					if(inventores[index].getNombreCompleto().equals(nombreInventoActual)){
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
		buscando = true;
		loading = new ProgressDialog(this){
			public void onBackPressed() {
				if(isShowing()){
					dismiss();
					buscando = false;
				}else{
					super.onBackPressed();
				}
			}
		};
		loading.setMessage("Espere un momento...");
		loading.setCancelable(false);
		loading.show();
		
		ModuloEntidad.obtenerModulo().buscarInventores(this);
	}
	private void actualizarSpinners(){
		List<String> spinnerArray =  new ArrayList<String>();
		if(inventores != null){
			//se llena el array con los inventosCargados
			for (int i = 0; i < inventores.length; i++){
				spinnerArray.add(inventores[i].getNombreCompleto());
			}
		}
		
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinnerArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		inventorSpinner.setAdapter(adapter);
		
		
		String nombreInventoActual = (String)inventorSpinner.getSelectedItem();
		for (int index = 0; index < inventores.length; index++){
			if(inventores[index].getNombreCompleto().equals(nombreInventoActual)){
				inventorActual = inventores[index];
				return;
			}
		}
	}
	
	public void update(Guardable[] g, int id) {
		if(buscando){
			if(g != null){
				if(g[0] instanceof Inventor){
					inventores = (Inventor[]) g;
					buscando = false;
					cargado = true;
					actualizarSpinners();
					loading.dismiss();
				}
			}else if(id == -1){
				loading.dismiss();
				new DialogoAlerta(this,"No se pudo conectar", "Error").mostrar();
			}
		}
	}
}
