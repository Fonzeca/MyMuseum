package com.a000webhostapp.mymuseum.Vista.PintorABM;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.IObserver;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.Modelo.Pintor;
import com.a000webhostapp.mymuseum.R;
import com.a000webhostapp.mymuseum.Vista.DialogoAlerta;

public class EditarPintorActivity extends AppCompatActivity implements IObserver{
    private EditText nombre, año, lugar;
    private CheckBox AC;
    private Button guardar;
	private Spinner pintoresSpinner;

	
    private Pintor pintorActual;
	
	private Pintor[] pintores;
	
	
	private ProgressDialog loading;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_pintor);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		
		
		nombre = (EditText) findViewById(R.id.nomyapePintor_EditPintor);
        año = (EditText) findViewById(R.id.añoNacPintor_EditPintor);
        lugar = (EditText) findViewById(R.id.lugarNacPintor_EditPintor);

        AC = (CheckBox) findViewById(R.id.ACPintor_EditPintor);
		
		pintoresSpinner = (Spinner) findViewById(R.id.spinnerPintor_EditPintor);
		pintoresSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				pintorActual = (Pintor) pintoresSpinner.getSelectedItem();
				actualizarCampos();
			}
			public void onNothingSelected(AdapterView<?> adapterView) {
			}
		});
		buscarInfoSpinner();
		

        guardar = (Button)findViewById(R.id.Save_EditPintor);
        guardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
				String nombreyapellido = nombre.getText().toString();
				String lugarConfig = lugar.getText().toString();
				int añoPintor;
				if(!nombreyapellido.equals("") && !lugarConfig.equals("") && !año.getText().toString().equals("")){
					if(AC.isChecked()){
						añoPintor = Integer.parseInt("-" + año.getText().toString());
					}else{
						añoPintor = Integer.parseInt(año.getText().toString());
					}
					pintorActual.setNombre(nombreyapellido);
					pintorActual.setAñoNacimiento(añoPintor);
					pintorActual.setLugarNacimiento(lugarConfig);
					ModuloEntidad.obtenerModulo().editarPintor(pintorActual);
					onBackPressed();
				}
            }
        });
		

    }
    private void actualizarCampos(){
		nombre.setText(pintorActual.getNombre());
		if(pintorActual.getAñoNacimiento() < 0){
			año.setText(String.valueOf(Math.abs(pintorActual.getAñoNacimiento())));
			AC.setChecked(true);
		}else{
			año.setText(String.valueOf(pintorActual.getAñoNacimiento()));
		}
		lugar.setText(pintorActual.getLugarNacimiento());
	}
	private void actualizarSpinnerPintores(){
		if(pintores != null){
			ArrayAdapter<Pintor> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, pintores);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			pintoresSpinner.setAdapter(adapter);
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
	
		ModuloEntidad.obtenerModulo().buscarPintores(this);
	}
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
	}
	
	public void update(Guardable[] g, String respuesta) {
		if(loading.isShowing()){
			switch (respuesta){
				case ControlDB.res_exitoBusqueda:
					if(g != null){
						if(g[0] instanceof Pintor){
							pintores = (Pintor[]) g;
							actualizarSpinnerPintores();
							loading.dismiss();
						}
					}
					break;
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
