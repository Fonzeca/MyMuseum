package com.a000webhostapp.mymuseum.Vista.PeriodoABM;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.R;

public class NuevoPeriodoActivity extends AppCompatActivity {

    private EditText nombrePeriodo,añoIncioPeriodo, añoFinPeriodo;
    private CheckBox checkAñoIncio, checkAñoFin;
    private Button btnGuardar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_periodo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        nombrePeriodo = (EditText) findViewById(R.id.nomPeriodo_Periodo);

        añoIncioPeriodo = (EditText) findViewById(R.id.añoInicoPeriodo_Periodo);
        añoFinPeriodo = (EditText) findViewById(R.id.añoFinPeriodo_Periodo);

        checkAñoIncio = (CheckBox) findViewById(R.id.checkbox_añoInicio);
        checkAñoFin = (CheckBox) findViewById(R.id.checkbox_añoFin);

        btnGuardar = (Button) findViewById(R.id.Save_Periodo);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                guardarInformacion();
            }
        });


    }
    private void guardarInformacion(){
        ModuloEntidad me = ModuloEntidad.obtenerModulo();
        String nombre = nombrePeriodo.getText().toString();
        String añoInicoTexto = añoIncioPeriodo.getText().toString();
        String añoFinTexto = añoFinPeriodo.getText().toString();
        int añoIncio;
        int añoFin;
        if(!nombre.equals("") && !añoInicoTexto.equals("") && !añoFinTexto.equals("")){
            if(checkAñoIncio.isChecked()){
                añoIncio = Integer.parseInt("-" + añoInicoTexto);
            }else{
                añoIncio = Integer.parseInt(añoInicoTexto);
            }

            if(checkAñoFin.isChecked()){
                añoFin = Integer.parseInt("-" + añoFinTexto);
            }else{
                añoFin = Integer.parseInt(añoFinTexto);
            }
            me.crearPeriodo(nombre,añoIncio, añoFin);
            onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
