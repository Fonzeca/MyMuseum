package com.a000webhostapp.mymuseum.Vista;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuscarObjetoActivity extends AppCompatActivity {
	private Button save;
	private Spinner objetosSpinner;
	private EditText nombreObjetoEdit;
	
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buscar_objeto);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		
		nombreObjetoEdit = (EditText)findViewById(R.id.NomObjeto_BuscarObjeto);
		
		save = (Button) findViewById(R.id.Save_BuscarObjeto);
		
		save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				switch ((String)objetosSpinner.getSelectedItem()){
					case ControlDB.str_obj_Invento:
						if(!nombreObjetoEdit.getText().toString().isEmpty()){
							Intent data = new Intent();
							data.setData(Uri.parse(ControlDB.str_obj_Invento + "=" + nombreObjetoEdit.getText()));
							setResult(RESULT_OK,data);
							finish();
						}
						break;
					case ControlDB.str_obj_Pintura:
						
						break;
				}
				
				onBackPressed();
			}
		});
		
		objetosSpinner = (Spinner) findViewById(R.id.spinnerObjeto_BuscarObjeto);
		
		actualizarSpinnerObjetos();
		
	}
	private void actualizarSpinnerObjetos(){
		List<String> spinnerArray =  new ArrayList<String>(Arrays.asList(ControlDB.objetos));
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinnerArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		objetosSpinner.setAdapter(adapter);
	}
	
	
}
