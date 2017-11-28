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
import com.a000webhostapp.mymuseum.Vista.InventoABM.NuevoInventoActivity;
import com.a000webhostapp.mymuseum.Vista.PinturaABM.NuevaPinturaActivity;

/**
 * Created by Alexis on 23/10/2017.
 */

public class ElegirNuevoObjetoActivity extends AppCompatActivity {
	private Spinner spinnerObjetos;
	private Button save;
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_elegir_nuevo_objeto);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		
		spinnerObjetos = (Spinner) findViewById(R.id.SpinnerObjetos_ElegirNuevoObjeto);
		actualizarSpinnerPersonas();
		
		save = (Button) findViewById(R.id.Save_ElegirNuevoObjeto);
		save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent;
				switch ((String) spinnerObjetos.getSelectedItem()){
					case ControlDB.str_obj_Invento:
						intent = new Intent(ElegirNuevoObjetoActivity.this, NuevoInventoActivity.class);
						startActivity(intent);
						finish();
						break;
					case ControlDB.str_obj_Pintura:
						intent = new Intent(ElegirNuevoObjetoActivity.this, NuevaPinturaActivity.class);
						startActivity(intent);
						finish();
						break;
				}
			}
		});
	}
	private void actualizarSpinnerPersonas() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, ControlDB.objetos);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerObjetos.setAdapter(adapter);
	}
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
	}
}
