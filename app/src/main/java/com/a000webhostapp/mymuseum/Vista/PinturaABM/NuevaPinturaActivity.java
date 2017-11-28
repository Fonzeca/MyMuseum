package com.a000webhostapp.mymuseum.Vista.PinturaABM;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.Controlador.ModuloImagen;
import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.Observers.IObserverEntidad;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.Modelo.Imagen;
import com.a000webhostapp.mymuseum.Modelo.Periodo;
import com.a000webhostapp.mymuseum.Modelo.Pintor;
import com.a000webhostapp.mymuseum.R;
import com.a000webhostapp.mymuseum.Vista.ModuloNotificacion;
import com.a000webhostapp.mymuseum.Vista.PeriodoABM.NuevoPeriodoActivity;
import com.a000webhostapp.mymuseum.Vista.PintorABM.NuevoPintorActivity;

/**
 * Created by Alexis on 10/10/2017.
 */

public class NuevaPinturaActivity extends AppCompatActivity implements IObserverEntidad {
	private static final int RQS_BUSCARIMAGEN = 0;
	
	private LinearLayout agregarPintor, agregarPeriodo;
	
	private TextView nombrePintura, descripcion, añoCreacion;
	private Spinner nombrePeriodoSpinner, nombrePintorSpinner;
	private CheckBox ACPintura;
	private Button guardar;
	
	private Pintor[] pintores;
	private Periodo[] periodos;
	
	private Pintor pintorActual;
	private Periodo periodoActual;
	
	private ImageView imageView;
	private TextView imagenTextView;
	private Uri uriImagen;
	
	private ModuloNotificacion notificacion;
	
	private View.OnClickListener clickListenerBotones;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nueva_pintura);
		
		notificacion = new ModuloNotificacion(this);
		
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
					case R.id.buttonAgregarImagen_NuevaPintura:
						Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
						intent.addCategory(Intent.CATEGORY_OPENABLE);
						intent.setType("image/*");
						startActivityForResult(Intent.createChooser(intent,"Elegir imagen"),RQS_BUSCARIMAGEN);
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
		
		imageView = (ImageView) findViewById(R.id.imagenView_NuevaPintura);
		imageView.setOnClickListener(clickListenerBotones);
		
		imagenTextView = (TextView)findViewById(R.id.buttonAgregarImagen_NuevaPintura);
		imagenTextView.setOnClickListener(clickListenerBotones);
		
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
					añoGuar,this);
			if(uriImagen != null){
				ModuloImagen.obtenerModulo().insertarImagen(nombreGuar,ControlDB.str_obj_Pintura,this,uriImagen,this);
			}
			notificacion.mostrarLoading();
			guardar.setEnabled(false);
		}
	}
	private void buscarInfoSpinners(){
		notificacion.mostrarLoading();
		
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
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == RQS_BUSCARIMAGEN){
			uriImagen = data.getData();
			imageView.setImageBitmap(Imagen.obtenerImagen(uriImagen,this).getBitmap());
			imageView.setAdjustViewBounds(true);
		}
	}
	
	public void update(Guardable[] g, int request, String respuesta) {
		if(notificacion.isLoadingShowing()){
			switch (respuesta){
				case ControlDB.res_exito:
					if(g != null){
						switch (request) {
							case ModuloEntidad.RQS_BUSQUEDA_PINTORES_TOTAL:
								if(g[0] instanceof Pintor){
									pintores = (Pintor[]) g;
									actualizarSpinnerPintores();
								}
								break;
							case ModuloEntidad.RQS_BUSQUEDA_PERIODOS_TOTAL:
								if(g[0] instanceof Periodo){
									periodos = (Periodo[]) g;
									actualizarSpinnerPeriodo();
								}
								break;
								
						}
						if(pintores != null && periodos != null){
							notificacion.loadingDismiss();
						}
					}else if(request == ModuloEntidad.RQS_ALTA_PINTURA){
						notificacion.loadingDismiss();
						notificacion.mostarNotificacion("Se cargo exitosamente");
						runOnUiThread(new Runnable() {
							public void run() {
								onBackPressed();
							}
						});
					}
					break;
				case ControlDB.res_falloConexion:
					notificacion.loadingDismiss();
					//Creamos un alertDialog en el Thread UI del activity
					notificacion.mostrarError(ControlDB.res_falloConexion);
					break;
				case ControlDB.res_tablaPintoresVacio:
					notificacion.loadingDismiss();
					//Creamos un alertDialog en el Thread UI del activity
					notificacion.mostrarError(ControlDB.res_tablaPintoresVacio);
					break;
				case ControlDB.res_tablaPeriodoVacio:
					notificacion.loadingDismiss();
					//Creamos un alertDialog en el Thread UI del activity
					notificacion.mostrarError(ControlDB.res_tablaPeriodoVacio);
					break;
				
			}
		}
	}
}
