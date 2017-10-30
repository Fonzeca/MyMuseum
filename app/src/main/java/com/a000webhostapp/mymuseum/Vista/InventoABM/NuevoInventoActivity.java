package com.a000webhostapp.mymuseum.Vista.InventoABM;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.IObserver;
import com.a000webhostapp.mymuseum.Modelo.Inventor;
import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.Modelo.Periodo;
import com.a000webhostapp.mymuseum.R;
import com.a000webhostapp.mymuseum.Vista.DialogoAlerta;
import com.a000webhostapp.mymuseum.Vista.InventorABM.NuevoInventorActivity;
import com.a000webhostapp.mymuseum.Vista.PeriodoABM.NuevoPeriodoActivity;

import java.util.ArrayList;
import java.util.List;

public class NuevoInventoActivity extends AppCompatActivity implements IObserver {
    private LinearLayout botonNuevoPeriodo, botonNuevoInventor;


    private EditText nomInvento, descriInvento, añoInvento;
    private Spinner periSpin, inventoresSpin;
    private CheckBox ACInvento, theMachine;
    private Button guardar;

    private Inventor[] inventores;
    private Periodo[] periodos;

    private Inventor inventorActual;
    private Periodo periodoActual;
    
    private ProgressDialog loading;
    private boolean cargado;

    private View.OnClickListener clickListenerBotones;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_invento);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Mandamos a buscar los inventores
        buscarInfoSpinners();




        clickListenerBotones = new View.OnClickListener() {
            public void onClick(View view) {
                int id = view.getId();
                switch (id){
                    case R.id.NewPeriodo_Inventor:
                        startNuevoPeriodoActivity();
                        break;
                    case R.id.NewInventor_Inventor:
                        startNuevoInventorActivity();
                        break;
                    case R.id.Save_Invento:
                        guardarInformacion();
                        break;
                }
            }
        };

        botonNuevoPeriodo = (LinearLayout) findViewById(R.id.NewPeriodo_Inventor);
        botonNuevoPeriodo.setOnClickListener(clickListenerBotones);

        botonNuevoInventor = (LinearLayout) findViewById(R.id.NewInventor_Inventor);
        botonNuevoInventor.setOnClickListener(clickListenerBotones);


        nomInvento = (EditText)findViewById(R.id.NomInvento_Invento);
        descriInvento = (EditText) findViewById(R.id.Descri_Invento);
        añoInvento = (EditText) findViewById(R.id.AñoInvento_Invento);

        periSpin = (Spinner) findViewById(R.id.SpinnerPeri_Invento);
        periSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                periodoActual = (Periodo)periSpin.getSelectedItem();
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });



        inventoresSpin =(Spinner) findViewById(R.id.SpinnerInventores_Invento);
        inventoresSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                inventorActual = (Inventor)inventoresSpin.getSelectedItem();
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });






        ACInvento = (CheckBox) findViewById(R.id.Ac_Invento);
        theMachine = (CheckBox) findViewById(R.id.TheMachine_Invento);

        guardar = (Button) findViewById(R.id.Save_Invento);
        guardar.setOnClickListener(clickListenerBotones);
		


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
        List<Inventor> spinnerArray =  new ArrayList<>();
        if(inventores != null){
            //se llena el array con los invententores
            for (int i = 0; i < inventores.length; i++){
                spinnerArray.add(inventores[i]);
            }
        }
		//Si bien guardamos inventores en el array, se muestro el toString de cada inventor.
        ArrayAdapter<Inventor> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inventoresSpin.setAdapter(adapter);
    }
    private void actualizarSpinnerPeriodo(){
        List<Periodo> spinnerArray =  new ArrayList<>();
        if(periodos != null){
            //se llena el array con los periodos
            for (int i = 0; i < periodos.length; i++){
                spinnerArray.add(periodos[i]);
            }
        }
        
		//Si bien guardamos peridoos en el array, se muestro el toString de cada periodo.
        ArrayAdapter<Periodo> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        periSpin.setAdapter(adapter);
    }
	
    
	private void guardarInformacion(){
		String nombreGuar = nomInvento.getText().toString();
		String descriGuar = descriInvento.getText().toString();
		String añoTextGuar = añoInvento.getText().toString();
		if(!nombreGuar.equals("") && !descriGuar.equals("") && !añoTextGuar.equals("")){
			int añoGuar;
			if(ACInvento.isChecked()){
				añoGuar = Integer.parseInt("-" + añoTextGuar);
			}else{
				añoGuar = Integer.parseInt(añoTextGuar);
			}
			
			ModuloEntidad.obtenerModulo().crearInvento(nombreGuar,descriGuar,periodoActual, inventorActual,
					añoGuar,theMachine.isChecked(),this);
			onBackPressed();
		}
	}
	
	protected void onRestart() {
		super.onRestart();
		buscarInfoSpinners();
	}
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
	}
	
	private void startNuevoInventorActivity() {
		Intent intent = new Intent(this, NuevoInventorActivity.class);
		startActivity(intent);
	}
	
	private void startNuevoPeriodoActivity() {
		Intent intent = new Intent(this, NuevoPeriodoActivity.class);
		startActivity(intent);
	}
	
	public void update(Guardable[] g,int request, String respuesta) {
        if(loading.isShowing()){
			switch (respuesta){
				case ControlDB.res_exito:
					if(g != null){
						switch (request){
							case ModuloEntidad.RQS_BUSQUEDA_INVENTORES_TOTAL:
								if(g[0] instanceof Inventor){
									inventores = (Inventor[]) g;
									actualizarSpinnerInventores();
								}
								break;
							case ModuloEntidad.RQS_BUSQUEDA_PERIODOS_TOTAL:
								if(g[0] instanceof Periodo){
									periodos = (Periodo[]) g;
									actualizarSpinnerPeriodo();
								}
								break;
						}
						if(inventores != null && periodos != null){
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
}
