package com.a000webhostapp.mymuseum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
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

public class NuevoInventoActivity extends AppCompatActivity {
    private LinearLayout botonNuevoPeriodo, botonNuevoInventor;


    private EditText nomInvento, descriInvento, añoInvento;
    private Spinner periSpin, inventoresSpin;
    private CheckBox hacerInvento, theMachine;
    private Button guardar;

    private Inventor[] inventores;
    private Periodo[] periodos;

    private View.OnClickListener clickListenerBotones;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_invento);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Mandamos a buscar los inventores
        ModuloEntidad.obtenerModulo().buscarInventores();


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

        inventoresSpin =(Spinner) findViewById(R.id.SpinnerInventores_Invento);
        actualizarSpinners();

        inventoresSpin.setOnTouchListener(new View.OnTouchListener() {
            //Este metodo se ejecuta cada vez que presionas el spinner
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(inventores == null){
                    //Mandamos a buscar los inventores que supuestamente estan buscando
                    //Puede que haya terminado de buscar o puede que no
                    inventores = ModuloEntidad.obtenerModulo().obtenerInventores();
                    actualizarSpinners();
                }
                return false;
            }
        });






        hacerInvento = (CheckBox) findViewById(R.id.Ac_Invento);
        theMachine = (CheckBox) findViewById(R.id.TheMachine_Invento);

        guardar = (Button) findViewById(R.id.Save_Invento);
        guardar.setOnClickListener(clickListenerBotones);



    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void guardarInformacion(){

    }
    private void actualizarSpinners(){
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

    private void startNuevoInventorActivity() {
        Intent intent = new Intent(this, NuevoInventorActivity.class);
        startActivity(intent);
    }

    private void startNuevoPeriodoActivity() {
        Intent intent = new Intent(this, NuevoPeriodoActivity.class);
        startActivity(intent);
    }
}
