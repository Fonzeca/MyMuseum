package com.a000webhostapp.mymuseum.Vista.PintorABM;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.IObserver;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.R;

/**
 * Created by Alexis on 10/10/2017.
 */

public class NuevoPintorActivity extends AppCompatActivity implements IObserver {
	private EditText nomYApe, año,lugarNacimiento;
	private CheckBox AC;
	private Button btnGuardar;
	
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nuevo_pintor);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		
		nomYApe = (EditText) findViewById(R.id.nomyapePintor_NuevoPintor);
		
		año = (EditText) findViewById(R.id.añoNacimiento_NuevoPintor);
		lugarNacimiento = (EditText) findViewById(R.id.lugarNacimiento_NuevoPintor);
		
		AC = (CheckBox) findViewById(R.id.checkboxAC_NuevoPintor);
		
		btnGuardar = (Button) findViewById(R.id.Save_NuevoPintor);
		btnGuardar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				guardarInformacion();
			}
		});
		
	}
	public void guardarInformacion(){
		ModuloEntidad me = ModuloEntidad.obtenerModulo();
		String nombreyapellido = nomYApe.getText().toString();
		String lugar = lugarNacimiento.getText().toString();
		int añoPintor;
		if(!nombreyapellido.equals("") && !lugar.equals("") && !año.getText().toString().equals("")){
			if(AC.isChecked()){
				añoPintor = Integer.parseInt("-" + año.getText().toString());
			}else{
				añoPintor = Integer.parseInt(año.getText().toString());
			}
			me.crearPintor(nombreyapellido,lugar,añoPintor,this);
			onBackPressed();
		}
	}
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
	}
	
	@Override
	public void update(Guardable[] g, int request, String respuesta) {
	
	}
}
