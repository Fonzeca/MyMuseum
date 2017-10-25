package com.a000webhostapp.mymuseum.Vista.PinturaABM;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
import com.a000webhostapp.mymuseum.Modelo.Pintura;
import com.a000webhostapp.mymuseum.R;
import com.a000webhostapp.mymuseum.Vista.DialogoAlerta;
import com.a000webhostapp.mymuseum.Vista.PeriodoABM.NuevoPeriodoActivity;
import com.a000webhostapp.mymuseum.Vista.PintorABM.NuevoPintorActivity;

public class EditarPinturaActivity extends AppCompatActivity implements IObserver {
    private Pintor[] pintoresCargados;
    private Periodo[] periodosCargados;

    private Pintura pintura;
	
	private LinearLayout agregarPintor, agregarPeriodo;
	
    private TextView nombre, descripcion, año;
    private Spinner nombrePeriodoSpinner, nombrePintoresSpinner;
    private CheckBox ACPintura;
    private Button guardar;

    private ProgressDialog loading;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_pintura);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        pintura = (Pintura) getIntent().getSerializableExtra("Pintura");

		buscarInfoSpinners();
		
        nombre = (TextView) findViewById(R.id.editar_pintura_nombre_pintura);
        descripcion = (TextView) findViewById(R.id.editar_pintura_descripcion);
        año = (TextView) findViewById(R.id.editar_pintura_año_invencion);

        nombrePeriodoSpinner = (Spinner) findViewById(R.id.editar_pintura_periodo_spinner);
		
        nombrePintoresSpinner = (Spinner) findViewById(R.id.editar_pintura_nombre_pintor_spinner);
		

        ACPintura = (CheckBox) findViewById(R.id.ACPintura_EditPintura);
		
		agregarPintor = (LinearLayout) findViewById(R.id.AgregarPintor_EditPintura);
		agregarPintor.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				startNuevoPintorActivity();
			}
		});
		agregarPeriodo = (LinearLayout)findViewById(R.id.AgregarPeriodo_EditPintura);
		agregarPeriodo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				startNuevoPeriodoActivity();
			}
		});

        guardar = (Button) findViewById(R.id.Save_EditPintura);
        guardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
				//Modificamos todoo el pintura
				pintura.setNombre(nombre.getText().toString());
				pintura.setDescripcion(descripcion.getText().toString());
				int añoModifica;
				if(ACPintura.isChecked()){
					añoModifica = Integer.parseInt("-" + año.getText().toString());
				}else{
					añoModifica = Integer.parseInt(año.getText().toString());
				}
				pintura.setAñoInvencion(añoModifica);
				
				
				pintura.setPeriodo((Periodo)nombrePeriodoSpinner.getSelectedItem());
				pintura.setPintor((Pintor) nombrePintoresSpinner.getSelectedItem());
	
	
				ModuloEntidad.obtenerModulo().editarPintura(pintura);
				onBackPressed();
            }
        });
        
        //seteamos los datos
        nombre.setText(pintura.getNombre());
        if(pintura.getAñoInvencion() < 0){
            año.setText(String.valueOf(Math.abs(pintura.getAñoInvencion())));
            ACPintura.setChecked(true);
        }else {
            año.setText(String.valueOf(pintura.getAñoInvencion()));
        }
        descripcion.setText(pintura.getDescripcion());
		
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
	private void actualizarSpinnerInventores(){
        int selected = 0;
        if(pintoresCargados != null){
            for (int i = 0; i < pintoresCargados.length; i++){
				//Buscamos cual es el inventor del pintura
				if(pintura.getPintor().getNombre().equals(pintoresCargados[i].getNombre())){
					selected = i;
                }
            }
        }

        ArrayAdapter<Pintor> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, pintoresCargados);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nombrePintoresSpinner.setAdapter(adapter);
        nombrePintoresSpinner.setSelection(selected);

    }
    private void actualizarSpinnerPeriodo(){
        int selected = 0;
        if(periodosCargados != null){
            for (int i = 0; i < periodosCargados.length; i++){
				//Buscamos cual es el periodo del pintura
                if(pintura.getPeriodo().getNombrePeriodo().equals(periodosCargados[i].getNombrePeriodo())){
                    selected = i;
                }
            }
        }

        ArrayAdapter<Periodo> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, periodosCargados);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nombrePeriodoSpinner.setAdapter(adapter);
        nombrePeriodoSpinner.setSelection(selected);
    }
	
	@Override
	protected void onRestart() {
		super.onRestart();
		buscarInfoSpinners();
	}
	
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
	}
    
    public void update(Guardable[] g, String respuesta) {
		if(loading.isShowing()){
			switch(respuesta){
				case ControlDB.res_exitoBusqueda:
					if(g != null){
						if(g[0] instanceof Pintor){
							pintoresCargados = (Pintor[]) g;
							actualizarSpinnerInventores();
							
						}else if(g[0] instanceof Periodo){
							periodosCargados = (Periodo[]) g;
							actualizarSpinnerPeriodo();
						}
						
						//Si todoo esta cargado, se ponen los booleanos como deben ser y se quita el loading
						if(periodosCargados != null && pintoresCargados != null){
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
				case ControlDB.res_tablaPeriodoVacio:
					loading.dismiss();
					//Creamos un alertDialog en el Thread UI del activity
					new DialogoAlerta(this, ControlDB.res_tablaPeriodoVacio, "Error").mostrar();
					break;
			}
		}
    }
	private void startNuevoPintorActivity() {
		Intent intent = new Intent(this, NuevoPintorActivity.class);
		startActivity(intent);
	}
	private void startNuevoPeriodoActivity() {
		Intent intent = new Intent(this, NuevoPeriodoActivity.class);
		startActivity(intent);
	}
    
}
