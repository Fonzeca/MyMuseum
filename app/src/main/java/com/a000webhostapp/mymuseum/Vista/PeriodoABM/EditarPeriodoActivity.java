package com.a000webhostapp.mymuseum.Vista.PeriodoABM;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.Vista.ModuloNotificacion;
import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.Observers.IObserverEntidad;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.Modelo.Periodo;
import com.a000webhostapp.mymuseum.R;

import java.util.ArrayList;
import java.util.List;

public class EditarPeriodoActivity extends AppCompatActivity implements IObserverEntidad {
    private EditText nombre, añoInicio, añoFin;
    private CheckBox ACIncio, ACFin;
    private Button guardar;
	private Spinner nombrePeriodoSpinner;
    private Periodo[] periodos;
    private Periodo periodoActual;
	private ModuloNotificacion notificacion;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_periodo);
	
		notificacion = new ModuloNotificacion(this);
	
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
        
        periodoActual = (Periodo) getIntent().getSerializableExtra("Periodo");
        
        nombre = (EditText) findViewById(R.id.nombre_EditPeriodo);
        añoInicio = (EditText) findViewById(R.id.añoInicio_EditPeriodo);
        añoFin = (EditText) findViewById(R.id.añoFin_EditPeriodo);
        
        ACIncio = (CheckBox) findViewById(R.id.ACInicio_EditPeriodo);
        ACFin = (CheckBox) findViewById(R.id.ACFin_EditPeriodo);
		
		nombrePeriodoSpinner = (Spinner) findViewById(R.id.spinnerPeriodo_EditPeriodo);
		nombrePeriodoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				String nombrePeriActual = (String) nombrePeriodoSpinner.getSelectedItem();
				for (int index = 0; index < periodos.length; index++){
					if(periodos[index].getNombrePeriodo().equals(nombrePeriActual)){
						periodoActual = periodos[index];
						actualizarCampos();
						return;
					}
				}
			}
			public void onNothingSelected(AdapterView<?> adapterView) {
			}
		});
		buscarInfoSpinner();
        
        guardar = (Button) findViewById(R.id.Save_EditPeriodo);
        guardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
				String nombreConfig = nombre.getText().toString();
				String añoInicoTexto = añoInicio.getText().toString();
				String añoFinTexto = añoFin.getText().toString();
				int añoIncio;
				int añoFin;
				if(!nombreConfig.equals("") && !añoInicoTexto.equals("") && !añoFinTexto.equals("")){
					if(ACIncio.isChecked()){
						añoIncio = Integer.parseInt("-" + añoInicoTexto);
					}else{
						añoIncio = Integer.parseInt(añoInicoTexto);
					}
		
					if(ACFin.isChecked()){
						añoFin = Integer.parseInt("-" + añoFinTexto);
					}else{
						añoFin = Integer.parseInt(añoFinTexto);
					}
					periodoActual.setNombrePeriodo(nombreConfig);
					periodoActual.setAñoInicio(añoIncio);
					periodoActual.setAñoFin(añoFin);
					ModuloEntidad.obtenerModulo().editarPeriodo(periodoActual,EditarPeriodoActivity.this);
					notificacion.mostrarLoading();
					guardar.setEnabled(false);
				}
            }
        });
        
        
        
    }
    private void actualizarCampos(){
		nombre.setText(periodoActual.getNombrePeriodo());
        int añoInicioInt = periodoActual.getAñoInicio();
        if(añoInicioInt < 0){
            añoInicio.setText(String.valueOf(Math.abs(añoInicioInt)));
            ACIncio.setChecked(true);
        }else{
            añoInicio.setText(String.valueOf(añoInicioInt));
        }
    
        int añoFinInt = periodoActual.getAñoFin();
        if(añoFinInt < 0){
            añoFin.setText(String.valueOf(Math.abs(añoFinInt)));
            ACFin.setChecked(true);
        }else{
            añoFin.setText(String.valueOf(añoFinInt));
        }
	}
	private void actualizarSpinnerInventores(){
		List<String> spinnerArray =  new ArrayList<String>();
		if(periodos != null){
			//se llena el array con los invententores
			for (int i = 0; i < periodos.length; i++){
				spinnerArray.add(periodos[i].getNombrePeriodo());
			}
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinnerArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		nombrePeriodoSpinner.setAdapter(adapter);
		
		//Buscamos para que sepan cual es el inventor actual
		String nombrePeriodoActual = (String)nombrePeriodoSpinner.getSelectedItem();
		for (int index = 0; index < periodos.length; index++){
			if(periodos[index].getNombrePeriodo().equals(nombrePeriodoActual)){
				periodoActual = periodos[index];
				actualizarCampos();
				return;
			}
		}
	}
	private void buscarInfoSpinner(){
		notificacion.mostrarLoading();
		ModuloEntidad.obtenerModulo().buscarPeriodos(this);
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
							case ModuloEntidad.RQS_BUSQUEDA_PERIODOS_TOTAL:
								if(g[0] instanceof Periodo){
									periodos = (Periodo[]) g;
								}
								break;
						}
						actualizarSpinnerInventores();
						notificacion.loadingDismiss();
					}else if(request == ModuloEntidad.RQS_MODIFICACION_PERIODO){
						notificacion.loadingDismiss();
						notificacion.mostarNotificacion("Se modifico exitosamente");
						runOnUiThread(new Runnable() {
							public void run() {
								onBackPressed();
							}
						});
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
