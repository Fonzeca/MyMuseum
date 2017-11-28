package com.a000webhostapp.mymuseum.Vista.InventorABM;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.a000webhostapp.mymuseum.Controlador.ModuloEntidad;
import com.a000webhostapp.mymuseum.Vista.ModuloNotificacion;
import com.a000webhostapp.mymuseum.DAO.ControlDB;
import com.a000webhostapp.mymuseum.Observers.IObserverEntidad;
import com.a000webhostapp.mymuseum.Modelo.Guardable;
import com.a000webhostapp.mymuseum.R;

public class NuevoInventorActivity extends AppCompatActivity implements IObserverEntidad {
    private EditText nomYApe, año,lugarNacimiento;
    private CheckBox AC;
    private Button btnGuardar;
    private ModuloNotificacion notificacion;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_inventor);
	
		notificacion = new ModuloNotificacion(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        nomYApe = (EditText) findViewById(R.id.nomyapeInventor_Inventor);

        año = (EditText) findViewById(R.id.añoNacimiento_Inventor);
        lugarNacimiento = (EditText) findViewById(R.id.lugarNacimiento_Inventor);

        AC = (CheckBox) findViewById(R.id.checkboxAC_Inventor);

        btnGuardar = (Button) findViewById(R.id.Save_Inventor);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                guardarInformacion();
            }
        });
    }
    private void guardarInformacion(){
        ModuloEntidad me = ModuloEntidad.obtenerModulo();
        String nombreyapellido = nomYApe.getText().toString();
        String lugar = lugarNacimiento.getText().toString();
        int añoInventor;
        if(!nombreyapellido.equals("") && !lugar.equals("") && !año.getText().toString().equals("")){
            if(AC.isChecked()){
                añoInventor = Integer.parseInt("-" + año.getText().toString());
            }else{
                añoInventor = Integer.parseInt(año.getText().toString());
            }
            me.crearInventor(nombreyapellido,lugar,añoInventor,this);
			notificacion.mostrarLoading();
			btnGuardar.setEnabled(false);
        }
    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
	public void update(Guardable[] g, int request, String respuesta) {
		if(notificacion.isLoadingShowing()){
			switch (respuesta){
				case ControlDB.res_exito:
					if(g != null){
					
					}else if(request == ModuloEntidad.RQS_ALTA_INVENTOR){
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
