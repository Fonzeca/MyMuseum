package com.a000webhostapp.mymuseum.Vista.InventorABM;

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
import com.a000webhostapp.mymuseum.Modelo.Invento;
import com.a000webhostapp.mymuseum.Modelo.Inventor;
import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.R;
import com.a000webhostapp.mymuseum.Vista.ModuloNotificacion;

import java.util.ArrayList;
import java.util.List;

public class EditarInventorActivity extends AppCompatActivity implements IObserver{
    private EditText nombre, año, lugar;
    private CheckBox AC;
    private Button guardar;
	private Spinner inventoresSpin;

	
    private Inventor inventorActual;
	
	private Inventor[] inventores;
	
	
	private ModuloNotificacion notificacion;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_inventor);
	
		notificacion = new ModuloNotificacion(this);
		
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
					ModuloEntidad.obtenerModulo().editarInventor(inventorActual,EditarInventorActivity.this);
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
		notificacion.mostrarLoading();
	
		ModuloEntidad.obtenerModulo().buscarInventores(this);
	}
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
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
						actualizarSpinnerInventores();
						notificacion.loadingDismiss();
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
