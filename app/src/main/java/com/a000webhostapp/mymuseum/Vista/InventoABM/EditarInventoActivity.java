package com.a000webhostapp.mymuseum.Vista.InventoABM;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.IObserver;
import com.a000webhostapp.mymuseum.Modelo.Invento;
import com.a000webhostapp.mymuseum.Modelo.Inventor;
import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.Modelo.Periodo;
import com.a000webhostapp.mymuseum.R;
import com.a000webhostapp.mymuseum.Vista.DialogoAlerta;
import com.a000webhostapp.mymuseum.Vista.InventorABM.NuevoInventorActivity;
import com.a000webhostapp.mymuseum.Vista.PeriodoABM.NuevoPeriodoActivity;

import java.util.ArrayList;
import java.util.List;

public class EditarInventoActivity extends AppCompatActivity implements IObserver {
    private Inventor[] inventoresCargados;
    private Periodo[] periodosCargados;

    private Invento invento;
	
	private LinearLayout agregarInventor, agregarPeriodo;
	
    private TextView nombreInvento, descripcion, añoInvencion;
    private Spinner nombrePeriodoSpinner, nombreInventorSpinner;
    private CheckBox ACInvento, theMachine;
    private Button guardar;

    private ProgressDialog loading;
	private boolean cargado;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_invento);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        invento = (Invento) getIntent().getSerializableExtra("Invento");

		buscarInfoSpinners();
		
        nombreInvento = (TextView) findViewById(R.id.editar_invento_nombre_invento);
        descripcion = (TextView) findViewById(R.id.editar_invento_descripcion);
        añoInvencion = (TextView) findViewById(R.id.editar_invento_año_invencion);

        nombrePeriodoSpinner = (Spinner) findViewById(R.id.editar_invento_periodo_spinner);
		
        nombreInventorSpinner = (Spinner) findViewById(R.id.editar_invento_nombre_inventor_spinner);
		

        ACInvento = (CheckBox) findViewById(R.id.ACInvento_EditInvento);
        theMachine = (CheckBox) findViewById(R.id.themachine_EditInvento);
		
		agregarInventor = (LinearLayout) findViewById(R.id.AgregarInventor_EditInvento);
		agregarInventor.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				startNuevoInventorActivity();
			}
		});
		agregarPeriodo = (LinearLayout)findViewById(R.id.AgregarPeriodo_EditInvento);
		agregarPeriodo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				startNuevoPeriodoActivity();
			}
		});

        guardar = (Button) findViewById(R.id.Save_EditInvento);
        guardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
				//Modificamos todoo el invento
				invento.setNombre(nombreInvento.getText().toString());
				invento.setDescripcion(descripcion.getText().toString());
				int añoModifica;
				if(ACInvento.isChecked()){
					añoModifica = Integer.parseInt("-" + añoInvencion.getText().toString());
				}else{
					añoModifica = Integer.parseInt(añoInvencion.getText().toString());
				}
				invento.setAñoInvencion(añoModifica);
				
				
				invento.setPeriodo((Periodo)nombrePeriodoSpinner.getSelectedItem());
				invento.setInventor((Inventor)nombreInventorSpinner.getSelectedItem());
				
				
				invento.setMaquina(theMachine.isChecked());
	
	
				ModuloEntidad.obtenerModulo().editarInvento(invento,EditarInventoActivity.this);
				onBackPressed();
            }
        });
        
        //seteamos los datos
        nombreInvento.setText(invento.getNombre());
        if(invento.getAñoInvencion() < 0){
            añoInvencion.setText(String.valueOf(Math.abs(invento.getAñoInvencion())));
            ACInvento.setChecked(true);
        }else {
            añoInvencion.setText(String.valueOf(invento.getAñoInvencion()));
        }
        descripcion.setText(invento.getDescripcion());
		
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
		
		ModuloEntidad.obtenerModulo().buscarInventores(this);
		ModuloEntidad.obtenerModulo().buscarPeriodos(this);
		
	}
	private void actualizarSpinnerInventores(){
        List<Inventor> spinnerArray =  new ArrayList<Inventor>();
        int selected = 0;
        if(inventoresCargados != null){
            //se llena el array con los inventores
            for (int i = 0; i < inventoresCargados.length; i++){
                spinnerArray.add(inventoresCargados[i]);
				//Buscamos cual es el inventor del invento
				if(invento.getInventor().getNombre().equals(inventoresCargados[i].getNombre())){
					selected = i;
                }
            }
        }

        ArrayAdapter<Inventor> adapter = new ArrayAdapter<Inventor>(this, R.layout.support_simple_spinner_dropdown_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nombreInventorSpinner.setAdapter(adapter);
        nombreInventorSpinner.setSelection(selected);

    }
    private void actualizarSpinnerPeriodo(){
        List<Periodo> spinnerArray =  new ArrayList<Periodo>();
        int selected = 0;
        if(periodosCargados != null){
            //se llena el array con los periodosCargados
            for (int i = 0; i < periodosCargados.length; i++){
                spinnerArray.add(periodosCargados[i]);
				//Buscamos cual es el periodo del invento
                if(invento.getPeriodo().getNombrePeriodo().equals(periodosCargados[i].getNombrePeriodo())){
                    selected = i;
                }
            }
        }

        ArrayAdapter<Periodo> adapter = new ArrayAdapter<Periodo>(this, R.layout.support_simple_spinner_dropdown_item, spinnerArray);
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
    
    public void update(Guardable[] g,int request, String respuesta) {
		if(loading.isShowing()){
			switch (respuesta){
				case ControlDB.res_exito:
					if(g != null){
						switch (request){
							case ModuloEntidad.RQS_BUSQUEDA_PERIODOS_TOTAL:
								if(g[0] instanceof Periodo){
									periodosCargados = (Periodo[]) g;
									actualizarSpinnerPeriodo();
								}
								break;
							case ModuloEntidad.RQS_BUSQUEDA_INVENTORES_TOTAL:
								if(g[0] instanceof Inventor){
									inventoresCargados = (Inventor[]) g;
									actualizarSpinnerInventores();
								}
								break;
						}
						
						//Si todoo esta cargado, se ponen los booleanos como deben ser y se quita el loading
						if(periodosCargados != null && inventoresCargados != null){
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
	private void startNuevoInventorActivity() {
		Intent intent = new Intent(this, NuevoInventorActivity.class);
		startActivity(intent);
	}
	
	private void startNuevoPeriodoActivity() {
		Intent intent = new Intent(this, NuevoPeriodoActivity.class);
		startActivity(intent);
	}
    
}
