package com.a000webhostapp.mymuseum.Vista;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.IObserver;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.Modelo.Inventor;
import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.R;

import java.util.ArrayList;
import java.util.List;

public class EditarInventorActivity extends AppCompatActivity implements IObserver{
    private EditText nombre, año, lugar;
    private CheckBox AC;
    private Button guardar;
	private Spinner inventoresSpin;

	
    private Inventor inventorActual;
	
	private Inventor[] inventores;
	
	
	private ProgressDialog loading;
	private boolean cargado;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_inventor);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		
		
		nombre = (EditText) findViewById(R.id.nomyapeInventor_EditInventor);
        año = (EditText) findViewById(R.id.añoNacInventor_EditInventor);
        lugar = (EditText) findViewById(R.id.lugarNacInventor_EditInventor);

        AC = (CheckBox) findViewById(R.id.ACInventor_EditInventor);
		
		inventoresSpin = (Spinner) findViewById(R.id.spinnerInventor_EditInventor);
		inventoresSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				String nombreInventorActual = (String)inventoresSpin.getSelectedItem();
				for (int index = 0; index < inventores.length; index++){
					if(inventores[index].getNombre().equals(nombreInventorActual)){
						inventorActual = inventores[index];
						actualizarCampos();
						return;
					}
				}
			}
			public void onNothingSelected(AdapterView<?> adapterView) {
			}
		});
		buscarInfoSpinner();
		

        guardar = (Button)findViewById(R.id.Save_EditInventor);
        guardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
				String nombreyapellido = nombre.getText().toString();
				String lugarConfig = lugar.getText().toString();
				int añoInventor;
				if(!nombreyapellido.equals("") && !lugarConfig.equals("") && !año.getText().toString().equals("")){
					if(AC.isChecked()){
						añoInventor = Integer.parseInt("-" + año.getText().toString());
					}else{
						añoInventor = Integer.parseInt(año.getText().toString());
					}
					inventorActual.setNombre(nombreyapellido);
					inventorActual.setAñoNacimiento(añoInventor);
					inventorActual.setLugarNacimiento(lugarConfig);
					ModuloEntidad.obtenerModulo().editarInventor(inventorActual);
					onBackPressed();
				}
            }
        });
		

    }
    private void actualizarCampos(){
		nombre.setText(inventorActual.getNombre());
		if(inventorActual.getAñoNacimiento() < 0){
			año.setText(String.valueOf(Math.abs(inventorActual.getAñoNacimiento())));
			AC.setChecked(true);
		}else{
			año.setText(String.valueOf(inventorActual.getAñoNacimiento()));
		}
		lugar.setText(inventorActual.getLugarNacimiento());
	}
	private void actualizarSpinnerInventores(){
		List<String> spinnerArray =  new ArrayList<String>();
		if(inventores != null){
			//se llena el array con los invententores
			for (int i = 0; i < inventores.length; i++){
				spinnerArray.add(inventores[i].getNombre());
			}
		}
	
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinnerArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		inventoresSpin.setAdapter(adapter);
		
		//Buscamos para que sepan cual es el inventor actual
		String nombreInventorActual = (String)inventoresSpin.getSelectedItem();
		for (int index = 0; index < inventores.length; index++){
			if(inventores[index].getNombre().equals(nombreInventorActual)){
				inventorActual = inventores[index];
				actualizarCampos();
				return;
			}
		}
	}
	
	private void buscarInfoSpinner(){
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
	
		ModuloEntidad.obtenerModulo().buscarInventores(this);
	}
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
	}
	
	public void update(Guardable[] g, String respuesta) {
		if(loading.isShowing()){
			if(g != null){
				if(g[0] instanceof Inventor){
					inventores = (Inventor[]) g;
					actualizarSpinnerInventores();
					loading.dismiss();
					cargado = true;
				}
			}if(respuesta != null && !respuesta.equals("")){
				switch (respuesta){
					case ControlDB.res_falloConexion:
						loading.dismiss();
						//Creamos un alertDialog en el Thread UI del activity
						new DialogoAlerta(this, ControlDB.res_falloConexion, "Error").mostrar();
						break;
					case ControlDB.res_tablaInventorVacio:
						loading.dismiss();
						//Creamos un alertDialog en el Thread UI del activity
						new DialogoAlerta(this, ControlDB.res_tablaInventorVacio, "Error").mostrar();
						break;
				}
			}
		}
	}
}
