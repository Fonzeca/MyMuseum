package com.a000webhostapp.mymuseum.Vista.PinturaABM;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.IObserver;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.Modelo.Periodo;
import com.a000webhostapp.mymuseum.Modelo.Pintor;
import com.a000webhostapp.mymuseum.R;
import com.a000webhostapp.mymuseum.Vista.DialogoAlerta;
import com.a000webhostapp.mymuseum.Vista.PeriodoABM.NuevoPeriodoActivity;
import com.a000webhostapp.mymuseum.Vista.PintorABM.NuevoPintorActivity;

/**
 * Created by Alexis on 10/10/2017.
 */

public class NuevaPinturaActivity extends AppCompatActivity implements IObserver {
	private LinearLayout agregarPintor, agregarPeriodo;
	
	private TextView nombrePintura, descripcion, añoCreacion;
	private Spinner nombrePeriodoSpinner, nombrePintorSpinner;
	private CheckBox ACPintura;
	private Button guardar;
	
	private Pintor[] pintores;
	private Periodo[] periodos;
	
	private Pintor pintorActual;
	private Periodo periodoActual;
	
	private ProgressDialog loading;
	
	private View.OnClickListener clickListenerBotones;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nueva_pintura);
		buscarInfoSpinners();
		
		clickListenerBotones = new View.OnClickListener() {
			public void onClick(View view) {
				int id = view.getId();
				switch (id){
					case R.id.NewPeri_NuevaPintura:
						startNuevoPeriodoActivity();
						break;
					case R.id.NewPintor_NuevaPintura:
						startNuevoPintorActivity();
						break;
					case R.id.Save_NuevaPintura:
						guardarInformacion();
						break;
				}
			}
		};
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		
		agregarPintor = (LinearLayout)findViewById(R.id.NewPintor_NuevaPintura);
		agregarPintor.setOnClickListener(clickListenerBotones);
		
		agregarPeriodo = (LinearLayout)findViewById(R.id.NewPeri_NuevaPintura);
		agregarPeriodo.setOnClickListener(clickListenerBotones);
		
		nombrePintura = (TextView)findViewById(R.id.NomPintura_NuevaPintura);
		descripcion = (TextView)findViewById(R.id.DescriPintura_NuevaPintura);
		añoCreacion = (TextView)findViewById(R.id.AñoCreacion_NuevaPintura);
		
		
		nombrePeriodoSpinner = (Spinner)findViewById(R.id.spinnerPeri_NuevaPintura);
		nombrePeriodoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				periodoActual = (Periodo)nombrePeriodoSpinner.getSelectedItem();
			}
			public void onNothingSelected(AdapterView<?> adapterView) {
			}
		});
		
		nombrePintorSpinner = (Spinner)findViewById(R.id.spinnerPintores_NuevaPintura);
		nombrePintorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				pintorActual = (Pintor)nombrePintorSpinner.getSelectedItem();
			}
			public void onNothingSelected(AdapterView<?> adapterView) {
			}
		});
		
		
		ACPintura = (CheckBox)findViewById(R.id.Ac_NuevaPintura);
		
		guardar = (Button)findViewById(R.id.Save_NuevaPintura);
		guardar.setOnClickListener(clickListenerBotones);
		
	}
	private void actualizarSpinnerPintores(){
		if(pintores != null){
			//Si bien guardamos inventores en el array, se muestro el toString de cada inventor.
			ArrayAdapter<Pintor> adapter = new ArrayAdapter<Pintor>(this, R.layout.support_simple_spinner_dropdown_item, pintores);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			nombrePintorSpinner.setAdapter(adapter);
		}
	}
	private void actualizarSpinnerPeriodo(){
		if(periodos != null){
			//Si bien guardamos peridoos en el array, se muestro el toString de cada periodo.
			ArrayAdapter<Periodo> adapter = new ArrayAdapter<Periodo>(this, R.layout.support_simple_spinner_dropdown_item, periodos);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			nombrePeriodoSpinner.setAdapter(adapter);
		}
		
	}
	private void guardarInformacion(){
		String nombreGuar = nombrePintura.getText().toString();
		String descriGuar = descripcion.getText().toString();
		String añoTextGuar = añoCreacion.getText().toString();
		if(!nombreGuar.equals("") && !descriGuar.equals("") && !añoTextGuar.equals("")){
			int añoGuar;
			if(ACPintura.isChecked()){
				añoGuar = Integer.parseInt("-" + añoTextGuar);
			}else{
				añoGuar = Integer.parseInt(añoTextGuar);
			}
			
			ModuloEntidad.obtenerModulo().crearPintura(nombreGuar,descriGuar,periodoActual, pintorActual,
					añoGuar);
			onBackPressed();
		}
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
		
		ModuloEntidad.obtenerModulo().buscarPintores(this);
		ModuloEntidad.obtenerModulo().buscarPeriodos(this);
	}
	
	protected void onRestart() {
		super.onRestart();
		buscarInfoSpinners();
	}
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
	}
	
	private void startNuevoPintorActivity() {
		Intent intent = new Intent(this, NuevoPintorActivity.class);
		startActivity(intent);
	}
	private void startNuevoPeriodoActivity() {
		Intent intent = new Intent(this, NuevoPeriodoActivity.class);
		startActivity(intent);
	}
	
	public void update(Guardable[] g, String respuesta) {
		if(loading.isShowing()){
			switch (respuesta){
				case ControlDB.res_exitoBusqueda:
					if(g != null){
						if(g[0] instanceof Pintor){
							pintores = (Pintor[]) g;
							actualizarSpinnerPintores();
						}else if(g[0] instanceof Periodo){
							periodos = (Periodo[]) g;
							actualizarSpinnerPeriodo();
						}
						if(pintores != null && periodos != null){
							loading.dismiss();
						}
					}
					break;
				case ControlDB.res_falloConexion:
					loading.dismiss();
					//Creamos un alertDialog en el Thread UI del activity
					new DialogoAlerta(this, ControlDB.res_falloConexion, "Error").mostrar();
					break;
				case ControlDB.res_tablaPintoresVacio:
					loading.dismiss();
					//Creamos un alertDialog en el Thread UI del activity
					new DialogoAlerta(this, ControlDB.res_tablaPintoresVacio, "Error").mostrar();
					break;
				case ControlDB.res_tablaPeriodoVacio:
					loading.dismiss();
					//Creamos un alertDialog en el Thread UI del activity
					new DialogoAlerta(this, ControlDB.res_tablaPeriodoVacio, "Error").mostrar();
					break;
				
			}
		}
	}
}
