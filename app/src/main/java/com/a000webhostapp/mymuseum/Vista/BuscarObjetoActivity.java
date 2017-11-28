package com.a000webhostapp.mymuseum.Vista;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.Controlador.RequestBusqueda;
import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.Observers.IObserverEntidad;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.Modelo.Inventor;
import com.a000webhostapp.mymuseum.Modelo.Periodo;
import com.a000webhostapp.mymuseum.Modelo.Pintor;
import com.a000webhostapp.mymuseum.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuscarObjetoActivity extends AppCompatActivity implements IObserverEntidad {
	private Button save;
	private Spinner objetosSpinner;
	private EditText nombreObjetoEdit;
	
	private TextView btnMasOpciones, viewPersona;
	private boolean masOpciones;
	private LinearLayout zonaMasOpciones;
	private Spinner spinnerPeriodos, spinnerPersona;
	
	private ModuloNotificacion notificacion;
	
	private Periodo[] periodos;
	private Inventor[] inventores;
	private Pintor[] pintores;
	
	
	public static final int requestPermisionCamera = 1;
	public static final int requestQR = 4;
	public static final int responseScannerQR = 5;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buscar_objeto);
		
		notificacion = new ModuloNotificacion(this);
		
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
							
							RequestBusqueda req = prepararRequestBusqueda();
							data.putExtra("Request", req);
							
							setResult(RESULT_OK,data);
							finish();
						}
						break;
					case ControlDB.str_obj_Pintura:
						if(!nombreObjetoEdit.getText().toString().isEmpty()){
							Intent data = new Intent();
							
							RequestBusqueda req = prepararRequestBusqueda();
							data.putExtra("Request", req);
							
							setResult(RESULT_OK,data);
							finish();
						}
						break;
				}
				
				onBackPressed();
			}
		});
		
		objetosSpinner = (Spinner) findViewById(R.id.spinnerObjeto_BuscarObjeto);
		objetosSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				viewPersona.setText(ControlDB.personas[i]);
				actualizarSpinnerPersona();
			}
			public void onNothingSelected(AdapterView<?> adapterView) {
			}
		});
		
		btnMasOpciones = (TextView) findViewById(R.id.boton_mas_opciones_BuscarObjeto);
		btnMasOpciones.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				masOpciones = !masOpciones;
				if(masOpciones){
					btnMasOpciones.setText("Mostrar menos opciones");
					zonaMasOpciones.setVisibility(View.VISIBLE);
				}else{
					btnMasOpciones.setText("Mostrar más opciones");
					zonaMasOpciones.setVisibility(View.GONE);
				}
			}
		});
		
		zonaMasOpciones = (LinearLayout) findViewById(R.id.layout_mas_opciones_BuscarObjeto);
		
		viewPersona = (TextView) findViewById(R.id.view_persona_BuscarObjeto);
		
		spinnerPeriodos = (Spinner)findViewById(R.id.spinner_periodos_BuscarObjeto);
		
		spinnerPersona = (Spinner)findViewById(R.id.spinner_persona_BuscarObjeto);
		
		zonaMasOpciones.setVisibility(View.GONE);
		buscarInfoSpinners();
		actualizarSpinnerObjetos();
		
	}
	private RequestBusqueda prepararRequestBusqueda(){
		RequestBusqueda requestBusqueda = null;
		switch ((String)objetosSpinner.getSelectedItem()){
			case ControlDB.str_obj_Invento:
				String nom = nombreObjetoEdit.getText().toString();
				
				Periodo per = (Periodo) spinnerPeriodos.getSelectedItem();
				if(spinnerPeriodos.getSelectedItemPosition() == 0){
					per = null;
				}
				Inventor inven = (Inventor) spinnerPersona.getSelectedItem();
				if(spinnerPersona.getSelectedItemPosition() == 0){
					inven = null;
				}
				
				requestBusqueda = new RequestBusqueda(ModuloEntidad.RQS_BUSQUEDA_INVENTOS_REFINADO,nom,per,inven);
				
				break;
			case ControlDB.str_obj_Pintura:
				nom = nombreObjetoEdit.getText().toString();
				
				per = (Periodo) spinnerPeriodos.getSelectedItem();
				if(spinnerPeriodos.getSelectedItemPosition() == 0){
					per = null;
				}
				Pintor pin = (Pintor) spinnerPersona.getSelectedItem();
				if(spinnerPersona.getSelectedItemPosition() == 0){
					pin = null;
				}
				
				requestBusqueda = new RequestBusqueda(ModuloEntidad.RQS_BUSQUEDA_PINTURAS_REFINADO,nom,per,pin);
				break;
			
		}
		return requestBusqueda;
	}
	
	private void actualizarSpinnerObjetos(){
		List<String> spinnerArray =  new ArrayList<String>(Arrays.asList(ControlDB.objetos));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinnerArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		objetosSpinner.setAdapter(adapter);
	}
	private void actualizarSpinnerPeriodos(){
		List<Periodo> spinnerArray =  new ArrayList<>(Arrays.asList(periodos));
		spinnerArray.add(0,new Periodo("Cualquiera",0,0));
		ArrayAdapter<Periodo> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, spinnerArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerPeriodos.setAdapter(adapter);
	}
	
	private void actualizarSpinnerPersona(){
		switch (objetosSpinner.getSelectedItem().toString()){
			case ControlDB.str_obj_Invento:
				if(inventores != null){
					List<Inventor> spinnerArrayInventores =  new ArrayList<>(Arrays.asList(inventores));
					spinnerArrayInventores.add(0,new Inventor("Cualquiera",null,0));
					ArrayAdapter<Inventor> adapterInventores = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, spinnerArrayInventores);
					adapterInventores.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spinnerPersona.setAdapter(adapterInventores);
					
				}
				break;
			case ControlDB.str_obj_Pintura:
				if(pintores != null){
					List<Pintor> spinnerArrayPintores =  new ArrayList<>(Arrays.asList(pintores));
					spinnerArrayPintores.add(0,new Pintor("Cualquiera",null,0));
					ArrayAdapter<Pintor> adapterPintores = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, spinnerArrayPintores);
					adapterPintores.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spinnerPersona.setAdapter(adapterPintores);
				}
				break;
		}
		
		
		
		
	}
	private void buscarInfoSpinners(){
		notificacion.mostrarLoading();
		ModuloEntidad.obtenerModulo().buscarPeriodos(this);
		ModuloEntidad.obtenerModulo().buscarInventores(this);
		ModuloEntidad.obtenerModulo().buscarPintores(this);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == requestQR && resultCode == RESULT_OK){
			setResult(responseScannerQR,data);
			finish();
		}
	}
	
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.articulo_invento_menu_qr, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.busqueda_escanearQR:
				escanearQR();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	private void escanearQR(){
		if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, requestPermisionCamera);
			
		}else{
			Intent intent = new Intent(this, LectorQRActivity.class);
			startActivityForResult(intent,requestQR);
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode){
			case requestPermisionCamera:
				if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
					Intent intent = new Intent(this, LectorQRActivity.class);
					startActivityForResult(intent,requestQR);
				}else{
					Toast.makeText(this, "Por favor, garantizar el permiso de la camara o sino no te va a andar", Toast.LENGTH_SHORT).show();
				}
				break;
		}
	}
	
	public void update(Guardable[] g, int request, String respuesta) {
		if(notificacion.isLoadingShowing()){
			switch (respuesta){
				case ControlDB.res_exito:
					if(g != null){
						switch (request){
							case ModuloEntidad.RQS_BUSQUEDA_PERIODOS_TOTAL:
								if(g[0] instanceof Periodo){
									periodos = (Periodo[]) g;
									actualizarSpinnerPeriodos();
								}
								break;
							case ModuloEntidad.RQS_BUSQUEDA_INVENTORES_TOTAL:
								if(g[0] instanceof Inventor){
									inventores = (Inventor[]) g;
									actualizarSpinnerPersona();
								}
								break;
							case ModuloEntidad.RQS_BUSQUEDA_PINTORES_TOTAL:
								if(g[0] instanceof Pintor){
									pintores = (Pintor[]) g;
									actualizarSpinnerPersona();
								}
								break;
						}
						if(periodos != null && inventores != null && pintores != null){
							notificacion.loadingDismiss();
						}
					}
					break;
				default:
					notificacion.loadingDismiss();
					notificacion.mostrarError(respuesta);
					break;
			}
		}
	}
}
