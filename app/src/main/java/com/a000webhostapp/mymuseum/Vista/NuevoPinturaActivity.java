package com.a000webhostapp.mymuseum.Vista;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.a000webhostapp.mymuseum.IObserver;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.R;

/**
 * Created by Alexis on 10/10/2017.
 */

public class NuevoPinturaActivity extends AppCompatActivity implements IObserver {
	
	private LinearLayout agregarPintor, agregarPeriodo;
	
	private TextView nombrePintura, descripcion, añoCreacion;
	private Spinner nombrePeriodoSpinner, nombrePintorSpinner;
	private CheckBox ACPintura;
	private Button guardar;
	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
		super.onCreate(savedInstanceState, persistentState);
		setContentView(R.layout.activity_nuevo_pintura);
		
		agregarPintor = (LinearLayout)findViewById(R.id.NewPintor_NuevaPintura);
		agregarPeriodo = (LinearLayout)findViewById(R.id.NewPeri_NuevaPintura);
		
		nombrePeriodoSpinner = (Spinner)findViewById(R.id.spinnerPeri_NuevaPintura);
		nombrePintorSpinner = (Spinner)findViewById(R.id.spinnerPintores_NuevaPintura);
		
		ACPintura = (CheckBox)findViewById(R.id.Ac_NuevaPintura);
		
		guardar = (Button)findViewById(R.id.Save_NuevaPintura);
		
	}
	
	public void update(Guardable[] g, String respuesta) {
		
	}
}
