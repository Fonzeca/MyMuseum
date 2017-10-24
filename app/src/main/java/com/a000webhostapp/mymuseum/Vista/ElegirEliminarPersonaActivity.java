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

/**
 * Created by Alexis on 23/10/2017.
 */

public class ElegirEliminarPersonaActivity extends AppCompatActivity {
	private Spinner spinnerPersonas;
	private Button save;
	
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_elegir_eliminar_persona);
		
		spinnerPersonas = (Spinner) findViewById(R.id.SpinnerPersonas_ElegirEliminarPersona);
		actualizarSpinnerPersonas();
		
		save = (Button) findViewById(R.id.Save_ElegirEliminarPersona);
		save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent;
				switch ((String)spinnerPersonas.getSelectedItem()){
					case ControlDB.str_per_Inventor:
						intent = new Intent(ElegirEliminarPersonaActivity.this, EliminarInventorActivity.class);
						startActivity(intent);
						finish();
						break;
					case ControlDB.str_per_Pintor:
						
						finish();
						break;
				}
			}
		});
		
	}
	private void actualizarSpinnerPersonas(){
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, ControlDB.personas);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerPersonas.setAdapter(adapter);
	}
}
