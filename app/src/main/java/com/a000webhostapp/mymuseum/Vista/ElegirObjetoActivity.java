package com.a000webhostapp.mymuseum.Vista;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.IObserver;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.Modelo.Objeto;
import com.a000webhostapp.mymuseum.Modelo.Periodo;
import com.a000webhostapp.mymuseum.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ElegirObjetoActivity extends AppCompatActivity {
	private Button save;
	private Spinner objetosSpinner;
	
	
	private ProgressDialog loading;
	private boolean cargado;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_elegir_objeto);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		
		save = (Button) findViewById(R.id.Save_ElegirObjeto);
		save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				switch ((String)objetosSpinner.getSelectedItem()){
					case ControlDB.str_obj_Invento:
						startNuevoInventoActivity();
						break;
					case ControlDB.str_obj_Pintura:
						
						break;
				}
				
				onBackPressed();
			}
		});
		
		objetosSpinner = (Spinner) findViewById(R.id.spinnerObjeto_ElegirObjeto);
		
		actualizarSpinnerObjetos();
		
	}
	private void actualizarSpinnerObjetos(){
		List<String> spinnerArray =  new ArrayList<String>(Arrays.asList(ControlDB.objetos));
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinnerArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		objetosSpinner.setAdapter(adapter);
	}
	
	private void startNuevoInventoActivity() {
		Intent intent = new Intent(this, NuevoInventoActivity.class);
		startActivity(intent);
	}
	
}
