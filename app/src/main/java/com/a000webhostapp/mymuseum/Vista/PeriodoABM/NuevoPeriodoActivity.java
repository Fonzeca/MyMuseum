package com.a000webhostapp.mymuseum.Vista.PeriodoABM;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.Observers.IObserverEntidad;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.R;
import com.a000webhostapp.mymuseum.Vista.ModuloNotificacion;

public class NuevoPeriodoActivity extends AppCompatActivity implements IObserverEntidad {

    private EditText nombrePeriodo,añoIncioPeriodo, añoFinPeriodo;
    private CheckBox checkAñoIncio, checkAñoFin;
    private Button btnGuardar;
    
    private ModuloNotificacion notificacion;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_periodo);
	
		notificacion = new ModuloNotificacion(this);

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
            me.crearPeriodo(nombre,añoIncio, añoFin,this);
            notificacion.mostrarLoading();
			btnGuardar.setEnabled(false);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    
    @Override
    public void update(Guardable[] g, int request, String respuesta) {
        if(notificacion.isLoadingShowing()){
            switch (respuesta){
                case ControlDB.res_exito:
                    if(g != null){
                    
                    }else if(request == ModuloEntidad.RQS_ALTA_PERIODO){
                        notificacion.loadingDismiss();
                        notificacion.mostarNotificacion("Se cargo exitosamente");
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
