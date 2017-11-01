package com.a000webhostapp.mymuseum.Vista.TrasladoABM;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.IObserver;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.Modelo.Periodo;
import com.a000webhostapp.mymuseum.Modelo.Pintura;
import com.a000webhostapp.mymuseum.Modelo.Traslado;
import com.a000webhostapp.mymuseum.R;

import java.util.Calendar;

/**
 * Created by Alexis on 25/10/2017.
 */

public class NuevoTrasladoActivity extends AppCompatActivity implements IObserver {
	
	private Spinner spinnerPinturas;
	private TextView destino, origen;
	private TextView fecha;
	private Button save;
	
	private ProgressDialog loading;
	private DatePickerDialog datePickerDialog;
	private DatePickerDialog.OnDateSetListener listenerDate;
	
	private Pintura pinturaActual;
	private Traslado trasladoAnterior;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_generar_traslado);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		Calendar calendar = Calendar.getInstance();
		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		int mes = calendar.get(Calendar.MONTH);
		int año = calendar.get(Calendar.YEAR);
		
		
		listenerDate= new DatePickerDialog.OnDateSetListener() {
			public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
				String fechaCompleta = i2 + "/" + i1 + "/" + i;
				fecha.setText(fechaCompleta);
			}
		};
		datePickerDialog = new DatePickerDialog(this, listenerDate,año,mes,dia);
		
		
		
		
		buscarInfoSpinner();
		
		spinnerPinturas = (Spinner)findViewById(R.id.spinnerPintura_Generar_Traslado);
		spinnerPinturas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				pinturaActual = (Pintura) spinnerPinturas.getSelectedItem();
				buscarInfoTrasladoPintura(pinturaActual);
			}
			public void onNothingSelected(AdapterView<?> adapterView) {
			}
		});
		
		origen = (TextView)findViewById(R.id.textOrigen_Generar_Traslado);
		
		destino = (TextView)findViewById(R.id.textDestino_GenerarTraslado);
		fecha = (TextView)findViewById(R.id.textFecha_GenerarTraslado);
		fecha.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				datePickerDialog.show();
			}
		});
		
		save = (Button)findViewById(R.id.boton_Generar_GenerarTraslado);
		save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String strOrigen = origen.getText().toString();
				String strDestino = destino.getText().toString();
				String strFecha = fecha.getText().toString();
				
				ModuloEntidad.obtenerModulo().crearTraslado(pinturaActual.getNombre(),pinturaActual.getID(),strOrigen,strDestino,strFecha,NuevoTrasladoActivity.this);
				
				finish();
			}
		});
	}
	private void actualizarCampos(){
		runOnUiThread(new Runnable() {
			public void run() {
				int cantidadTraslados = pinturaActual.cantidadTraslados();
				
				destino.setText("");
				fecha.setText("");
				if(cantidadTraslados > 0){
					Traslado ultimoTraslado = pinturaActual.getTraslado(cantidadTraslados-1);
					origen.setText(ultimoTraslado.getLugarDestino());
					origen.setEnabled(false);
				}else{
					origen.setText("");
					origen.setEnabled(true);
				}
			}
		});
	}
	private void buscarInfoSpinner(){
		runOnUiThread(new Runnable() {
			public void run() {
				loading = new ProgressDialog(NuevoTrasladoActivity.this){
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
			}
		});
		
		ModuloEntidad.obtenerModulo().buscarPinturas(this);
	}
	private void buscarInfoTrasladoPintura(Pintura pintura){
		runOnUiThread(new Runnable() {
			public void run() {
				loading = new ProgressDialog(NuevoTrasladoActivity.this){
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
			}
		});
		
		ModuloEntidad.obtenerModulo().buscarTraslados(this,pintura);
	}
	private void cargarPinturasSpinner(Pintura[] p){
		if(p != null){
			ArrayAdapter<Pintura> adapter = new ArrayAdapter<Pintura>(this, R.layout.support_simple_spinner_dropdown_item, p);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerPinturas.setAdapter(adapter);
		}
	}
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
	}
	
	public void update(Guardable[] g, int request, String respuesta) {
		if(loading.isShowing()){
			switch (respuesta){
				case ControlDB.res_exito:
					switch (request){
						case ModuloEntidad.RQS_BUSQUEDA_PINTURAS_TOTAL:
							cargarPinturasSpinner((Pintura[]) g);
							break;
						case ModuloEntidad.RQS_BUSQUEDA_TRASLADOS_UNICO_PINTURA:
							actualizarCampos();
							break;
					}
					loading.dismiss();
					break;
				case ControlDB.res_tablaTrasladoUnicoVacio:
					actualizarCampos();
					loading.dismiss();
					break;
			}
		}
	}
}
