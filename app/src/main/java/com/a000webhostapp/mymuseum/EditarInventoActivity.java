package com.a000webhostapp.mymuseum;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.a000webhostapp.mymuseum.Entidades.Invento;
import com.a000webhostapp.mymuseum.Entidades.Inventor;
import com.a000webhostapp.mymuseum.Entidades.ModuloEntidad;
import com.a000webhostapp.mymuseum.Entidades.Periodo;

import java.util.ArrayList;
import java.util.List;

public class EditarInventoActivity extends AppCompatActivity implements IObserver{
    private Inventor[] inventoresCargados;
    private Periodo[] periodosCargados;

    private Invento invento;
    private TextView nombreInvento, descripcion, añoInvencion;
    private Spinner nombrePeriodoSpinner, nombreInventorSpinner;
    private CheckBox ACInvento, theMachine;
    private Button guardar;

    private ProgressDialog loading;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_invento);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        invento = (Invento) getIntent().getSerializableExtra("Invento");

        loading = new ProgressDialog(this);
        loading.setMessage("Loading...");
        loading.setCancelable(false);
        loading.show();

        ModuloEntidad.obtenerModulo().buscarInventores(this);
        ModuloEntidad.obtenerModulo().buscarPeriodos(this);


        nombreInvento = (TextView) findViewById(R.id.editar_invento_nombre_invento);
        descripcion = (TextView) findViewById(R.id.editar_invento_descripcion);
        añoInvencion = (TextView) findViewById(R.id.editar_invento_año_invencion);

        nombrePeriodoSpinner = (Spinner) findViewById(R.id.editar_invento_periodo_spinner);
        nombreInventorSpinner = (Spinner) findViewById(R.id.editar_invento_nombre_inventor_spinner);

        ACInvento = (CheckBox) findViewById(R.id.ACInvento_EditInvento);
        theMachine = (CheckBox) findViewById(R.id.themachine_EditInvento);

        guardar = (Button) findViewById(R.id.Save_EditInvento);


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


    public void update(Guardable[] g, int id) {
        if(g != null){
            if(g[0] instanceof Inventor){
                inventoresCargados = (Inventor[]) g;
                actualizarSpinnerInventores();

            }else if(g[0] instanceof Periodo){
                periodosCargados = (Periodo[]) g;
                actualizarSpinnerPeriodo();
            }
        }
        loading.dismiss();
    }
}
