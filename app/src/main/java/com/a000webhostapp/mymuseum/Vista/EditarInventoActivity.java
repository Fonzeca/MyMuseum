package com.a000webhostapp.mymuseum.Vista;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.IObserver;
import com.a000webhostapp.mymuseum.Modelo.Invento;
import com.a000webhostapp.mymuseum.Modelo.Inventor;
import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.Modelo.Periodo;
import com.a000webhostapp.mymuseum.R;

import java.util.ArrayList;
import java.util.List;

public class EditarInventoActivity extends AppCompatActivity implements IObserver {
    private Inventor[] inventoresCargados;
    private Periodo[] periodosCargados;

    private Invento invento;
    private TextView nombreInvento, descripcion, añoInvencion;
    private Spinner nombrePeriodoSpinner, nombreInventorSpinner;
    private CheckBox ACInvento, theMachine;
    private Button guardar;

    private ProgressDialog loading;
	private boolean buscando, cargado;


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
				if(!invento.getPeriodo().getNombrePeriodo().equals(nombrePeriodoSpinner.getSelectedItem())){
					for(Periodo p : periodosCargados){
						if(p.getNombrePeriodo().equals(nombrePeriodoSpinner.getSelectedItem())){
							invento.setPeriodo(p);
						}
					}
				}
				
				if(!invento.getInventor().getNombreCompleto().equals(nombreInventorSpinner.getSelectedItem())){
					for(Inventor i : inventoresCargados){
						if(i.getNombreCompleto().equals(nombreInventorSpinner.getSelectedItem())){
							invento.setInventor(i);
						}
					}
				}
				invento.setMaquina(theMachine.isChecked());
	
	
				ModuloEntidad.obtenerModulo().editarInvento(invento);
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

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
	
	
	private void actualizarSpinnerInventores(){
        List<String> spinnerArray =  new ArrayList<String>();
        int selected = 0;
        if(inventoresCargados != null){
            //se llena el array con los inventores
            for (int i = 0; i < inventoresCargados.length; i++){
                spinnerArray.add(inventoresCargados[i].getNombreCompleto());
                if(invento.getInventor().getNombreCompleto().equals(inventoresCargados[i].getNombreCompleto())){
                    selected = i;
                }
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nombreInventorSpinner.setAdapter(adapter);
        nombreInventorSpinner.setSelection(selected);

    }
    private void actualizarSpinnerPeriodo(){
        List<String> spinnerArray =  new ArrayList<String>();
        int selected = 0;
        if(periodosCargados != null){
            //se llena el array con los periodosCargados
            for (int i = 0; i < periodosCargados.length; i++){
                spinnerArray.add(periodosCargados[i].getNombrePeriodo());
                if(invento.getPeriodo().getNombrePeriodo().equals(periodosCargados[i].getNombrePeriodo())){
                    selected = i;
                }
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nombrePeriodoSpinner.setAdapter(adapter);
        nombrePeriodoSpinner.setSelection(selected);
    }
	
    private void buscarInfoSpinners(){
		buscando = true;
		loading = new ProgressDialog(this){
			public void onBackPressed() {
				if(isShowing()){
					dismiss();
					buscando = false;
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

    
    public void update(Guardable[] g, int id) {
		if(buscando){
			if(g != null){
				if(g[0] instanceof Inventor){
					inventoresCargados = (Inventor[]) g;
					actualizarSpinnerInventores();
					
				}else if(g[0] instanceof Periodo){
					periodosCargados = (Periodo[]) g;
					actualizarSpinnerPeriodo();
				}
				
				//Si todoo esta cargado, se ponen los booleanos como deben ser y se quita el loading
				if(periodosCargados != null && inventoresCargados != null){
					cargado = true;
					buscando = false;
					loading.dismiss();
				}
			}else if(id == -1){
				loading.dismiss();
				new DialogoAlerta(this, "No se pudo conectar", "Error").mostrar();
				buscando = false;
			}
		}
    }
}
