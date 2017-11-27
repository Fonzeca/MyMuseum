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
import com.a000webhostapp.mymuseum.Vista.ModuloNotificacion;

public class EditarPintorActivity extends AppCompatActivity implements IObserver{
    private EditText nombre, año, lugar;
    private CheckBox AC;
    private Button guardar;
	private Spinner pintoresSpinner;

	
    private Pintor pintorActual;
	
	private Pintor[] pintores;
	
	
	private ModuloNotificacion notificacion;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_pintor);
	
		notificacion = new ModuloNotificacion(this);
		
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
					ModuloEntidad.obtenerModulo().editarPintor(pintorActual,EditarPintorActivity.this);
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
		notificacion.mostrarLoading();
	
		ModuloEntidad.obtenerModulo().buscarPintores(this);
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
						switch (request) {
							case ModuloEntidad.RQS_BUSQUEDA_PINTORES_TOTAL:
								if(g[0] instanceof Pintor){
									pintores = (Pintor[]) g;
								}
								break;
						}
						actualizarSpinnerPintores();
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
