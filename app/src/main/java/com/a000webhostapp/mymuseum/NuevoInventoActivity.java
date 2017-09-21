package com.a000webhostapp.mymuseum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.a000webhostapp.mymuseum.Entidades.Inventor;
import com.a000webhostapp.mymuseum.Entidades.ModuloEntidad;
import com.a000webhostapp.mymuseum.Entidades.Periodo;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class NuevoInventoActivity extends AppCompatActivity implements IObserver{
    private LinearLayout botonNuevoPeriodo, botonNuevoInventor;


    private EditText nomInvento, descriInvento, añoInvento;
    private Spinner periSpin, inventoresSpin;
    private CheckBox ACInvento, theMachine;
    private Button guardar;

    private Inventor[] inventores;
    private Periodo[] periodos;

    private Inventor inventoActual;
    private Periodo periodoActual;

    private View.OnClickListener clickListenerBotones;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_invento);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Mandamos a buscar los inventores
        ModuloEntidad.obtenerModulo().buscarInventores(this);
        ModuloEntidad.obtenerModulo().buscarPeriodos(this);




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
                String nombrePeriodoActual = (String)periSpin.getSelectedItem();
                for (int index = 0; index< periodos.length; index++){
                    if(periodos[index].getNombrePeriodo().equals(nombrePeriodoActual)){
                        periodoActual = periodos[index];
                        return;
                    }
                }

            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });



        inventoresSpin =(Spinner) findViewById(R.id.SpinnerInventores_Invento);
        inventoresSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String nombreInventorActual = (String)inventoresSpin.getSelectedItem();
                for (int index = 0; index< inventores.length; index++){
                    if(inventores[index].getNombreCompleto().equals(nombreInventorActual)){
                        inventoActual = inventores[index];
                        return;
                    }
                }
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });






        ACInvento = (CheckBox) findViewById(R.id.Ac_Invento);
        theMachine = (CheckBox) findViewById(R.id.TheMachine_Invento);

        guardar = (Button) findViewById(R.id.Save_Invento);
        guardar.setOnClickListener(clickListenerBotones);



    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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

            ModuloEntidad.obtenerModulo().crearInvento(nombreGuar,descriGuar,periodoActual,inventoActual,
                    añoGuar,theMachine.isChecked());
            onBackPressed();
        }
    }

    private void actualizarSpinnerInventores(){
        List<String> spinnerArray =  new ArrayList<String>();
        if(inventores != null){
            //se llena el array con los invententores
            for (int i = 0; i < inventores.length; i++){
                spinnerArray.add(inventores[i].getNombreCompleto());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inventoresSpin.setAdapter(adapter);
    }
    private void actualizarSpinnerPeriodo(){
        List<String> spinnerArray =  new ArrayList<String>();
        if(periodos != null){
            //se llena el array con los periodos
            for (int i = 0; i < periodos.length; i++){
                spinnerArray.add(periodos[i].getNombrePeriodo());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        periSpin.setAdapter(adapter);
    }

    private void startNuevoInventorActivity() {
        Intent intent = new Intent(this, NuevoInventorActivity.class);
        startActivity(intent);
    }

    private void startNuevoPeriodoActivity() {
        Intent intent = new Intent(this, NuevoPeriodoActivity.class);
        startActivity(intent);
    }

    public void update(Guardable[] g, int id) {
        if(g != null){
            if(g[0] instanceof Inventor){
                inventores = (Inventor[]) g;
                actualizarSpinnerInventores();
            }else if(g[0] instanceof Periodo){
                periodos = (Periodo[]) g;
                actualizarSpinnerPeriodo();
            }
        }
    }
}
