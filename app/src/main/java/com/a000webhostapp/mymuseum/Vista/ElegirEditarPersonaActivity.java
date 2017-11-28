package com.a000webhostapp.mymuseum.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.R;
import com.a000webhostapp.mymuseum.Vista.InventorABM.EditarInventorActivity;
import com.a000webhostapp.mymuseum.Vista.PintorABM.EditarPintorActivity;

/**
 * Created by Alexis on 23/10/2017.
 */

public class ElegirEditarPersonaActivity extends AppCompatActivity {
	private Spinner spinnerPersonas;
	private Button save;
	
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_elegir_editar_persona);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		
		
		spinnerPersonas = (Spinner) findViewById(R.id.SpinnerPersonas_ElegirEditarPersona);
		
		
		actualizarSpinnerPersonas();
		
		save = (Button) findViewById(R.id.Save_ElegirEditarPersona);
		save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent;
				switch ((String)spinnerPersonas.getSelectedItem()){
					case ControlDB.str_per_Inventor:
						intent = new Intent(ElegirEditarPersonaActivity.this, EditarInventorActivity.class);
						startActivity(intent);
						finish();
						break;
					case ControlDB.str_per_Pintor:
						intent = new Intent(ElegirEditarPersonaActivity.this, EditarPintorActivity.class);
						startActivity(intent);
						finish();
						break;
				}
			}
		});
	}
	private void actualizarSpinnerPersonas() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, ControlDB.personas);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerPersonas.setAdapter(adapter);
	}
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
	}
}
