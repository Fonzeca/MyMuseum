package com.a000webhostapp.mymuseum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.a000webhostapp.mymuseum.Entidades.ModuloEntidad;
import com.a000webhostapp.mymuseum.Entidades.Periodo;

public class EditarPeriodoActivity extends AppCompatActivity {
    
    private EditText nombre, añoInicio, añoFin;
    private CheckBox ACIncio, ACFin;
    private Button guardar;
    
    private Periodo periodo;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_periodo);
        
        periodo = (Periodo) getIntent().getSerializableExtra("Periodo");
        
        nombre = (EditText) findViewById(R.id.nombre_EditPeriodo);
        añoInicio = (EditText) findViewById(R.id.añoInicio_EditPeriodo);
        añoFin = (EditText) findViewById(R.id.añoFin_EditPeriodo);
        
        ACIncio = (CheckBox) findViewById(R.id.ACInicio_EditPeriodo);
        ACFin = (CheckBox) findViewById(R.id.ACFin_EditPeriodo);
        
        guardar = (Button) findViewById(R.id.Save_EditPeriodo);
        guardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ModuloEntidad.obtenerModulo().editarPeriodo();
            }
        });
        
        nombre.setText(periodo.getNombrePeriodo());
        int añoInicioInt = periodo.getAñoInicio();
        if(añoInicioInt < 0){
            añoInicio.setText(String.valueOf(Math.abs(añoInicioInt)));
            ACIncio.setChecked(true);
        }else{
            añoInicio.setText(String.valueOf(añoInicioInt));
        }
    
        int añoFinInt = periodo.getAñoFin();
        if(añoFinInt < 0){
            añoFin.setText(String.valueOf(Math.abs(añoFinInt)));
            ACFin.setChecked(true);
        }else{
            añoFin.setText(String.valueOf(añoFinInt));
        }
        
        
    }
}
