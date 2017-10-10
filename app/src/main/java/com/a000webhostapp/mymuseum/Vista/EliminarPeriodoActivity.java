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
import com.a000webhostapp.mymuseum.Modelo.Inventor;
import com.a000webhostapp.mymuseum.Modelo.Periodo;
import com.a000webhostapp.mymuseum.R;

import java.util.ArrayList;
import java.util.List;

public class EliminarPeriodoActivity extends AppCompatActivity implements IObserver{
    private Button eliminar;
    private Spinner periodoSpinner;
    
    private Periodo periodoActual;
    private Periodo[] periodos;
    
    private ProgressDialog loading;
    private boolean cargado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_periodo);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		
		
		eliminar = (Button) findViewById(R.id.Save_EliminarPeriodo);
		eliminar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				ModuloEntidad.obtenerModulo().eliminarPeriodo(periodoActual.getID());
				onBackPressed();
			}
		});
	
		periodoSpinner = (Spinner)findViewById(R.id.spinnerPeriodo_EliminarPeriodo);
		periodoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				String nombrePeriodoActual = (String)periodoSpinner.getSelectedItem();
				for (int index = 0; index < periodos.length; index++){
					if(periodos[index].getNombrePeriodo().equals(nombrePeriodoActual)){
						periodoActual = periodos[index];
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
		
		ModuloEntidad.obtenerModulo().buscarPeriodos(this);
	}
	
	private void actualizarSpinners(){
		List<String> spinnerArray =  new ArrayList<String>();
		if(periodos != null){
			//se llena el array con los inventosCargados
			for (int i = 0; i < periodos.length; i++){
				spinnerArray.add(periodos[i].getNombrePeriodo());
			}
		}
		
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinnerArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		periodoSpinner.setAdapter(adapter);
		
		
		String nombrePeriodoActual = (String)periodoSpinner.getSelectedItem();
		for (int index = 0; index < periodos.length; index++){
			if(periodos[index].getNombrePeriodo().equals(nombrePeriodoActual)){
				periodoActual = periodos[index];
				return;
			}
		}
	}
    
	public void update(Guardable[] g, int id) {
		if(loading.isShowing()){
			if(g != null){
				if(g[0] instanceof Periodo){
					periodos = (Periodo[]) g;
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
